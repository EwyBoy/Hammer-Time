package com.ewyboy.hammertime.Rendering.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SickleModel extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape4;
    public ModelRenderer shape8;
    public ModelRenderer shape9;
    public ModelRenderer shape10;
    public ModelRenderer shape9_1;
    public ModelRenderer shape8_1;
    public ModelRenderer shape9_2;
    public ModelRenderer shape15;

    public SickleModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.shape9_2 = new ModelRenderer(this, 11, 24);
        this.shape9_2.setRotationPoint(-1.5F, 12.0F, 0.0F);
        this.shape9_2.addBox(-0.5F, -2.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape9_2, 0.0F, 0.0F, -2.356194490192345F);
        this.shape8 = new ModelRenderer(this, 5, 25);
        this.shape8.setRotationPoint(1.0F, 10.8F, 0.0F);
        this.shape8.addBox(-1.5F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
        this.shape9_1 = new ModelRenderer(this, 11, 24);
        this.shape9_1.setRotationPoint(-1.5F, 16.0F, 0.0F);
        this.shape9_1.addBox(-0.5F, -2.0F, -0.5F, 1, 4, 1, 0.0F);
        this.setRotateAngle(shape9_1, 0.0F, 0.0F, -0.7853981633974483F);
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.shape2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
        this.shape8_1 = new ModelRenderer(this, 5, 25);
        this.shape8_1.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.shape8_1.addBox(-0.5F, 0.0F, -0.5F, 1, 1, 1, 0.0F);
        this.shape10 = new ModelRenderer(this, 0, 25);
        this.shape10.setRotationPoint(-2.8F, 14.0F, 0.0F);
        this.shape10.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
        this.shape1 = new ModelRenderer(this, 56, 0);
        this.shape1.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.shape1.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.shape4 = new ModelRenderer(this, 56, 0);
        this.shape4.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.shape4.addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.shape9 = new ModelRenderer(this, 11, 24);
        this.shape9.setRotationPoint(2.9F, 11.3F, 0.0F);
        this.shape9.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);
        this.setRotateAngle(shape9, 0.0F, 0.0F, 2.356194490192345F);
        this.shape15 = new ModelRenderer(this, 0, 21);
        this.shape15.setRotationPoint(3.45F, 12.1F, 0.0F);
        this.shape15.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.setRotateAngle(shape15, 0.0F, 0.0F, 1.5707963267948966F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape9_2.render(f5);
        this.shape8.render(f5);
        this.shape9_1.render(f5);
        this.shape2.render(f5);
        this.shape8_1.render(f5);
        this.shape10.render(f5);
        this.shape1.render(f5);
        this.shape4.render(f5);
        this.shape9.render(f5);
        this.shape15.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
