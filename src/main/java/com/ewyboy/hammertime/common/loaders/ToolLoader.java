package com.ewyboy.hammertime.common.loaders;

import com.ewyboy.hammertime.common.tools.Excavator;
import com.ewyboy.hammertime.common.tools.Hammer;
import com.ewyboy.hammertime.common.tools.LumberAxe;
import com.ewyboy.hammertime.common.tools.Sickle;
import com.ewyboy.hammertime.common.utillity.Logger;
import com.ewyboy.hammertime.common.utillity.Reference;
import com.google.common.base.Stopwatch;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemTool;

import java.util.concurrent.TimeUnit;

public class ToolLoader {

    public static ItemTool hammerWood, hammerStone, hammerIron, hammerGold, hammerDiamond;
    public static final ItemTool[] hammers = {hammerWood, hammerStone, hammerIron, hammerGold, hammerDiamond};
    public static ItemTool excavatorWood, excavatorStone, excavatorIron, excavatorGold, excavatorDiamond;
    public static final ItemTool[] excavators = {excavatorWood, excavatorStone, excavatorIron, excavatorGold, excavatorDiamond};
    public static ItemTool lumberAxeWood, lumberAxeStone, lumberAxeIron, lumberAxeGold, lumberAxeDiamond;
    public static final ItemTool[] lumberAxes = {lumberAxeWood, lumberAxeStone, lumberAxeIron, lumberAxeGold, lumberAxeDiamond};
    public static ItemTool sickleWood, sickleStone, sickleIron, sickleGold, sickleDiamond;
    public static final ItemTool[] sickles = {sickleWood, sickleStone, sickleIron, sickleGold, sickleDiamond};

    public static void log(String toolName) {Logger.info("  " + toolName + " successfully loaded");}

    public static void loadTools() {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Loading tools started");
                for (int i = 0; i < MaterialLoader.Materials.length; i++) {
                    hammers[i] = new Hammer(MaterialLoader.Materials[i], Reference.Hammers[i]);
                    GameRegistry.registerItem(hammers[i], Reference.Hammers[i]);
                        log(Reference.Hammers[i]);
                    excavators[i] = new Excavator(MaterialLoader.Materials[i], Reference.Excavators[i]);
                    GameRegistry.registerItem(excavators[i], Reference.Excavators[i]);
                        log(Reference.Excavators[i]);
                    lumberAxes[i] = new LumberAxe(MaterialLoader.Materials[i], Reference.LumberAxes[i]);
                    GameRegistry.registerItem(lumberAxes[i], Reference.LumberAxes[i]);
                        log(Reference.LumberAxes[i]);
                    sickles[i] = new Sickle(MaterialLoader.Materials[i], Reference.Sickles[i]);
                    GameRegistry.registerItem(sickles[i], Reference.Sickles[i]);
                        log(Reference.Sickles[i]);
                }
        Logger.info("Loading tools finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
}