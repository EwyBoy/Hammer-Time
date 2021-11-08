package com.ewyboy.template.tools;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.template.HammerTime;
import com.ewyboy.template.util.BlockBreaker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class Tool3x3 extends ToolItem implements ContentLoader.IHasCustomGroup {

    private final int radius;
    private final ItemTier tier;
    private final Set<Block> effective;

    public Tool3x3(int radius, float attackDmg, float attackSpeed, IItemTier tier, Set<Block> effective, Properties properties) {
        super(attackDmg, attackSpeed, tier, effective, properties);
        this.radius = radius;
        this.tier = (ItemTier) tier;
        this.effective = effective;
    }

    @Override
    public boolean canAttackBlock(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        int radius = this.radius;
        float localHardness = world.getBlockState(pos).getDestroySpeed(null, null);

        if (player.isCrouching()) {
            radius = 0;
        }

        if (player.getMainHandItem().isCorrectToolForDrops(world.getBlockState(pos))) {
            BlockBreaker.breakBlocksInRadius(world, player, radius, pos, (breakState) -> {
                double hardness = breakState.getDestroySpeed(null, null);
                boolean isEffective = player.getMainHandItem().isCorrectToolForDrops(breakState);
                boolean verifyHardness = hardness < localHardness * 5 && hardness > 0;

                return isEffective && verifyHardness;
            });
        }

        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return effective.contains(state.getMaterial()) ? this.speed : super.getDestroySpeed(stack, state);
    }

    @Override
    public ItemGroup getCustomItemGroup() {
        return HammerTime.ITEM_GROUP;
    }
}
