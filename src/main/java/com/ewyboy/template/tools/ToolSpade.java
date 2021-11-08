package com.ewyboy.template.tools;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.template.HammerTime;
import com.ewyboy.template.util.BlockBreaker;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.List;
import java.util.Set;

public class ToolSpade extends Tool3x3 {

    private static final Set<Block> DIGGABLES = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
    private static final Set<Block> FLATTENABLES = Sets.newHashSet(Blocks.GRASS_BLOCK);

    public ToolSpade(float attackDmg, float attackSpeed, IItemTier tier) {
        super(1, attackDmg, attackSpeed, tier, DIGGABLES, new Properties().addToolType(ToolType.SHOVEL, tier.getLevel()).tab(HammerTime.ITEM_GROUP));
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos blockpos = ctx.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);

        if (ctx.getClickedFace() == Direction.DOWN) {
            return ActionResultType.PASS;
        } else {
            PlayerEntity playerentity = ctx.getPlayer();

            List<BlockPos> flattenedBlocks = BlockBreaker.getArea(1, ctx.getClickedPos());

            BlockState blockstate1 = blockstate.getToolModifiedState(world, blockpos, playerentity, ctx.getItemInHand(), ToolType.SHOVEL);
            BlockState blockstate2 = null;

            if (blockstate1 != null && world.isEmptyBlock(blockpos.above())) {
                world.playSound(playerentity, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockstate2 = blockstate1;
            } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
                if (!world.isClientSide()) {
                    world.levelEvent(null, 1009, blockpos, 0);
                }

                CampfireBlock.dowse(world, blockpos, blockstate);
                blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.FALSE);
            }

            if (blockstate2 != null) {
                if (!world.isClientSide) {
                    for (BlockPos pos : flattenedBlocks) {
                        if (FLATTENABLES.contains(world.getBlockState(pos).getBlock())) {
                            world.setBlock(pos, blockstate2, 11);
                            if (playerentity != null) {
                                ctx.getItemInHand().hurtAndBreak(1, playerentity, (player) -> player.broadcastBreakEvent(ctx.getHand()));
                            }
                        }
                    }
                }

                return ActionResultType.sidedSuccess(world.isClientSide);
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        int tier = this.getTier().getLevel();

        if (state.getHarvestTool() == ToolType.SHOVEL) {
            return tier >= state.getHarvestLevel();
        }

        return state.getMaterial() == Material.SAND      ||
               state.getMaterial() == Material.CLAY      ||
               state.getMaterial() == Material.DIRT      ||
               state.getMaterial() == Material.TOP_SNOW  ||
               state.getMaterial() == Material.SNOW;
    }
}
