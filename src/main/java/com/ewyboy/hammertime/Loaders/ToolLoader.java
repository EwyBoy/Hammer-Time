package com.ewyboy.hammertime.Loaders;

import com.ewyboy.hammertime.Tools.Excavator;
import com.ewyboy.hammertime.Tools.Hammer;
import com.ewyboy.hammertime.Tools.LumberAxe;
import com.ewyboy.hammertime.Tools.Sickle;
import com.ewyboy.hammertime.Utillity.Logger;
import com.ewyboy.hammertime.Utillity.StringMap;
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
                    hammers[i] = new Hammer(MaterialLoader.Materials[i], StringMap.Hammers[i]);
                    GameRegistry.registerItem(hammers[i], StringMap.Hammers[i]);
                        log(StringMap.Hammers[i]);
                    excavators[i] = new Excavator(MaterialLoader.Materials[i], StringMap.Excavators[i]);
                    GameRegistry.registerItem(excavators[i], StringMap.Excavators[i]);
                        log(StringMap.Excavators[i]);
                    lumberAxes[i] = new LumberAxe(MaterialLoader.Materials[i], StringMap.LumberAxes[i]);
                    GameRegistry.registerItem(lumberAxes[i], StringMap.LumberAxes[i]);
                        log(StringMap.LumberAxes[i]);
                    sickles[i] = new Sickle(MaterialLoader.Materials[i], StringMap.Sickles[i]);
                    GameRegistry.registerItem(sickles[i], StringMap.Sickles[i]);
                        log(StringMap.Sickles[i]);
                }
        Logger.info("Loading tools finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
}