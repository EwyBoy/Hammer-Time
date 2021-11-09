package com.ewyboy.template.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class Tree {

    private final World world;
    private final Set<TreeSection> parts;
    private final Map<TreeSectionType, Integer> partCounts;
    private final BlockPos hitPos;

    public Tree(World world, BlockPos blockPos){
        this.world = world;
        this.hitPos = blockPos;
        this.parts = new LinkedHashSet<>();
        this.partCounts = new HashMap<>();
    }

    public void addPart(TreeSection treePart){
        this.parts.add(treePart);
        this.partCounts.compute(treePart.getTreePartType(), (key, value) -> {
            if(Objects.isNull(value)){
                return 1;
            }
            return value + 1;
        });
    }

    public int getBreakableCount(){
        return Arrays.stream(TreeSectionType.values())
                .filter(TreeSectionType::isBreakable)
                .mapToInt(this::getPartCount)
                .sum();
    }

    private int getPartCount(TreeSectionType treePartType){
        return this.partCounts.computeIfAbsent(treePartType, key -> 0);
    }

    public Optional<TreeSection> getLastSequencePart(){
        return getParts().stream().max(Comparator.comparingInt(TreeSection :: getSequence));
    }

    public Collection<TreeSection> getLogs(){
        return getParts().stream().filter(part -> part.getTreePartType() == TreeSectionType.LOG).collect(Collectors.toSet());
    }

    public Collection<TreeSection> getBreakableParts(){
        return getParts().stream()
                .filter(part -> part.getTreePartType().isBreakable())
                .collect(Collectors.toSet());
    }

    public int getLogCount(){
        return getPartCount(TreeSectionType.LOG);
    }

    public Optional<BlockPos> getTopMostLog(){
        return getLogs().stream()
                .map(TreeSection :: getBlockPos)
                .max(Comparator.comparingInt(BlockPos :: getY));
    }

    private Optional<BlockPos> getTopMostPart(){
        return getParts().stream()
                .map(TreeSection :: getBlockPos)
                .max(Comparator.comparingInt(BlockPos :: getY));
    }

    public Collection<TreeSection> getWarts(){
        return getParts().stream()
                .filter(part -> part.getTreePartType() == TreeSectionType.NETHER_WART)
                .collect(Collectors.toSet());
    }

    public BlockPos getHitPos(){
        return this.hitPos;
    }

    public World getWorld(){
        return this.world;
    }

    public Collection<TreeSection> getParts(){
        return this.parts;
    }

}
