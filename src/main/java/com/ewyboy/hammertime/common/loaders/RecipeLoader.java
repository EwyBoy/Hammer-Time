/**
package com.ewyboy.hammertime.common.loaders;

import com.ewyboy.hammertime.common.utillity.Logger;
import com.ewyboy.hammertime.common.utillity.Reference;
import com.google.common.base.Stopwatch;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.concurrent.TimeUnit;

public class RecipeLoader {

    private static String stick = "stickWood";
    private static String[] craftingMaterial = {"plankWood", "cobblestone", "ingotIron", "ingotGold", "gemDiamond"};
    public static void log(String toolName) {Logger.info("  " + toolName + " recipe successfully loaded");}

    public static void loadRecipes() {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Loading recipes started");
                for (int i = 0; i < MaterialLoader.Materials.length; i++) {
                    GameRegistry.addRecipe(new ShapedOreRecipe(ToolLoader.hammers[i], "xxx", "xsx", " s ", 's', stick, 'x', craftingMaterial[i]).setMirrored(false));
                        log(Reference.Hammers[i]);
                    GameRegistry.addRecipe(new ShapedOreRecipe(ToolLoader.excavators[i], " xx", " sx", "s  ", 's', stick, 'x', craftingMaterial[i]).setMirrored(true));
                        log(Reference.Excavators[i]);
                    GameRegistry.addRecipe(new ShapedOreRecipe(ToolLoader.lumberAxes[i], "xxx", "xxs", "  s", 's', stick, 'x', craftingMaterial[i]).setMirrored(true));
                        log(Reference.LumberAxes[i]);
                    GameRegistry.addRecipe(new ShapedOreRecipe(ToolLoader.sickles[i], " x ", "  x", "sx ", 's', stick, 'x', craftingMaterial[i]).setMirrored(true));
                        log(Reference.Sickles[i]);
                }
        Logger.info("Loading recipes finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
}
 */
