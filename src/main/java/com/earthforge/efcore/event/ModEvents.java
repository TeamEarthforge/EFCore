package com.earthforge.efcore.event;


import com.earthforge.efcore.feature.skillpool.SkillPoolManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;


public class ModEvents {
    private ModEvents instance;
    public ModEvents()
        {
            FMLCommonHandler.instance().bus().register(this);
            //MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void onServerTick(ServerTickEvent event)
        {
            if(event.phase == TickEvent.Phase.END){SkillPoolManager.getInstance().tick();}
        }
        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event)
        {
            if(event.phase == TickEvent.Phase.END){}
        }

}
