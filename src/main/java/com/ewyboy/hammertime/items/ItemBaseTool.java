package com.ewyboy.hammertime.items;

import com.ewyboy.bibliotheca.common.interfaces.IItemRenderer;
import com.ewyboy.hammertime.loaders.ConfigLoader;
import com.ewyboy.hammertime.loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Created by EwyBoy
 */
public class ItemBaseTool extends ItemTool implements IItemRenderer {

    public ItemBaseTool(ToolMaterial toolMaterial, Set<Block> effective_on) {
        super(toolMaterial.getDamageVsEntity() + 1, -3.25f, toolMaterial, effective_on);
        setMaxDamage((int) (toolMaterial.getMaxUses() * ConfigLoader.durabilityModifier));
        setCreativeTab(CreativeTabLoader.tabBlink);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        return super.getStrVsBlock(stack, state) / 3;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Durability: " + (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage());
    }

    private int mineRadius = 1, mineDepth = 0;

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            RayTraceResult result = rayTrace(world, player, false);
            EnumFacing sideHit = result.sideHit;

            int xDist, yDist, zDist;
            yDist = xDist = zDist = mineRadius;

            switch (sideHit) {
                case UP:
                case DOWN: yDist = mineDepth; break;
                case NORTH:
                case SOUTH: zDist = mineDepth; break;
                case EAST:
                case WEST: xDist = mineDepth; break;
            }

            if (!player.isSneaking()) {
                for (int x = pos.getX() - xDist; x <= pos.getX() + xDist; x++) {
                    for (int y = pos.getY() - yDist; y <= pos.getY() + yDist; y++) {
                        for (int z = pos.getZ() - zDist; z <= pos.getZ() + zDist; z++) {
                            BlockPos targetPos = new BlockPos(x, y, z);
                            IBlockState targetBlock = world.getBlockState(targetPos);
                            if (canHarvestBlock(targetBlock, player.getHeldItem(EnumHand.MAIN_HAND))) {
                                if ((stack.getMaxDamage() - stack.getItemDamage()) >= 1 && targetBlock.getBlock() != Blocks.BEDROCK) {
                                    if (targetBlock.getBlock().getExpDrop(targetBlock, world, targetPos, 0) > 0) {
                                        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
                                            world.spawnEntity(new EntityXPOrb(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, world.getBlockState(pos).getBlock().getExpDrop(targetBlock, world, targetPos, 0)));
                                        }
                                    }
                                    world.destroyBlock(new BlockPos(x, y, z), true);
                                }
                                stack.damageItem(1, player);
                            }
                        }
                    }
                }
            } else {
                stack.damageItem(1, player);
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
