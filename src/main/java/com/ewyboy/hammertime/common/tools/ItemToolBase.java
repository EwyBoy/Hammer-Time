package com.ewyboy.hammertime.common.tools;

import com.ewyboy.hammertime.client.gui.HammertimeTab;
import com.ewyboy.hammertime.common.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemToolBase extends ItemPickaxe {

    private String toolName;

    protected ItemToolBase(ToolMaterial material, String toolName) {
        super(material);
        this.toolName = toolName;
        this.register();
    }

    private void register(){
        String internalName = this.toolName + "_" + this.getToolMaterial().name();
        this.setCreativeTab(HammertimeTab.HAMMERTIME);
        this.setRegistryName(Reference.ModInfo.MODID, internalName);
        this.setUnlocalizedName(Reference.ModInfo.MODID + "." + internalName);
        GameRegistry.register(this);
    }

    protected RayTraceResult traceFromEntity(World world, EntityPlayer player, boolean par3, double range){
        float angel = 0.017453292F;
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f;

        if (!world.isRemote && player instanceof EntityPlayer){
            d1 += 1.62D;
        }

        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * angel - (float) Math.PI);
        float f4 = MathHelper.sin(- f2 * angel - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * angel);
        float f6 = MathHelper.sin(- f1 * angel);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;

        if (player instanceof EntityPlayerMP){
            d3 = ((EntityPlayerMP)player).interactionManager.getBlockReachDistance();
        }

        Vec3d vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3, !par3, par3);
    }

}
