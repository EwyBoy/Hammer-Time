package com.ewyboy.hammertime.Loaders;

import com.ewyboy.hammertime.Utillity.StringMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabLoader {

    public static CreativeTabs HammerTimeTab = new CreativeTabs (StringMap.HammerTimeCreativeTab) {
        double i = 0;
        public ItemStack getIconItemStack() {
            i+=0.01; if (i >= ToolLoader.hammers.length) i=0;
            return new ItemStack(ToolLoader.hammers[((int) Math.floor(i))]);
        }
        @Override
        public Item getTabIconItem() {return null;}
    };
}