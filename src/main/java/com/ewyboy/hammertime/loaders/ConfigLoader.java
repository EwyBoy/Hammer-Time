package com.ewyboy.hammertime.loaders;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by EwyBoy
 **/
public class ConfigLoader {

    public static float durabilityModifier;

    public static void registerConfig(File file) {
        Configuration config = new Configuration(file);

        config.load();
            durabilityModifier = config.getFloat("Durability Modifier", Configuration.CATEGORY_GENERAL, 3.0f, 1.0f, Float.MAX_VALUE, "Takes the base durability value of the tool material and multiplies it with this value");
        config.save();
    }
}
