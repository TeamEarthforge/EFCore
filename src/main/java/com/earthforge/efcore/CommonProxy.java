package com.earthforge.efcore;

import com.earthforge.efcore.packet.CameraHandler;
import com.earthforge.efcore.packet.CameraPacket;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
    private static SimpleNetworkWrapper chancel;

    public static SimpleNetworkWrapper getChancel() {
        return chancel;
    }

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        EFCore.LOG.info(Config.greeting);
        EFCore.LOG.info("I am MyMod at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        SimpleNetworkWrapper chancel = NetworkRegistry.INSTANCE.newSimpleChannel(EFCore.MODID);
        chancel.registerMessage(CameraHandler.class, CameraPacket.class, 0, Side.CLIENT);
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}


}
