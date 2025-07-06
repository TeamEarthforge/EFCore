package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class DialogButton implements IComponent {
    private ResourceLocation normal;
    private ResourceLocation hover;
    private ResourceLocation disabled;
    private boolean isdisabled ;
    private int x;
    private int y;
    private int width;
    private int height;
    public DialogButton(ResourceLocation normal, ResourceLocation hover, ResourceLocation disabled, int x, int y, int width, int height,boolean isdisabled) {
        this.normal = normal;
        this.hover = hover;
        this.disabled = disabled;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isdisabled = isdisabled;
    }

    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mousex >= x && mousex <= x + width && mousey >= y && mousey <= y + height) {
            mc.getTextureManager().bindTexture(hover);
        } else {
            mc.getTextureManager().bindTexture(normal);
        }
        if(isdisabled){
            mc.getTextureManager().bindTexture(disabled);
        }
        gui.drawTexturedModalRect(x, y, 0, 0, width, height);
    }

    @Override
    public void tick() {

    }
}
