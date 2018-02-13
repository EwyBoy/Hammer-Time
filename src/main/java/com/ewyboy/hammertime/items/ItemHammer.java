package com.ewyboy.hammertime.items;

import com.ewyboy.bibliotheca.common.interfaces.IItemRenderer;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class ItemHammer extends ItemBaseTool implements IItemRenderer {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE);

    public ItemHammer(ToolMaterial toolMaterial) {
        super(toolMaterial, EFFECTIVE_ON);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        Block block = blockIn.getBlock();
        if (block == Blocks.OBSIDIAN) {
            return this.toolMaterial.getHarvestLevel() == 3;
        } else if (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE) {
            if (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK) {
                if (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE) {
                    if (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE) {
                        if (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE) {
                            if (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE) {
                                Material material = blockIn.getMaterial();
                                if (material == Material.ROCK) {
                                    return true;
                                }
                                else if (material == Material.IRON) {
                                    return true;
                                } else {
                                    return material == Material.ANVIL;
                                }
                            } else {
                                return this.toolMaterial.getHarvestLevel() >= 2;
                            }
                        } else {
                            return this.toolMaterial.getHarvestLevel() >= 1;
                        }
                    } else {
                        return this.toolMaterial.getHarvestLevel() >= 1;
                    }
                } else {
                    return this.toolMaterial.getHarvestLevel() >= 2;
                }
            } else {
                return this.toolMaterial.getHarvestLevel() >= 2;
            }
        } else {
            return this.toolMaterial.getHarvestLevel() >= 2;
        }
    }
}
