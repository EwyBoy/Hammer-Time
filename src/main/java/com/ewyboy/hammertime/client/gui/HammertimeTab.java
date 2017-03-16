package com.ewyboy.hammertime.client.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HammertimeTab extends CreativeTabs {

    public static final HammertimeTab HAMMERTIME = new HammertimeTab();

    public HammertimeTab() {
        super("hammertime.name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Items.ACACIA_BOAT;
    }

}
