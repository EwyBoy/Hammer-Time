package com.ewyboy.template.tools;

import com.ewyboy.template.HammerTime;
import com.ewyboy.template.util.BlockBreaker;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
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

public class ToolSickle extends Tool3x3 {

    private static final Set<Block> DIGGABLES = ImmutableSet.of(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK, Blocks.HAY_BLOCK, Blocks.DRIED_KELP_BLOCK, Blocks.TARGET, Blocks.SHROOMLIGHT, Blocks.SPONGE, Blocks.WET_SPONGE, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.ACACIA_LEAVES, Blocks.BIRCH_LEAVES);
    private static final Set<Block> TILLABLES = ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH, Blocks.DIRT, Blocks.COARSE_DIRT);

    public ToolSickle(float attackDmg, float attackSpeed, IItemTier tier) {
        super(3, attackDmg, attackSpeed, tier, DIGGABLES, new Properties().addToolType(ToolType.HOE, tier.getLevel()).tab(HammerTime.ITEM_GROUP));
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        World world = ctx.getLevel();
        BlockPos blockpos = ctx.getClickedPos();

        List<BlockPos> brokenBlocks = BlockBreaker.getArea(1, ctx.getClickedPos());

        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(ctx);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;

        if (ctx.getClickedFace() != Direction.DOWN && world.isEmptyBlock(blockpos.above())) {
            BlockState blockstate = world.getBlockState(blockpos).getToolModifiedState(world, blockpos, ctx.getPlayer(), ctx.getItemInHand(), ToolType.HOE);
            if (blockstate != null) {
                PlayerEntity player = ctx.getPlayer();
                world.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isClientSide) {
                    for (BlockPos pos : brokenBlocks) {
                        if (TILLABLES.contains(world.getBlockState(pos).getBlock())) {
                            world.setBlock(pos, blockstate, 11);
                            if (player != null) {
                                ctx.getItemInHand().hurtAndBreak(1, player, (playerEntity) -> playerEntity.broadcastBreakEvent(ctx.getHand()));
                            }
                        }
                    }
                }
                return ActionResultType.sidedSuccess(world.isClientSide);
            }
        }
        return ActionResultType.PASS;
    }


    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        int tier = this.getTier().getLevel();

        if (state.getHarvestTool() == ToolType.HOE) {
            return tier >= state.getHarvestLevel();
        }

        return state.getMaterial() == Material.VEGETABLE                   ||
               state.getMaterial() == Material.BAMBOO                      ||
               state.getMaterial() == Material.BAMBOO_SAPLING              ||
               state.getMaterial() == Material.CACTUS                      ||
               state.getMaterial() == Material.CORAL                       ||
               state.getMaterial() == Material.LEAVES                      ||
               state.getMaterial() == Material.REPLACEABLE_FIREPROOF_PLANT ||
               state.getMaterial() == Material.WATER_PLANT                 ||
               state.getMaterial() == Material.PLANT                       ||
               state.getMaterial() == Material.REPLACEABLE_WATER_PLANT     ||
               state.getMaterial() == Material.REPLACEABLE_PLANT           ||
               state.getMaterial() == Material.WEB;
    }

}
