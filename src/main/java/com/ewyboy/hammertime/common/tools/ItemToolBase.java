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

    protected RayTraceResult traceFromEntity(World world, EntityPlayer player, boolean stopOnLiquids, double range){
        float angel = 0.017453292F;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch);
        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw);
        double x = player.prevPosX + (player.posX - player.prevPosX);
        double y = player.prevPosY + (player.posY - player.prevPosY);
        double z = player.prevPosZ + (player.posZ - player.prevPosZ);

        if (!world.isRemote && player instanceof EntityPlayer){
            y += 1.62D;
        }

        Vec3d vec3 = new Vec3d(x, y, z);
        float val1 = MathHelper.cos(-yaw * angel - (float)Math.PI);
        float val2 = MathHelper.sin(-yaw * angel - (float)Math.PI);
        float val3 = -MathHelper.cos(-pitch * angel);
        float val4 = MathHelper.sin(-pitch * angel);
        float val5 = val2 * val3;
        float val6 = val1 * val3;
        double rangeCopy = range;

        if (player instanceof EntityPlayerMP){
            rangeCopy = ((EntityPlayerMP)player).interactionManager.getBlockReachDistance();
        }

        Vec3d vec31 = vec3.addVector((double)val5 * rangeCopy, (double)val4 * rangeCopy, (double)val6 * rangeCopy);
        return world.rayTraceBlocks(vec3, vec31, stopOnLiquids, !stopOnLiquids, stopOnLiquids);
    }

}
