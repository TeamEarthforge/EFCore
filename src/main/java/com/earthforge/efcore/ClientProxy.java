package com.earthforge.efcore;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.MinecraftForge;


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
