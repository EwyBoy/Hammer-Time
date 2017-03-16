package com.ewyboy.hammertime.client.renderers;

import com.ewyboy.hammertime.client.models.ExcavatorModel;
import com.ewyboy.hammertime.common.utillity.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ExcavatorRenderer extends ModelRenderer {

    public ExcavatorModel model;
    private String textureName;

    public ExcavatorRenderer(String textureName) {
        model = new ExcavatorModel();
        this.textureName = textureName;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        switch (type) {
            case EQUIPPED:
                GL11.glScalef(-2.5f, -2.5f, 2.5f);
                GL11.glTranslatef(0.7f, -0.125f, -0.7f);
                GL11.glRotatef(180, 0,1,0);
                GL11.glRotatef(-90,1,0,0);
                GL11.glRotatef(-45,0,0,1);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glScalef(2.0f,2.0f,2.0f);
                GL11.glTranslatef(0.8f, 0.535f, -0.7f);
                GL11.glRotatef(180, 1, 0, 1);
                GL11.glRotatef(130, 0, 1, 0);
                GL11.glRotatef(180, 0,1,0);
                GL11.glRotatef(-85, 1,0,0);
                break;
            case INVENTORY:
                GL11.glScalef(1.5f,1.15f,1.5f);
                GL11.glTranslatef(0, 0.825f, 0);
                GL11.glRotatef(180, 1, 0, 1);
                GL11.glRotatef(180, 0,1,0);
                break;
            case ENTITY:
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                GL11.glTranslatef(0, -0.25f, 0);
                break;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.ID + ":" + "textures/models/" + textureName + ".png"));
        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
