package com.ewyboy.hammertime.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ExcavatorModel extends ModelBase {

    public ModelRenderer Rod;
    public ModelRenderer HandleBottom;
    public ModelRenderer HandelTop;
    public ModelRenderer HandelSide1;
    public ModelRenderer HandelSide2;
    public ModelRenderer Head;
    public ModelRenderer HeadSide2;
    public ModelRenderer HeadSide1;
    public ModelRenderer HeadBottom;
    public ModelRenderer HandleSupport;

    public ExcavatorModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.HandelSide2 = new ModelRenderer(this, 0, 28);
        this.HandelSide2.setRotationPoint(2.0F, 22.0F, 0.0F);
        this.HandelSide2.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
        this.HeadSide2 = new ModelRenderer(this, 0, 21);
        this.HeadSide2.setRotationPoint(2.0F, 6.0F, -0.5F);
        this.HeadSide2.addBox(0.0F, -2.0F, -0.5F, 1, 6, 1, 0.0F);
        this.HandleSupport = new ModelRenderer(this, 14, 9);
        this.HandleSupport.setRotationPoint(-1.0F, 19.0F, -0.5F);
        this.HandleSupport.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.HeadBottom = new ModelRenderer(this, 0, 21);
        this.HeadBottom.setRotationPoint(0.0F, 11.0F, -0.5F);
        this.HeadBottom.addBox(-2.0F, -2.0F, -0.5F, 4, 1, 1, 0.0F);
        this.HandleBottom = new ModelRenderer(this, 0, 28);
        this.HandleBottom.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.HandleBottom.addBox(-2.5F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.Rod = new ModelRenderer(this, 0, 0);
        this.Rod.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Rod.addBox(-0.5F, -5.0F, -0.5F, 1, 9, 1, 0.0F);
        this.HeadSide1 = new ModelRenderer(this, 0, 20);
        this.HeadSide1.setRotationPoint(-3.0F, 6.0F, -0.5F);
        this.HeadSide1.addBox(0.0F, -2.0F, -0.5F, 1, 6, 1, 0.0F);
        this.HandelTop = new ModelRenderer(this, 0, 28);
        this.HandelTop.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.HandelTop.addBox(-2.5F, 0.0F, -0.5F, 5, 1, 1, 0.0F);
        this.Head = new ModelRenderer(this, 0, 25);
        this.Head.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Head.addBox(-3.0F, -2.0F, 0.0F, 6, 6, 1, 0.0F);
        this.HandelSide1 = new ModelRenderer(this, 0, 28);
        this.HandelSide1.setRotationPoint(-2.0F, 22.0F, 0.0F);
        this.HandelSide1.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.HandelSide2.render(f5);
        this.HeadSide2.render(f5);
        this.HandleSupport.render(f5);
        this.HeadBottom.render(f5);
        this.HandleBottom.render(f5);
        this.Rod.render(f5);
        this.HeadSide1.render(f5);
        this.HandelTop.render(f5);
        this.Head.render(f5);
        this.HandelSide1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
