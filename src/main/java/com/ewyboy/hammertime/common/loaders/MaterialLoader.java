package com.ewyboy.hammertime.common.loaders;

import com.ewyboy.hammertime.common.utillity.Logger;
import com.ewyboy.hammertime.common.utillity.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class MaterialLoader {
    public static Item.ToolMaterial Wood    = EnumHelper.addToolMaterial(Reference.MaterialWood, 0, 59 * ConfigLoader.durabilityMultiplier, 2.0F / ConfigLoader.efficiencyReducerMultiplier, 0.0F, 15);
    public static Item.ToolMaterial Stone   = EnumHelper.addToolMaterial(Reference.MaterialStone, 1, 131 * ConfigLoader.durabilityMultiplier, 4.0F / ConfigLoader.efficiencyReducerMultiplier, 1.0F, 5);
    public static Item.ToolMaterial Iron    = EnumHelper.addToolMaterial(Reference.MaterialIron, 2, 250 * ConfigLoader.durabilityMultiplier, 6.0f / ConfigLoader.efficiencyReducerMultiplier, 2.0f, 14);
    public static Item.ToolMaterial Gold    = EnumHelper.addToolMaterial(Reference.MaterialGold, 0, 32 * ConfigLoader.durabilityMultiplier, 12.0F / ConfigLoader.efficiencyReducerMultiplier, 0.0F, 22);
    public static Item.ToolMaterial Diamond = EnumHelper.addToolMaterial(Reference.MaterialDiamond, 3, 1561 * ConfigLoader.durabilityMultiplier, 8.0F / ConfigLoader.efficiencyReducerMultiplier, 3.0F, 10);

    public static Item.ToolMaterial Materials[] = {Wood,Stone,Iron,Gold,Diamond};

    public static void readMaterialProperties() {
        String spacing = "    ", split = " | ";
        Logger.info("Valid material types found: " + Materials.length);

        for (int i = 0; i < Materials.length; i++) {
            Logger.info(spacing +
                    "Material: " + Materials[i] + split
                    + "Harvest level: " + Materials[i].getHarvestLevel() + split
                    + "Durability: " + Materials[i].getMaxUses() + split
                    + "Efficiency: " + Materials[i].getEfficiencyOnProperMaterial() + split
                    + "Damage: " + Materials[i].getDamageVsEntity() + split
                    + "Enchantability: " + Materials[i].getEnchantability()
            );
        }
    }
}
