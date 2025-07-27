package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

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
        GL11.glPushMatrix();
        GL11.glTranslatef(x,y, 0.0F);
        GL11.glScalef(1.5F,1.5F, 1.0F);
        gui.drawString(Minecraft.getMinecraft().fontRenderer,text, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    @Override
    public void tick() {

    }
}
