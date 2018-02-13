package com.ewyboy.hammertime.register;

import com.ewyboy.hammertime.items.ItemAxe;
import com.ewyboy.hammertime.items.ItemHammer;
import com.ewyboy.hammertime.items.ItemShovel;
import net.minecraft.item.Item;

/**
 * Created by EwyBoy
 */
public class Register {

    public static class Items {
        public static ItemHammer hammerWood = new ItemHammer(Item.ToolMaterial.WOOD);
        public static ItemHammer hammerStone = new ItemHammer(Item.ToolMaterial.STONE);
        public static ItemHammer hammerIron = new ItemHammer(Item.ToolMaterial.IRON);
        public static ItemHammer hammerGold = new ItemHammer(Item.ToolMaterial.GOLD);
        public static ItemHammer hammerDiamond = new ItemHammer(Item.ToolMaterial.DIAMOND);

        public static ItemShovel shovelWood = new ItemShovel(Item.ToolMaterial.WOOD);
        public static ItemShovel shovelStone = new ItemShovel(Item.ToolMaterial.STONE);
        public static ItemShovel shovelIron = new ItemShovel(Item.ToolMaterial.IRON);
        public static ItemShovel shovelGold = new ItemShovel(Item.ToolMaterial.GOLD);
        public static ItemShovel shovelDiamond = new ItemShovel(Item.ToolMaterial.DIAMOND);

        public static ItemAxe axeWood = new ItemAxe(Item.ToolMaterial.WOOD);
        public static ItemAxe axeStone = new ItemAxe(Item.ToolMaterial.STONE);
        public static ItemAxe axeIron = new ItemAxe(Item.ToolMaterial.IRON);
        public static ItemAxe axeGold = new ItemAxe(Item.ToolMaterial.GOLD);
        public static ItemAxe axeDiamond = new ItemAxe(Item.ToolMaterial.DIAMOND);
    }

}
