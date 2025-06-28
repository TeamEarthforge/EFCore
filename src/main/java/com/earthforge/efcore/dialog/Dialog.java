package com.earthforge.efcore.dialog;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.api.IDialog;
import com.earthforge.efcore.dialog.data.DialogData;

import com.earthforge.efcore.network.DialogPacket;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.scripted.CustomNPCsException;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class Dialog implements IDialog {
    ScriptContainer scripts;
    boolean enableScript = false;
    DialogData data;
    IPlayer<EntityPlayerMP> player;


    public Dialog() {}
    @Deprecated
    public void fromJsonDialog(String filename){
        try {
            File worldDir = new File(Minecraft.getMinecraft().mcDataDir, "saves");
            File dialogDir = new File(worldDir, MinecraftServer.getServer().getFolderName() + File.separator + "dialogs");

            if (!dialogDir.exists()) {
                dialogDir.mkdirs();
                return;
            }
            File dialogFile = new File(dialogDir, filename + ".json");
            if (dialogFile.exists()) {
                try (Reader reader = new InputStreamReader(new FileInputStream(dialogFile), StandardCharsets.UTF_8)) {
                    this.data = new Gson().fromJson(reader, DialogData.class);
                    }
            }
        } catch (IOException ignored) {}
    }
    public IPlayer<EntityPlayerMP> getPlayer() {return this.player; }

    public void enableScript(){
        if (ScriptContainer.Current == null) {
            throw new CustomNPCsException("Can only be called during scripts");
        } else {
            this.scripts = ScriptContainer.Current;
            this.enableScript = true;
        }
    }
    public void show(IPlayer<EntityPlayerMP> player){
        this.player = player;
        if (!enableScript){
            CommonProxy.getChancel().sendTo(new DialogPacket(data), player.getMCEntity());
        }else{
            this.start(player);
        }
    }
    public void sendText(String text, @Nullable String portrait){}
    public void sendTextWithOptions(String text,String selects, @Nullable String portrait){}
    public void dispose(){}
    private void start(IPlayer<EntityPlayerMP> player){
        DialogManager.getInstance().start(player.getMCEntity(),this);
    }

}
