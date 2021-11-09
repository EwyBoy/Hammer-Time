package com.ewyboy.template.util;

import it.unimi.dsi.fastutil.objects.ReferenceLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import java.util.EnumSet;
import java.util.Optional;
import java.util.PriorityQueue;

public class TreeBuilder {

    private static final EnumSet<Direction> ALL_DIRECTIONS = EnumSet.allOf(Direction.class);

    public static Optional<Tree> getTree(World world, BlockPos originPos) throws TreeTooBigException{
        Block originBlock = world.getBlockState(originPos).getBlock();
        if(!isLogBlock(originBlock)){
            return empty();
        }

        int maxLogCount = 300;
        Queue<ToAnalyzePos> toAnalyzePos = new PriorityQueue<>();
        Set<ToAnalyzePos> analyzedPos = new HashSet<>();
        Tree tree = new Tree(world, originPos);
        toAnalyzePos.add(new ToAnalyzePos(getFirstPositionFetcher(), originPos, originBlock, originPos, originBlock, TreeSectionType.LOG, 0));

        Predicate<BlockPos> boundingBoxSearch = getBoundingBoxSearch(originPos);
        Predicate<Block> adjacentPredicate = getAdjacentPredicate();

        try{
            while(!toAnalyzePos.isEmpty()){
                ToAnalyzePos analyzingPos = toAnalyzePos.remove();
                tree.addPart(analyzingPos.toTreePart());
                analyzedPos.add(analyzingPos);

                if(tree.getLogCount() > maxLogCount){
                    throw new TreeTooBigException();
                }

                Collection<ToAnalyzePos> potentialPositions = analyzingPos.getPositionFetcher().getPositions(world, originPos, analyzingPos);
                Collection<ToAnalyzePos> nextPositions = filterPotentialPos(boundingBoxSearch, adjacentPredicate, world, originPos, originBlock, analyzingPos, potentialPositions, analyzedPos);

                nextPositions.removeAll(analyzedPos);
                nextPositions.removeAll(toAnalyzePos);
                toAnalyzePos.addAll(nextPositions);
            }
        }
        catch(AbortSearchException e){
            return Optional.empty();
        }

        /*if(Config.COMMON.getTreesConfiguration().getBreakMode().shouldCheckLeavesAround()){
            int aroundRequired = Config.COMMON.getTreesConfiguration().getMinimumLeavesAroundRequired();
            if(tree.getTopMostLog()
                    .map(topLog -> getLeavesAround(world, topLog) < aroundRequired)
                    .orElse(true)){
                return empty();
            }
        }*/

        return Optional.of(tree);
    }

    private static Predicate<Block> getAdjacentPredicate(){
        Collection<Block> whitelist = Collections.EMPTY_LIST;
        Collection<Block> base = ConfigCache.getInstance().getAdjacentBlocksBase();

        if(whitelist.isEmpty()){
            return block -> true;
        }
        switch(Config.COMMON.getTreesConfiguration().getAdjacentStopMode()){
            case STOP_ALL:
                return block -> {
                    boolean whitelisted = whitelist.contains(block) || base.contains(block);
                    if(!whitelisted){
                        throw new AbortSearchException("Found block " + block + " that isn't whitelisted");
                    }
                    return true;
                };
            case STOP_BRANCH:
                return block -> whitelist.contains(block) || base.contains(block);
        }
        return block -> true;
    }

    private static Predicate<BlockPos> getBoundingBoxSearch(BlockPos originPos){
        int radius = 10;

        if(radius < 0) {
            return pos -> true;
        }

        int minX = originPos.getX() - radius;
        int maxX = originPos.getX() + radius;
        int minZ = originPos.getZ() - radius;
        int maxZ = originPos.getZ() + radius;

        return pos -> minX <= pos.getX()
                && maxX >= pos.getX()
                && minZ <= pos.getZ()
                && maxZ >= pos.getZ();
    }

    private static IPositionFetcher getFirstPositionFetcher(){
        DetectionMode detectionMode = Config.COMMON.getTreesConfiguration().getDetectionMode();
        if(detectionMode == ABOVE_CUT){
            return AbovePositionFetcher.getInstance();
        }
        if(detectionMode == ABOVE_Y){
            return AboveYFetcher.getInstance();
        }
        return BasicPositionFetcher.getInstance();
    }

    private static Collection<ToAnalyzePos> filterPotentialPos(Predicate<BlockPos> boundingBoxSearch,
                                                               Predicate<Block> adjacentPredicate,
                                                               World world,
                                                               BlockPos originPos,
                                                               Block originBlock,
                                                               ToAnalyzePos parent,
                                                               Collection<ToAnalyzePos> potentialPos,
                                                               Collection<ToAnalyzePos> analyzedPos){
        return potentialPos.stream()
                .filter(pos -> !analyzedPos.contains(pos))
                .filter(pos -> shouldIncludeInChain(boundingBoxSearch, originPos, originBlock, parent, pos))
                .filter(pos -> EnumSet.allOf(Direction.class).stream()
                        .map(direction -> pos.getCheckPos().relative(direction))
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .allMatch(adjacentPredicate))
                .collect(Collectors.toList());
    }

    private static long getLeavesAround(World world, BlockPos blockPos){
        return ALL_DIRECTIONS.stream()
                .map(blockPos::relative)
                .filter(testPos -> {
                    Block block = world.getBlockState(testPos).getBlock();
                    return isLeafBlock(block) || isNetherWartOrShroomlight(block) || isLeafNeedBreakBlock(block);
                })
                .count();
    }

    private static boolean shouldIncludeInChain(Predicate<BlockPos> boundingBoxSearch, BlockPos originPos, Block originBlock, ToAnalyzePos parent, ToAnalyzePos check){
        if(parent.getTreePartType() == LOG && isSameTree(originBlock, check) && boundingBoxSearch.test(check.getCheckPos())){
            return true;
        }
        if(Config.COMMON.getTreesConfiguration().isBreakNetherTreeWarts()){
            if(check.getTreePartType() == NETHER_WART){
                BlockPos checkBlockPos = check.getCheckPos();
                int dx = Math.abs(originPos.getX() - checkBlockPos.getX());
                int dz = Math.abs(originPos.getZ() - checkBlockPos.getZ());
                return dx <= 4 && dz <= 4;
            }
        }
        return check.getTreePartType() == LEAF_NEED_BREAK;
    }

    private static boolean isSameTree(Block parentLogBlock, ToAnalyzePos check){
        if(Config.COMMON.getTreesConfiguration().isAllowMixedLogs()){
            return check.getTreePartType() == LOG;
        }
        else{
            return check.getCheckBlock().equals(parentLogBlock);
        }
    }

}
