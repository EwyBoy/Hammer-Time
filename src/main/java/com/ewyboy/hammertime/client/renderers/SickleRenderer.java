package com.ewyboy.hammertime.client.renderers;

import com.ewyboy.hammertime.client.models.SickleModel;
import com.ewyboy.hammertime.common.utillity.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class SickleRenderer extends ModelRenderer {

    public SickleModel model;
    private String textureName;

    public SickleRenderer(String textureName) {
        model = new SickleModel();
        this.textureName = textureName;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        switch (type) {
            case EQUIPPED:
                GL11.glScalef(-2.5f, -2.5f, 2.5f);
                GL11.glTranslatef(0.25f, -0.75f, -0.575f);
                GL11.glRotatef(-90.0f, -1.0f, 1.0f, 0.0f);
                GL11.glRotatef(180f,0.0f,1.0f,0.0f);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glScalef(2.0f,2.0f,2.0f);
                GL11.glTranslatef(0.1f, 1.7f, 0.35f);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                GL11.glRotatef(130, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(155f,0.0f,1.0f,0.0f);
                GL11.glRotatef(-70f,0.0f,1.0f,0.0f);
                break;
            case INVENTORY:
                GL11.glScalef(1.75f,1.75f,1.75f);
                GL11.glTranslatef(0,1.075f,0);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                break;
            case ENTITY:
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                GL11.glTranslatef(0, 1.5f, 0);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                break;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Reference.ID + ":" + "textures/models/" + textureName + ".png"));
        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
