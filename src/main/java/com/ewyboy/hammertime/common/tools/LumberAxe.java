/**
package com.ewyboy.hammertime.common.tools;

import com.ewyboy.hammertime.common.loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

public class LumberAxe extends ItemAxe {

    public LumberAxe(ToolMaterial material, String name) {
        super(material);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabLoader.HammerTimeTab);
    }

    public int height;

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        World world = player.worldObj;
        final Block wood = world.getBlock(x,y,z);

        if (wood == null) return super.onBlockStartBreak(stack, x, y, z, player);

        if (wood.isWood(world,x,y,z)) {
            if (detectTree(world, x,y,z, wood)) {
                breakTree(world, x,y,z,x,y,z,wood,player,stack);
                stack.damageItem(height,player);
            } else {
                breakBlock(world, player, x,y,z);
                stack.damageItem(1,player);
            }
            return true;
        }
        return super.onBlockStartBreak(stack, x,y,z, player);
    }

    private boolean detectTree(World world, int x, int y, int z, Block wood) {
        height = y;
        boolean foundTop = false;
        do {
            height++;
            Block block = world.getBlock(x, height, z);
            if (block != wood) {
                height--;
                foundTop = true;
            }
        } while (!foundTop);

        int numLeaves = 0;
        if (height - y < 50) {
            for (int xPos = x - 1; xPos <= x + 1; xPos++) {
                for (int yPos = height - 1; yPos <= height + 1; yPos++) {
                    for (int zPos = z - 1; zPos <= z + 1; zPos++) {
                        Block leaves = world.getBlock(xPos, yPos, zPos);
                        if (leaves != null && leaves.isLeaves(world, xPos, yPos, zPos)) numLeaves++;
                    }
                }
            }
        }
        return numLeaves > 3;
    }

    private void breakBlock(World world,EntityPlayer player, int x, int y, int z) {
        Block localBlock = world.getBlock(x,y,z);
        int localMeta = world.getBlockMetadata(x,y,z);
        localBlock.harvestBlock(world,player,x,y,z, localMeta);
        world.setBlockToAir(x,y,z);
    }

    private void breakTree(World world, int x, int y, int z, int xStart, int yStart, int zStart, Block blockID, EntityPlayer player, ItemStack stack) {
        for (int posX = x-1; posX <= x+1; posX++) {
            for (int posY = y-1; posY <= y+1; posY++) {
                for (int posZ = z-1; posZ <= z+1; posZ++) {
                    Block localBlock = world.getBlock(posX,posY,posZ);
                    if (blockID == localBlock) {
                        int localMeta = world.getBlockMetadata(posX,posY,posZ);

                        boolean cancelHarvest = false;

                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(x,y,z, world, localBlock,localMeta, player);
                        event.setCanceled(cancelHarvest);
                        MinecraftForge.EVENT_BUS.post(event);
                        cancelHarvest = event.isCanceled();

                        if (cancelHarvest) breakTree(world,x,y,z,xStart,yStart,zStart,blockID,player, stack); else {
                            if (!player.capabilities.isCreativeMode) {
                                localBlock.harvestBlock(world, player, x,y,z, localMeta);
                                onBlockDestroyed(stack, world, localBlock, posX,posY,posZ, player);
                            }
                            world.setBlockToAir(posX,posY,posZ);
                            if (!world.isRemote) breakTree(world,posX,posY,posZ, xStart,yStart,zStart, blockID, player, stack);
                        }
                    }
                }
            }
        }
    }
}
 */
