package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class DialogLabel implements IComponent{
    private int x;
    private int y;
    private String text;
    public DialogLabel(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }
    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        gui.drawString(Minecraft.getMinecraft().fontRenderer,text, x, y, 0xFFFFFF);

    }

    @Override
    public void tick() {

    }
}
