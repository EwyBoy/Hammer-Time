package com.ewyboy.hammertime.Rendering.Renderers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ModelRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        //return helper != ItemRendererHelper.ENTITY_BOBBING && helper != ItemRendererHelper.ENTITY_ROTATION;
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {}
}
