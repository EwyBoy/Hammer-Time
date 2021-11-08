package com.ewyboy.template;

import com.ewyboy.bibliotheca.common.loaders.ContentLoader;
import com.ewyboy.bibliotheca.common.loaders.ItemLoader;
import com.ewyboy.template.register.ToolRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static com.ewyboy.template.HammerTime.MOD_ID;

@Mod(MOD_ID)
public class HammerTime {

    public static final String MOD_ID = "hammertime";

    public HammerTime() {
        ContentLoader.init(MOD_ID, ITEM_GROUP, ToolRegister.BLOCKS.class, ToolRegister.TOOLS.class, ToolRegister.TILES.class);
    }

    public static final ItemGroup ITEM_GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ToolRegister.TOOLS.NETHERITE_HAMMER);
        }
    };


}
