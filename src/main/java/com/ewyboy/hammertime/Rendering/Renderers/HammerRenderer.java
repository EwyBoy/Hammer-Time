package com.ewyboy.hammertime.Rendering.Renderers;

import com.ewyboy.hammertime.Loaders.ToolLoader;
import com.ewyboy.hammertime.Rendering.Models.HammerModel;
import com.ewyboy.hammertime.Tools.Hammer;
import com.ewyboy.hammertime.Utillity.StringMap;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HammerRenderer extends ModelRenderer {

    public HammerModel model;
    private String textureName;

    public HammerRenderer(String textureName) {
        model = new HammerModel();
        this.textureName = textureName;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        switch (type) {
            case EQUIPPED:
                GL11.glScalef(-2.5f, -2.5f, 2.5f);
                GL11.glTranslatef(0.25f, -0.75f, -0.575f);
                GL11.glRotatef(-90.0f,-1.0f,1.0f,0.0f);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glScalef(2.0f,2.0f,2.0f);
                GL11.glTranslatef(0.1f, 1.5f, 0.2f);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                GL11.glRotatef(130, 0.0f, 1.0f, 0.0f);
                break;
            case INVENTORY:
                GL11.glScalef(1.5f,1.25f,1.5f);
                GL11.glTranslatef(0,0.9f,0);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                break;
            case ENTITY:
                GL11.glScalef(1.5f, 1.5f, 1.5f);
                GL11.glTranslatef(0, 1.5f, 0);
                GL11.glRotatef(180, 1.0f, 0.0f, 1.0f);
                break;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(StringMap.ID + ":" + "textures/models/" + textureName + ".png"));
        this.model.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }
}
