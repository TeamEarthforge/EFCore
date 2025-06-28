package com.earthforge.efcore;

import com.earthforge.efcore.dialog.DialogGui;
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

    @SubscribeEvent
    public void guiScreenshow(GuiScreenEvent.InitGuiEvent.Post event){
        if(event.gui instanceof GuiMainMenu){
            event.buttonList.add(btn);
        }
    }
    @SubscribeEvent
    public void guiClickButton(GuiScreenEvent.ActionPerformedEvent event){
        if(event.button == btn){
            try {
                // 从资源文件读取对话数据
                InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/efcore/dialog/abc.json");
                if (stream == null) {
                    throw new RuntimeException("Could not find dialog file");
                }
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                DialogData data = new Gson().fromJson(reader, DialogData.class);
                reader.close();

                // 显示对话框
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayGuiScreen(new DialogGui(mc.currentScreen, data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
