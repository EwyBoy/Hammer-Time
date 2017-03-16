package com.ewyboy.hammertime.common.loaders;

import com.ewyboy.hammertime.common.tools.ItemToolHammer;
import net.minecraft.item.Item;

public class ToolLoader {

    public static Item hammerWood;

    public static void preInit(){
        hammerWood = new ItemToolHammer(Item.ToolMaterial.WOOD);
    }

}
