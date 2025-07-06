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
    @Nullable private ResourceLocation portrait;
    public DialogData(String name, String text, @Nullable List<DialogOption> options,@Nullable ResourceLocation portrait,String side) {
        this.name = name;
        this.text = text;
        this.portrait = portrait;
        this.side = side;
    }

    public String getName() {
        return name;
    }


    public String getText() {
        return text;
    }


    public String getSide() {
        return side;
    }
    @Nullable
    public ResourceLocation getPortrait() {
        return portrait;
    }

    public void clear(){
        this.name = null;
        this.text = null;
        this.portrait = null;
    }



}
