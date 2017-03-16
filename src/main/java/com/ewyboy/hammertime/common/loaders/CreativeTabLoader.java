package com.ewyboy.hammertime.common.loaders;

import com.ewyboy.hammertime.common.utillity.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabLoader {

    public static CreativeTabs HammerTimeTab = new CreativeTabs (Reference.HammerTimeCreativeTab) {
        double i = 0;
        public ItemStack getIconItemStack() {
            i+=0.01; if (i >= ToolLoader.hammers.length) i=0;
            return new ItemStack(ToolLoader.hammers[((int) Math.floor(i))]);
        }
        @Override
        public Item getTabIconItem() {return null;}
    };
}