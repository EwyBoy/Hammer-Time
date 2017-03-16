package com.ewyboy.hammertime.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelHammer extends ModelBase {

    public ModelRenderer Rod;
    public ModelRenderer CapBottom;
    public ModelRenderer CapTop;
    public ModelRenderer HammerHead;
    public ModelRenderer HammerCapTop;
    public ModelRenderer HammerCapBack;
    public ModelRenderer HammerCapFront;

    public ModelHammer() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Rod = new ModelRenderer(this, 0, 0);
        this.Rod.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.Rod.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
        this.CapBottom = new ModelRenderer(this, 24, 0);
        this.CapBottom.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.CapBottom.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.HammerCapFront = new ModelRenderer(this, 22, 28);
        this.HammerCapFront.setRotationPoint(0.0F, 9.0F, 3.0F);
        this.HammerCapFront.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1, 0.0F);
        this.HammerCapTop = new ModelRenderer(this, 14, 15);
        this.HammerCapTop.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HammerCapTop.addBox(-1.5F, -0.5F, -2.5F, 3, 1, 5, 0.0F);
        this.CapTop = new ModelRenderer(this, 24, 0);
        this.CapTop.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.CapTop.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.HammerHead = new ModelRenderer(this, 0, 22);
        this.HammerHead.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HammerHead.addBox(-2.0F, 0.0F, -3.0F, 4, 4, 6, 0.0F);
        this.HammerCapBack = new ModelRenderer(this, 22, 28);
        this.HammerCapBack.setRotationPoint(0.0F, 9.0F, -3.0F);
        this.HammerCapBack.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Rod.render(f5);
        this.CapBottom.render(f5);
        this.HammerCapFront.render(f5);
        this.HammerCapTop.render(f5);
        this.CapTop.render(f5);
        this.HammerHead.render(f5);
        this.HammerCapBack.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
