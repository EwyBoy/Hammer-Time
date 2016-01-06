package com.ewyboy.hammertime.Networking;

import com.ewyboy.hammertime.Loaders.MaterialLoader;
import com.ewyboy.hammertime.Loaders.ToolLoader;
import com.ewyboy.hammertime.Rendering.Renderers.ExcavatorRenderer;
import com.ewyboy.hammertime.Rendering.Renderers.HammerRenderer;
import com.ewyboy.hammertime.Rendering.Renderers.LumberAxeRenderer;
import com.ewyboy.hammertime.Rendering.Renderers.SickleRenderer;
import com.ewyboy.hammertime.Utillity.Logger;
import com.ewyboy.hammertime.Utillity.StringMap;
import com.google.common.base.Stopwatch;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.MinecraftForgeClient;

import java.util.concurrent.TimeUnit;

public class ClientProxy extends CommonProxy {

    public static void log(ItemTool tool) {Logger.info("  " + tool.getUnlocalizedName() + " successfully bound to renderer");}

    @Override
    public void loadModels() {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Binding tools to model renderer started");
                for (int i = 0; i < MaterialLoader.Materials.length; i++) {
                    MinecraftForgeClient.registerItemRenderer(ToolLoader.hammers[i], new HammerRenderer(StringMap.Materials[i]));
                        log(ToolLoader.hammers[i]);
                    MinecraftForgeClient.registerItemRenderer(ToolLoader.excavators[i], new ExcavatorRenderer(StringMap.Materials[i]));
                        log(ToolLoader.excavators[i]);
                    MinecraftForgeClient.registerItemRenderer(ToolLoader.lumberAxes[i], new LumberAxeRenderer(StringMap.Materials[i]));
                        log(ToolLoader.lumberAxes[i]);
                    MinecraftForgeClient.registerItemRenderer(ToolLoader.sickles[i], new SickleRenderer(StringMap.Materials[i]));
                        log(ToolLoader.sickles[i]);
                }
        Logger.info("Binding all tools to model renderer finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        }
    }
