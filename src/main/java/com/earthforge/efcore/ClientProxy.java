package com.earthforge.efcore;

import com.earthforge.efcore.dialog.data.DialogData;
import com.google.gson.Gson;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


public class ClientProxy extends CommonProxy {
    private GuiButton btn = new GuiButton(223,0,0,"abc");

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
