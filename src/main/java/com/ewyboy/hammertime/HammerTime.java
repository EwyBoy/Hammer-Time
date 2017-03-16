package com.ewyboy.hammertime;

import com.ewyboy.hammertime.common.loaders.ConfigLoader;
import com.ewyboy.hammertime.common.loaders.MaterialLoader;
import com.ewyboy.hammertime.common.loaders.RecipeLoader;
import com.ewyboy.hammertime.proxy.CommonProxy;
import com.ewyboy.hammertime.common.loaders.ToolLoader;
import com.ewyboy.hammertime.common.utillity.Logger;
import com.ewyboy.hammertime.common.utillity.Reference;
import com.google.common.base.Stopwatch;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.ID, name = Reference.Name, version = Reference.VersionBuildName, acceptedMinecraftVersions = "["+ Reference.MinecraftVersion+"]")
public class HammerTime {

    @Mod.Instance(Reference.ID)
    public static HammerTime instance;

    @SidedProxy(modId = Reference.ID, clientSide = Reference.clientProxyPath, serverSide = Reference.serverProxyPath)
    public static CommonProxy proxy;

    private double launchTime;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Pre-Initialization started");
                ConfigLoader.init(event.getSuggestedConfigurationFile());
                ToolLoader.loadTools();
                MaterialLoader.readMaterialProperties();
                RecipeLoader.loadRecipes();
                proxy.loadModels();
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Pre-Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        Logger.info("Pre-Initialization process successfully done");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Initialization started");
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
        Logger.info("Initialization process successfully done");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Post-Initialization started");
                launchTime += watch.elapsed(TimeUnit.MILLISECONDS);
            Logger.info("Post-Initialization finished after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
            Logger.info("Post-Initialization process successfully done");
        Logger.info("Total launch time for " + Reference.Name + " : " + launchTime + " ms");
    }
}
