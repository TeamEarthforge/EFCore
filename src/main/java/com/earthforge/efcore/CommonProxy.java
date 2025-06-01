package com.earthforge.efcore;

import com.earthforge.efcore.api.EFAPI;

import com.earthforge.efcore.network.DialogHandler;
import com.earthforge.efcore.network.DialogPacket;
import noppes.npcs.scripted.NpcAPI;

import com.earthforge.efcore.item.ModItems;
import com.earthforge.efcore.network.CameraHandler;
import com.earthforge.efcore.network.CameraPacket;

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

        new ModItems(event);

        EFCore.LOG.info(Config.greeting);
        EFCore.LOG.info("I am MyMod at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        NpcAPI.Instance().addGlobalObject("EFAPI", EFAPI.Instance());
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        chancel = NetworkRegistry.INSTANCE.newSimpleChannel(EFCore.MODID);
        chancel.registerMessage(CameraHandler.class, CameraPacket.class, 0, Side.CLIENT);
        chancel.registerMessage(DialogHandler.class, DialogPacket.class, 1, Side.CLIENT);
        /*
        String mn = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
            EnumConnectionState.class.getName(),
            "func_150756_b",
            Type.getMethodDescriptor(org.objectweb.asm.Type.VOID_TYPE, Type.INT_TYPE, Type.getType(Class.class)));
        try {
            Method regCPmsg = EnumConnectionState.class.getDeclaredMethod(mn, int.class, Class.class);
            regCPmsg.setAccessible(true);
            regCPmsg.invoke(EnumConnectionState.PLAY, 71, CameraPacket.class);
            regCPmsg.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}

}
