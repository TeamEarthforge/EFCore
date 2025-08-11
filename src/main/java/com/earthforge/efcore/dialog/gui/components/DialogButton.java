package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class DialogButton implements IComponent {
    private ResourceLocation normal;
    private ResourceLocation hover;
    private String displayString;
    private int x;
    private int y;
    private int width;
    private int height = 20;
    private int level;
    public DialogButton(ResourceLocation normal, ResourceLocation hover, int x, int y, int width, String displayString,int level) {
        this.normal = normal;
        this.hover = hover;
        this.x = x;
        this.y = y;
        this.width = width;
        this.displayString = displayString;
        this.level = level;
    }

    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mousex >= x && mousex <= x + width && mousey >= y && mousey <= y + height) {
            mc.getTextureManager().bindTexture(hover);
        } else {
            mc.getTextureManager().bindTexture(normal);
        }
        FontRenderer fontrenderer = mc.fontRenderer;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        gui.func_146110_a(this.x, this.y, 0, 0, this.width, this.height,this.width, this.height);
        gui.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0xFFFFFF);
    }

    @Override
    public void tick() {

    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getLevel() {
        return level;
    }
}
