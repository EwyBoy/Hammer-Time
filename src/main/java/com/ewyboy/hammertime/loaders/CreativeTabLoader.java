package com.ewyboy.hammertime.loaders;

import com.ewyboy.hammertime.register.Register;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import static com.ewyboy.hammertime.register.Reference.Info.MOD_ID;

/**
 * Created by EwyBoy
 **/
public class CreativeTabLoader {

    public static CreativeTabs tabBlink = new CreativeTabs(MOD_ID) {
        public ItemStack getIconItemStack() {
            return new ItemStack(Register.Items.hammerDiamond);
        }
        @Override
        public ItemStack getTabIconItem() {return null;}
    };
}
