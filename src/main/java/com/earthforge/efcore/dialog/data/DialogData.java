package com.earthforge.efcore.dialog.data;

import com.github.bsideup.jabel.Desugar;
import com.google.gson.Gson;
import net.minecraft.client.particle.EntityFireworkOverlayFX;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.util.JsonException;
import noppes.npcs.util.NBTJsonUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DialogData {
    private String name;
    private String text;
    private String side; // "left" or "right"
    private List<String> options;
    private List<Integer> optionLevels;
    private ResourceLocation portrait;
    public DialogData(String name, String text, @Nullable List<String> options, @Nullable List<Integer> optionLevels, ResourceLocation portrait,String side) {
        this.name = name;
        this.text = text;
        this.options = options;
        this.optionLevels = optionLevels;
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }


    public String getText() {
        return text;
    }


    public List<String> getOptions() {
        return options;
    }


    public List<Integer> getOptionLevels() {
        return optionLevels;
    }


    public ResourceLocation getPortrait() {
        return portrait;
    }

    public void clear(){
        this.name = null;
        this.text = null;
        this.options.clear();
        this.optionLevels.clear();
        this.portrait = null;
    }


}
