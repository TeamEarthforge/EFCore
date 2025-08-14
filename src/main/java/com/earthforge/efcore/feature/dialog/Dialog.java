package com.earthforge.efcore.feature.dialog;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.api.IDialog;
import com.earthforge.efcore.feature.dialog.data.DialogData;

import com.earthforge.efcore.feature.dialog.data.DialogOption;
import com.earthforge.efcore.network.DialogPacket;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.controllers.ScriptContainer;
import noppes.npcs.scripted.CustomNPCsException;

import javax.annotation.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Dialog implements IDialog {
    ScriptContainer scripts;
    boolean enableScript = false;
    boolean dirty=false;
    List<DialogData> data = new ArrayList<>();
    IPlayer<EntityPlayerMP> player;
    List<DialogOption> options;
    int defaultLevel = 114514;



    public Dialog() {
    }


    public IPlayer<EntityPlayerMP> getPlayer() {
        return this.player;
    }
    public void setDefaultLevel(int level) {
        this.defaultLevel = level;
    }
    public void enableScript() {
        if (ScriptContainer.Current == null) {
            throw new CustomNPCsException("Can only be called during scripts");
        } else {
            this.scripts = ScriptContainer.Current;
            this.enableScript = true;
            this.dirty=true;
        }
    }

    public void show(IPlayer<EntityPlayerMP> player) {
        this.player = player;
        send();
        if(dirty){
            DialogManager.getInstance().addDialog(player.getMCEntity(), this);
            dirty = false;
        }
    }

    public void clear() {
        this.data.clear();
        this.options = null;
    }

    public void clearPage(int page) {
        this.data.remove(page);
    }

    public void addText(String name, String text, @Nullable String portrait, String side) {
        DialogData dialogData = new DialogData(name, text, null, portrait == null ? null : new ResourceLocation(portrait), side);
        this.data.add(dialogData);
    }


    public void addOptions(String optionsString) {
        List<DialogOption> dialogOptions = new ArrayList<>();

        // 分割字符串，保留所有部分
        String[] parts = optionsString.split("#", -1);  // 使用-1保留空字符串

        // 从第一个元素开始处理，每两个元素为一组(level和text)
        for (int i = 1; i < parts.length; i += 2) {
            if (i + 1 >= parts.length) break;  // 确保有足够的元素

            String levelStr = parts[i];
            String text = parts[i + 1];

            try {
                int level = Integer.parseInt(levelStr);
                dialogOptions.add(new DialogOption(text, level));
            } catch (NumberFormatException e) {
                // 如果等级不是数字，使用默认等级
                dialogOptions.add(new DialogOption(text, defaultLevel));
            }
        }
        this.options = dialogOptions;
    }
    public void addOptions(List<String> options, List<Integer> levels) {
        List<DialogOption> dialogOptions = new ArrayList<>();
        for (int i = 0; i < options.size(); i++) {
            dialogOptions.add(new DialogOption(options.get(i), levels.get(i)));
        }
        this.options = dialogOptions;
    }


    public void send() {
        JsonObject jsonObject = new JsonObject();
        JsonArray contentArray = new JsonArray();

        // 构建content数组
        for (DialogData dialogData : data) {
            JsonObject contentObj = new JsonObject();
            contentObj.addProperty("text", dialogData.getText());
            contentObj.addProperty("name", dialogData.getName());
            contentObj.addProperty("side", dialogData.getSide());
            if (dialogData.getPortrait() != null) {
                contentObj.addProperty("portrait", dialogData.getPortrait().toString());
            }
            contentArray.add(contentObj);
        }

        // 设置type和content
        if (this.options == null) {
            jsonObject.addProperty("type", "S2CText");
            JsonArray array = new JsonArray();
            array.add(new Gson().toJsonTree(defaultLevel));
            jsonObject.add("level",array) ; // 添加空的level数组
        } else {
            jsonObject.addProperty("type", "S2CTextWithOptions");

            // 构建options数组
            JsonArray optionsArray = new JsonArray();
            for (DialogOption option : options) {
                optionsArray.add(new Gson().toJsonTree(option.getText()));
            }
            jsonObject.add("options", optionsArray);

            // 构建level数组
            JsonArray levelArray = new JsonArray();
            levelArray.add(new Gson().toJsonTree(defaultLevel));
            for (DialogOption option : options) {
                levelArray.add(new Gson().toJsonTree(option.getLevel()));
            }
            jsonObject.add("level", levelArray);
        }

        jsonObject.add("content", contentArray);

        String json = new Gson().toJson(jsonObject);
        CommonProxy.getChancel().sendTo(new DialogPacket(json), player.getMCEntity());

    }
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
                    JsonObject json = new Gson().fromJson(reader, JsonObject.class);

                    // 清空现有数据
                    this.clear();

                    // 解析内容
                    JsonArray contentArray = json.getAsJsonArray("content");
                    for (int i = 0; i < contentArray.size(); i++) {
                        JsonObject contentObj = contentArray.get(i).getAsJsonObject();
                        String text = contentObj.get("text").getAsString();
                        String portrait = contentObj.has("portrait") ? contentObj.get("portrait").getAsString() : null;
                        String side = contentObj.get("side").getAsString();
                        String name = contentObj.get("name").getAsString();
                        this.addText(name, text, portrait, side); // 名称留空，因为send方法中没有包含name
                    }

                    // 解析选项（如果有）
                    if (json.has("options") && json.has("level")) {
                        JsonArray optionsArray = json.getAsJsonArray("options");
                        JsonArray levelArray = json.getAsJsonArray("level");

                        // 第一个level是defaultLevel
                        if (levelArray.size() > 0) {
                            this.defaultLevel = levelArray.get(0).getAsInt();
                        }

                        // 解析选项
                        List<String> options = new ArrayList<>();
                        List<Integer> levels = new ArrayList<>();

                        // 从第二个元素开始是选项的level
                        for (int i = 1; i < levelArray.size(); i++) {
                            levels.add(levelArray.get(i).getAsInt());
                        }

                        for (int i = 0; i < optionsArray.size(); i++) {
                            options.add(optionsArray.get(i).getAsString());
                        }

                        this.addOptions(options, levels);
                    }
                }
            }
        } catch (IOException ignored) {}
    }
    public void dispose() {
        DialogManager.getInstance().dispose(this);
    }


}
