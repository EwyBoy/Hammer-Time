package com.ewyboy.hammertime.common.tools;

import com.google.common.collect.Sets;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.Set;

public class ItemToolHammer extends ItemToolBase {

    private final int mineRadius = 1;
    private final int mineDepth = 0;
    private final Set<Material> effectiveBlocks = Sets.newHashSet(
            Material.ROCK,
            Material.IRON,
            Material.ANVIL,
            Material.PACKED_ICE,
            Material.GLASS,
            Material.ICE,
            Material.PISTON
    );

    public ItemToolHammer(ToolMaterial material) {
        super(material, "hammer");
    }

    @SuppressWarnings("deprecation")
    private boolean isEffective(IBlockState state){
        for(Material material : this.effectiveBlocks){
            if(state.getBlock().getMaterial(state) == material){
                return true;
            }
        }

        return false;
    }

    private void breakExtraBlock(World world, BlockPos pos, int sidehit, EntityPlayer player, BlockPos refPos){
        if(world.isAirBlock(pos)){
            return;
        }
        if(!(player instanceof EntityPlayerMP)){
            return;
        }

        EntityPlayerMP playermp = (EntityPlayerMP)player;
        IBlockState state = world.getBlockState(pos);
        int meta = state.getBlock().getMetaFromState(state);

        if(!isEffective(state)){
            return;
        }

        IBlockState refState = world.getBlockState(refPos);
        float refStrength = ForgeHooks.blockStrength(refState, playermp, world, refPos);
        float strength = ForgeHooks.blockStrength(state, playermp, world, pos);

        if(!ForgeHooks.canHarvestBlock(state.getBlock(), playermp, world, pos) || refStrength / strength > 10F){
            return;
        }

        ForgeHooks.onBlockBreakEvent(world, playermp.interactionManager.getGameType(), playermp, pos);

        if(player.capabilities.isCreativeMode){
            state.getBlock().onBlockHarvested(world, pos, state, playermp);

            if(state.getBlock().removedByPlayer(state, world, pos, playermp, false)){
                state.getBlock().onBlockDestroyedByPlayer(world, pos, state);
            }

            if(!world.isRemote) {
                playermp.connection.sendPacket(new SPacketBlockChange(world, pos));
                return;
            }
        }

        playermp.getHeldItem(EnumHand.MAIN_HAND).onBlockDestroyed(world, state, pos, playermp);

        if(!world.isRemote) {
            state.getBlock().onBlockHarvested(world, pos, state, playermp);

            if(state.getBlock().removedByPlayer(state, world, pos, playermp, true)) {
                state.getBlock().onBlockDestroyedByPlayer(world, pos, state);
                TileEntity tile = null;

                if(world.getTileEntity(pos) != null){
                    tile = world.getTileEntity(pos);
                }

                state.getBlock().harvestBlock(world, playermp, pos, state, tile, null);
                state.getBlock().dropXpOnBlockBreak(world, pos, 0);
            }

            playermp.connection.sendPacket(new SPacketBlockChange(world, pos));
        }
        else {
            if(state.getBlock().removedByPlayer(state, world, pos, playermp, true)) {
                state.getBlock().onBlockDestroyedByPlayer(world, pos, state);
            }

            ItemStack itemstack = player.getActiveItemStack();

            if(itemstack != null){
                itemstack.onBlockDestroyed(world, state, pos, playermp);

                if(itemstack.stackSize == 0) {
                    player.getActiveItemStack().stackSize = 0;
                    player.inventory.markDirty();
                }
            }

            playermp.connection.processPlayerDigging(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        IBlockState state = player.worldObj.getBlockState(pos);
        RayTraceResult result = this.traceFromEntity(player.worldObj, player, false, 4.5D);

        if (result == null) {
            return super.onBlockStartBreak(stack, pos, player);
        }

        EnumFacing sideHit = result.sideHit;

        if (!isEffective(state)) {
            return super.onBlockStartBreak(stack, pos, player);
        }

        int xDist, yDist, zDist;
        yDist = xDist = zDist = mineRadius;

        switch (sideHit.ordinal()) {
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

        if (player.isSneaking()) {
            if (!super.onBlockStartBreak(stack, pos, player)) {
                this.breakExtraBlock(player.worldObj, pos, sideHit.ordinal(), player, pos);
            }
            else {
                for (int xPos = pos.getX() - xDist; xPos <= pos.getX() + xDist; xPos++) {
                    for (int yPos = pos.getY() - yDist; yPos <= pos.getY() + yDist; yPos++) {
                        for (int zPos = pos.getZ() - zDist; zPos <= pos.getZ() + zDist; zPos++) {
                            if (xPos == pos.getX() && yPos == pos.getY() && zPos == pos.getZ()) {
                                continue;
                            }
                            if (!super.onBlockStartBreak(stack, new BlockPos(xPos, yPos, zPos), player)) {
                                breakExtraBlock(player.worldObj, new BlockPos(xPos, yPos, zPos), sideHit.ordinal(), player, pos);
                            }
                        }
                    }
                }
            }
        }

        return super.onBlockStartBreak(stack, pos, player);
    }

}
