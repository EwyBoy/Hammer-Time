package com.ewyboy.hammertime.Rendering.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LumberAxeModel extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;
    public ModelRenderer shape8;
    public ModelRenderer shape8_1;
    public ModelRenderer shape10;
    public ModelRenderer shape11;

    public LumberAxeModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape4 = new ModelRenderer(this, 56, 0);
        this.shape4.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.shape4.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.shape1 = new ModelRenderer(this, 56, 0);
        this.shape1.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.shape1.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.shape11 = new ModelRenderer(this, 0, 27);
        this.shape11.setRotationPoint(0.0F, 7.5F, -2.0F);
        this.shape11.addBox(-1.0F, 0.0F, 0.0F, 2, 1, 4, 0.0F);
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.shape2.addBox(-0.5F, 0.0F, -0.5F, 1, 11, 1, 0.0F);
        this.shape3 = new ModelRenderer(this, 0, 54);
        this.shape3.setRotationPoint(0.0F, 8.0F, 1.0F);
        this.shape3.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 4, 0.0F);
        this.shape8_1 = new ModelRenderer(this, 0, 24);
        this.shape8_1.setRotationPoint(1.0F, 9.0F, 3.0F);
        this.shape8_1.addBox(-1.11F, -1.0F, 0.35F, 2, 3, 2, 0.0F);
        this.setRotateAngle(shape8_1, 0.0F, -2.356194490192345F, 0.0F);
        this.shape10 = new ModelRenderer(this, 8, 25);
        this.shape10.setRotationPoint(0.0F, 10.0F, -2.0F);
        this.shape10.addBox(-1.0F, -2.5F, -0.5F, 2, 3, 1, 0.0F);
        this.shape8 = new ModelRenderer(this, 0, 24);
        this.shape8.setRotationPoint(-1.0F, 9.0F, 3.0F);
        this.shape8.addBox(-0.9F, -1.0F, 0.35F, 2, 3, 2, 0.0F);
        this.setRotateAngle(shape8, 0.0F, 2.356194490192345F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape4.render(f5);
        this.shape1.render(f5);
        this.shape11.render(f5);
        this.shape2.render(f5);
        this.shape3.render(f5);
        this.shape8_1.render(f5);
        this.shape10.render(f5);
        this.shape8.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
