package com.ewyboy.template.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BlockBreaker {

    public static void breakBlocksInRadius(World level, PlayerEntity player, int radius, BlockPos targetPos, IBreakValidator validator) {

        if (!level.isClientSide) {
            List<BlockPos> brokenBlocks = getBreakBlocks(level, player, radius, targetPos);
            ItemStack held = player.getMainHandItem();

            for (BlockPos pos : brokenBlocks) {
                BlockState state = level.getBlockState(pos);

                if (validator.canBreak(state)) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

                    if (serverPlayer.abilities.instabuild) {
                        if (state.removedByPlayer(level, pos, player, true, state.getFluidState())) {
                            state.getBlock().destroy(level, pos, state);
                        }
                    } else {
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(level, pos, state, player);
                        MinecraftForge.EVENT_BUS.post(event);

                        if (event.isCanceled()) {
                            //serverPlayer.connection.send(new SPacket(world, pos));

                            TileEntity tile = level.getBlockEntity(pos);
                            if (tile != null) {
                                SUpdateTileEntityPacket packet = tile.getUpdatePacket();
                                if (packet != null) {
                                    serverPlayer.connection.send(packet);
                                }
                            }
                        } else {
                            held.getItem().mineBlock(held, level, state, pos, player);
                            TileEntity tileEntity = level.getBlockEntity(pos);
                            state.getBlock().destroy(level, pos, state);
                            state.getBlock().playerDestroy(level, player, pos, state, tileEntity, held);
                            state.getBlock().popExperience((ServerWorld) level, pos, event.getExpToDrop());
                            level.removeBlock(pos, false);
                            level.levelEvent(2001, pos, Block.getId(state));

                            // TODO award / track stats?

                            //serverPlayer.connection.send(new SPacket);
                        }
                    }
                }
            }
        }
    }

    public static boolean detectTree(World level, BlockPos pos, Block wood) {
        int height = pos.getY();
        boolean foundTop = false;

        do {
            height++;
            BlockState block = level.getBlockState(new BlockPos(pos.getX(), height, pos.getZ()));
            if (block.getBlock() != wood) {
                height--;
                foundTop = true;
            }
        } while (!foundTop);

        int numLeaves = 0;

        if (height - pos.getY() < 50) {
            for (int xPos = pos.getX() - 1; xPos <= pos.getX() + 1; xPos++) {
                for (int yPos = height - 1; yPos <= height + 1; yPos++) {
                    for (int zPos = pos.getZ() - 1; zPos <= pos.getZ() + 1; zPos++) {
                        BlockState leaves = level.getBlockState(new BlockPos(xPos, yPos, zPos));
                        if (leaves.is(leaves.getBlock())) numLeaves++;
                    }
                }
            }
        }
        return numLeaves > 3;
    }

    public static void destroy(@Nonnull Tree tree, @Nonnull PlayerEntity player, @Nonnull ItemStack tool){
        World world = tree.getWorld();
        int toolUsesLeft = tool.isDamageableItem() ? (tool.getMaxDamage() - tool.getDamageValue()) : Integer.MAX_VALUE;

        if(toolUsesLeft <= 1){
            //player.sendMessage(new TranslationTextComponent("chat.fallingtree.prevented_break_tool"), NIL_UUID);
            return;
        }

        tree.getLastSequencePart()
                .map(TreeSection :: getBlockPos)
                .ifPresent(logBlock -> {
                    final BlockState logState = world.getBlockState(logBlock);
                    //player.awardStat(ITEM_USED.get(logState.getBlock().asItem()));
                    logState.getBlock().playerDestroy(world, player, tree.getHitPos(), logState, world.getBlockEntity(logBlock), tool);
                    world.removeBlock(logBlock, false);
                }
        );
    }


    // TODO remove ??
    private static void dropLoot(World level, List<ItemStack> stacks, BlockPos pos) {
        for (ItemStack stack : stacks) {
            ItemEntity loot = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            level.addFreshEntity(loot);
        }
    }

    public static List<BlockPos> getArea(int radius, BlockPos originPosition) {
        ArrayList<BlockPos> potentialArea = new ArrayList<>();
        ArrayList<BlockPos> positions = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                positions.add(new BlockPos(x, 0, z));
            }
        }

        positions.forEach(pos -> {
            if (pos.getY() == 0) {
                potentialArea.add(originPosition.offset(pos));
            }
            if (pos.getX() == 0) {
                potentialArea.add(originPosition.offset(pos));
            }
            if (pos.getZ() == 0) {
                potentialArea.add(originPosition.offset(pos));
            }
        });

        potentialArea.remove(originPosition);

        return potentialArea;
    }

    /**
     * Returns a list of the blocks that would be broken in breakInRadius, but doesn't break them.
     *
     * @param level  world of player
     * @param player player breaking
     * @param radius radius to break in
     * @return a list of blocks that would be broken with the given radius and tool
     */
    public static List<BlockPos> getBreakBlocks(World level, PlayerEntity player, int radius, BlockPos originPosition) {
        ArrayList<BlockPos> potentialBrokenBlocks = new ArrayList<>();

        Vector3d eyePosition = player.getEyePosition(1);
        Vector3d rotation = player.getViewVector(1);
        Vector3d combined = eyePosition.add(rotation.x * 5, rotation.y * 5, rotation.z * 5);

        BlockRayTraceResult rayTraceResult = level.clip(new RayTraceContext(eyePosition, combined, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));

        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
            Direction.Axis axis = rayTraceResult.getDirection().getAxis();
            ArrayList<BlockPos> positions = new ArrayList<>();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        positions.add(new BlockPos(x, y, z));
                    }
                }
            }

            for (BlockPos pos : positions) {
                if (axis == Direction.Axis.Y) {
                    if (pos.getY() == 0) {
                        potentialBrokenBlocks.add(originPosition.offset(pos));
                    }
                } else if (axis == Direction.Axis.X) {
                    if (pos.getX() == 0) {
                        potentialBrokenBlocks.add(originPosition.offset(pos));
                    }
                } else if (axis == Direction.Axis.Z) {
                    if (pos.getZ() == 0) {
                        potentialBrokenBlocks.add(originPosition.offset(pos));
                    }
                }
            }

            potentialBrokenBlocks.remove(originPosition);
        }

        return potentialBrokenBlocks;
    }

}
