package com.earthforge.efcore.network;

import com.earthforge.efcore.feature.dialog.DialogManager;
import com.earthforge.efcore.feature.dialog.data.DialogData;
import com.earthforge.efcore.feature.dialog.data.DialogOption;
import com.earthforge.efcore.feature.dialog.gui.GuiDialog;
import com.google.gson.*;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
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
        int level = 114514;
        List<DialogOption> dialogOptions = new ArrayList<>();

        if (type.equals("S2CText")) {
            JsonArray array = data.getAsJsonArray("content");
            for (int i = 0; i < array.size(); i++) {
                JsonObject item = array.get(i).getAsJsonObject();
                dialogData.add(parseDialogData(item));
            }
            level = levelArray.get(0).getAsInt();
        } else if (type.equals("S2CTextWithOptions")) {
            JsonArray contentArray = data.getAsJsonArray("content");
            for (int i = 0; i < contentArray.size(); i++) {
                JsonObject item = contentArray.get(i).getAsJsonObject();
                dialogData.add(parseDialogData(item));
            }

            JsonArray optionsArray = data.getAsJsonArray("options");
            // 从第二个元素开始的等级数组（第一个是默认等级）
            for (int i = 0; i < optionsArray.size(); i++) {
                String optionText = optionsArray.get(i).getAsString();
                int optionLevel = levelArray.size() > i + 1 ? levelArray.get(i + 1).getAsInt() : 114514;
                dialogOptions.add(new DialogOption(optionText, optionLevel));
            }
        }

        if (!dialogData.isEmpty()) {
            if (mc.currentScreen instanceof GuiDialog) {
                GuiDialog dialog = (GuiDialog) mc.currentScreen;
                dialog.setLevel(level); // 修改为传递level数组
                dialog.addData(dialogData);
                dialog.clearOptions();
                if (!dialogOptions.isEmpty()) {
                    dialog.setOptions(dialogOptions);
                }
            } else if (mc.currentScreen == null) {
                GuiDialog dialog = new GuiDialog();
                dialog.setLevel(level); // 修改为传递level数组
                dialog.addData(dialogData);
                mc.displayGuiScreen(dialog);
                dialog.clearOptions();
                if (!dialogOptions.isEmpty()) {
                    dialog.setOptions(dialogOptions);
                }
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



    public static void ApplyDialog(JsonObject data, MessageContext ctx) {
        String type = data.get("type").getAsString();
        if(type.equals("C2SOption")){
                int level = data.get("level").getAsInt();
                if(level != 114514){
                    DialogManager.getInstance().levelX(ctx.getServerHandler().playerEntity, level);
                }

        }
    }
    @Override
    public IMessage onMessage(DialogPacket message, MessageContext ctx) {
        JsonObject data = new JsonParser().parse(message.getData()).getAsJsonObject();
        if(ctx.side.isClient()){ApplyDialog(data);}
        else{ApplyDialog(data,ctx);}
        return null;
    }
}
