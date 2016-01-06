package com.ewyboy.hammertime.Utillity;

public class StringMap {

    //Mod info
    public static final String ID = "hammertime";
    public static final String Name = "Hammer Time";
    public static final String MinecraftVersion = "1.7.10";
    public static final String VersionMajor = "1";
    public static final String VersionMinor = "3";
    public static final String VersionPatch = "5";
    public static final String VersionBuildName = Name + "-" + MinecraftVersion + "-" + VersionMajor + "." + VersionMinor + "." + VersionPatch;

    //Proxy Paths
    public static final String clientProxyPath = "com.ewyboy.hammertime.Networking.ClientProxy";
    public static final String serverProxyPath = "com.ewyboy.hammertime.Networking.CommonProxy";

    //CreativeTab
    public static final String HammerTimeCreativeTab = "HammerTimeCreativeTab";

    //Hammer Names
    public static final String HammerWood = "HammerWood";
    public static final String HammerStone = "HammerStone";
    public static final String HammerIron = "HammerIron";
    public static final String HammerGold = "HammerGold";
    public static final String HammerDiamond = "HammerDiamond";

    public static final String[] Hammers = {HammerWood, HammerStone, HammerIron, HammerGold, HammerDiamond};

    //Excavator Names
    public static final String ExcavatorWood = "ExcavatorWood";
    public static final String ExcavatorStone = "ExcavatorStone";
    public static final String ExcavatorIron = "ExcavatorIron";
    public static final String ExcavatorGold = "ExcavatorGold";
    public static final String ExcavatorDiamond = "ExcavatorDiamond";

    public static final String[] Excavators = {ExcavatorWood, ExcavatorStone, ExcavatorIron, ExcavatorGold, ExcavatorDiamond};

    //LumberAxeModel Names
    public static final String LumberAxeWood = "LumberAxeWood";
    public static final String LumberAxeStone = "LumberAxeStone";
    public static final String LumberAxeIron = "LumberAxeIron";
    public static final String LumberAxeGold = "LumberAxeGold";
    public static final String LumberAxeDiamond = "LumberAxeDiamond";

    public static final String[] LumberAxes = {LumberAxeWood, LumberAxeStone, LumberAxeIron, LumberAxeGold, LumberAxeDiamond};

    //Sickle Names
    public static final String SickleWood = "SickleWood";
    public static final String SickleStone = "SickleStone";
    public static final String SickleIron = "SickleIron";
    public static final String SickleGold = "SickleGold";
    public static final String SickleDiamond = "SickleDiamond";

    public static final String[] Sickles = {SickleWood, SickleStone, SickleIron, SickleGold, SickleDiamond};

    //Material Types Names
    public static final String MaterialWood = "Wood";
    public static final String MaterialStone = "Stone";
    public static final String MaterialIron = "Iron";
    public static final String MaterialGold = "Gold";
    public static final String MaterialDiamond = "Diamond";

    public static final String[] Materials = {MaterialWood, MaterialStone, MaterialIron, MaterialGold, MaterialDiamond};

    //Config Categories
    public static final String ConfigCategoryTogglables = "Togglables";
    public static final String ConfigCategoryTweaks = "Tweaks";
}
