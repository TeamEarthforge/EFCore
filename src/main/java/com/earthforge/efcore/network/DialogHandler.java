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

    public static void ApplyDialog(JsonObject data) {
        Minecraft mc = Minecraft.getMinecraft();
        String type = data.get("type").getAsString();
        List<DialogData> dialogData = new ArrayList<>();
        // 修改level处理为数组
        JsonArray levelArray = data.has("level") ? data.getAsJsonArray("level") : new JsonArray();
        List<JsonObject> options = new ArrayList<>();

        if (type.equals("S2CText")) {
            JsonArray array = data.getAsJsonArray("content");
            for (int i = 0; i < array.size(); i++) {
                JsonObject item = array.get(i).getAsJsonObject();
                dialogData.add(parseDialogData(item));
            }
        } else if (type.equals("S2CTextWithOptions")) {
            JsonArray contentArray = data.getAsJsonArray("content");
            for (int i = 0; i < contentArray.size(); i++) {
                JsonObject item = contentArray.get(i).getAsJsonObject();
                dialogData.add(parseDialogData(item));
            }

            JsonArray optionsArray = data.getAsJsonArray("options");
            for (int i = 0; i < optionsArray.size(); i++) {
                options.add(optionsArray.get(i).getAsJsonObject());
            }
        }

        if (!dialogData.isEmpty()) {
            if (mc.currentScreen instanceof GuiDialog) {
                GuiDialog dialog = (GuiDialog) mc.currentScreen;
                dialog.setLevel(0); // 修改为传递level数组
                dialog.addData(dialogData);
            } else if (mc.currentScreen == null) {
                GuiDialog dialog = new GuiDialog();
                dialog.setLevel(0); // 修改为传递level数组
                dialog.addData(dialogData);
                mc.displayGuiScreen(dialog);
            }
        }
    }
    private static DialogData parseDialogData(JsonObject item) {
        String name = item.get("name").getAsString();
        String text = item.get("text").getAsString();
        String side = item.has("side") ? item.get("side").getAsString() : "right"; // 默认值
        ResourceLocation portrait = item.has("portrait") ? new ResourceLocation(item.get("portrait").getAsString()):null;
        return new DialogData(name, text, null, portrait, side);
    }


    @SideOnly(Side.SERVER)
    public static void ApplyDialog(JsonObject data, MessageContext ctx) {
        String type = data.get("type").getAsString();
        if(type.equals("C2SOption")){
            JsonArray levelArray = data.getAsJsonArray("level");
            // 这里需要根据你的业务逻辑处理level数组
            // 例如取第一个元素：
            if(levelArray.size() > 0) {
                DialogManager.getInstance().levelX(ctx.getServerHandler().playerEntity, levelArray.get(0).getAsInt());
            }
        }
    }
    @Override
    public IMessage onMessage(DialogPacket message, MessageContext ctx) {
        JsonObject data = new JsonParser().parse(message.getData()).getAsJsonObject();
        if(ctx.side.isClient()){ApplyDialog(data);}else{ApplyDialog(data,ctx);}
        return null;
    }
}
