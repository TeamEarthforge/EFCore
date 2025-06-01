package com.earthforge.efcore.gui.dialog.data;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.Gson;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.util.JsonException;
import noppes.npcs.util.NBTJsonUtil;

import javax.annotation.Nullable;
import java.io.*;
import java.util.List;
import java.util.Map;

@Desugar
public record DialogData(Map<String,CharacterData> characters, List<DialogPart> parts) {
    @Desugar
    public record DialogPart(String characterid, String content, @Nullable String emotion, String position){
        public String defaultPosition(DialogData root) {
            return position != null ? position :
                root.characters().get(characterid).defaultPosition();
        }
    }
    @Desugar
    public record CharacterData(String name, Map<String, String> images, String defaultPosition) {
        public String getImage(String emotion) {
            return images.getOrDefault(emotion, images.get("default"));
        }
    }
    public CharacterData getCharacter(String id) {
        return characters.get(id);
    }
    public static DialogData readFromNBT(NBTTagCompound nbt) {
            return new Gson().fromJson(NBTJsonUtil.Convert(nbt), DialogData.class);
    }
    public NBTTagCompound writeToNBT() throws JsonException {
        return NBTJsonUtil.Convert(new Gson().toJson(this));
    }
}
