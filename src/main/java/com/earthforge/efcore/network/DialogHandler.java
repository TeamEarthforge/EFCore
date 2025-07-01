package com.earthforge.efcore.network;

import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.dialog.DialogManager;
import com.earthforge.efcore.dialog.data.DialogData;
import com.earthforge.efcore.dialog.gui.GuiDialog;
import com.google.gson.*;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DialogHandler implements IMessageHandler<DialogPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public static void ApplyDialog(JsonObject data) {
        Minecraft mc = Minecraft.getMinecraft();
        String type = data.get("type").getAsString();
        List<DialogData> dialogData = new ArrayList<>();
        if(type.equals("S2CText")){

            JsonArray array = data.getAsJsonArray("content");
            for(int i = 0; i < array.size(); i++){
                JsonObject item = array.get(i).getAsJsonObject();
                String name = item.get("name").getAsString();
                String text = item.get("text").getAsString();
                String side = item.get("side").getAsString();
                ResourceLocation portrait =  new ResourceLocation(EFCore.MODID,item.get("portrait").getAsString());
                dialogData.add(new DialogData(name,text,null,null,portrait,side));
            }
        } else if (type.equals("S2CTextWithOptions")) {
            //TODO
        }
        if(!dialogData.isEmpty()){
            if(mc.currentScreen instanceof GuiDialog){
            GuiDialog dialog = (GuiDialog) mc.currentScreen;
            dialog.setLevel(data.get("level").getAsInt());
            dialog.addData(dialogData);} else if (mc.currentScreen == null) {
                GuiDialog dialog = new GuiDialog();
                dialog.addData(dialogData);
                mc.displayGuiScreen(dialog);
            }
        }
    }
    @SideOnly(Side.SERVER)
    public static void ApplyDialog(JsonObject data, MessageContext ctx) {
        String type = data.get("type").getAsString();
        if(type.equals("C2SOption")){
            DialogManager.getInstance().levelX(ctx.getServerHandler().playerEntity, data.get("level").getAsInt());
        }


    }
    @Override
    public IMessage onMessage(DialogPacket message, MessageContext ctx) {
        JsonObject data = new JsonParser().parse(message.getData()).getAsJsonObject();
        if(ctx.side.isClient()){ApplyDialog(data);}else{ApplyDialog(data,ctx);}
        return null;
    }
}
