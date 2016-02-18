package com.ewyboy.hammertime.Tools;

import com.ewyboy.hammertime.Loaders.ConfigLoader;
import com.ewyboy.hammertime.Loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class Sickle extends BaseTool {

    public Sickle(ToolMaterial material, String name) {
        super(material);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabLoader.HammerTimeTab);
    }

    private static int mineRadius = 3, mineDepth = 1;
    private static Material[] materials = {Material.vine, Material.circuits, Material.coral, Material.plants, Material.vine, Material.leaves, Material.web};

    public boolean isEffective (Material material) {
        for (Material m : materials)
            if (m == material)
                return true;
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (side != 0) {
            final Block dirt = world.getBlock(x,y,z);
            if (dirt == Blocks.dirt || dirt == Blocks.grass || dirt == Blocks.mycelium) {
                if (player.isSneaking()) {world.setBlock(x,y,z,Blocks.farmland); stack.damageItem(ConfigLoader.durabilityMultiplier,player);} else {
                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), dirt.stepSound.getStepResourcePath(), (dirt.stepSound.getVolume() + 1.0F) / 2.0F, dirt.stepSound.getPitch() * 0.8F);
                    for (int posX=-1; posX<2; posX++) for (int posZ=-1; posZ<2; posZ++) world.setBlock(x+posX,y,z+posZ,Blocks.farmland); stack.damageItem(ConfigLoader.durabilityMultiplier,player);
                }
            }
        }
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    protected void breakExtraBlock(World world, int x, int y, int z, int sidehit, EntityPlayer playerEntity, int refX, int refY, int refZ) {
        if(world.isAirBlock(x, y, z)) return;
        if(!(playerEntity instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) playerEntity;
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if(!isEffective(block.getMaterial())) return;

        Block refBlock = world.getBlock(refX, refY, refZ);
        float refStrength = ForgeHooks.blockStrength(refBlock, player, world, refX, refY, refZ);
        float strength = ForgeHooks.blockStrength(block, player, world, x, y, z);

        if(!ForgeHooks.canHarvestBlock(block, player, meta) || refStrength / strength > 10f) return;
        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(), player, x, y, z);
        if(event.isCanceled()) return;
        if(player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, x, y, z, meta, player);
            if(block.removedByPlayer(world, player, x, y, z, false))
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            if(!world.isRemote) player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world)); return;
        }
        player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);
        if(! world.isRemote) {
            block.onBlockHarvested(world, x, y, z, meta, player);

            if(block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
                block.harvestBlock(world, player, x, y, z, meta);
                block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
                if(!player.capabilities.isCreativeMode)
                    stack.damageItem(1,player);
            }
            player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        } else {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
            if(block.removedByPlayer(world, player, x, y, z, true)) {
                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
            }
            ItemStack itemstack = player.getCurrentEquippedItem();
            if(itemstack != null) {
                itemstack.func_150999_a(world, block, x, y, z, player);
                if(itemstack.stackSize == 0) {
                    player.destroyCurrentEquippedItem();
                }
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }

    public boolean onBlockStartBreak (ItemStack stack, int x, int y, int z, EntityPlayer player) {
        Block block = player.worldObj.getBlock(x, y, z);
        MovingObjectPosition mop = raytraceFromEntity(player.worldObj, player, false, 4.5d);
        if (mop == null)
            return super.onBlockStartBreak(stack, x, y, z, player);
        int sideHit = mop.sideHit;

        if (!isEffective(block.getMaterial()))
            return super.onBlockStartBreak(stack, x, y, z, player);

        int xDist, yDist, zDist;
        yDist = xDist = zDist = mineRadius;

        switch (sideHit) {
            case 0:
            case 1:
                yDist = mineDepth;
                break;
            case 2:
            case 3:
                zDist = mineDepth;
                break;
            case 4:
            case 5:
                xDist = mineDepth;
                break;
        }
        for (int xPos = x - xDist; xPos <= x + xDist; xPos++)
            for (int yPos = y - yDist; yPos <= y + yDist; yPos++)
                for (int zPos = z - zDist; zPos <= z + zDist; zPos++) {
                    if (xPos == x && yPos == y && zPos == z)
                        continue;
                    if (!super.onBlockStartBreak(stack, xPos, yPos, zPos, player))
                        breakExtraBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, x, y, z);
                }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }
}
