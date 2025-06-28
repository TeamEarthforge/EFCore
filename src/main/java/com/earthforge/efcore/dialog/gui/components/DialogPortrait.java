package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DialogPortrait implements IComponent{
    private ResourceLocation location;
    private int x;
    private int y;
    private int width;
    private int height;
    private float scale;
    private int textureX;
    private int textureY;
    private boolean gotWidthHeight;
    private int totalWidth;
    private int totalHeight;
    public DialogPortrait(ResourceLocation location, int x, int y, int textureX,int textureY,int width, int height,float scale) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.textureX = textureX;
        this.textureY = textureY;
    }
    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        TextureManager texturemgr = Minecraft.getMinecraft().getTextureManager();
        try {
            texturemgr.bindTexture(this.location);
        } catch (Exception ignored) {return;}

        float u1 = (float)this.textureX / (float)this.totalWidth;
        float u2 = u1 + (float)this.width / (float)this.totalWidth;
        float v1 = (float)this.textureY / (float)this.totalHeight;
        float v2 = v1 + (float)this.height / (float)this.totalHeight;

        GL11.glPushMatrix();
        GL11.glScalef(this.scale, this.scale, this.scale);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        tessellator.addVertexWithUV((double)(u2 * (float)this.totalWidth), (double)(v2 * (float)this.totalHeight), 0.0, (double)u2, (double)v2);
        tessellator.addVertexWithUV((double)(u2 * (float)this.totalWidth), (double)(v1 * (float)this.totalHeight), 0.0, (double)u2, (double)v1);
        tessellator.addVertexWithUV((double)(u1 * (float)this.totalWidth), (double)(v1 * (float)this.totalHeight), 0.0, (double)u1, (double)v1);
        tessellator.addVertexWithUV((double)(u1 * (float)this.totalWidth), (double)(v2 * (float)this.totalHeight), 0.0, (double)u1, (double)v2);
        tessellator.draw();
        GL11.glPopMatrix();
    }
    public void getWidthHeight() throws IOException {
        InputStream inputstream = null;

        try {
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(this.location);
            inputstream = iresource.getInputStream();
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            this.gotWidthHeight = true;
            this.totalWidth = bufferedimage.getWidth();
            this.totalHeight = bufferedimage.getHeight();
            this.correctWidthHeight();
        } catch (Exception var7) {
            Exception e = var7;
            e.printStackTrace();
        } finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
    }
    public void correctWidthHeight() {
        this.totalWidth = Math.max(this.totalWidth, 1);
        this.totalHeight = Math.max(this.totalHeight, 1);
        this.width = this.width < 0 ? this.totalWidth : this.width;
        this.height = this.height < 0 ? this.totalHeight : this.height;
    }
}
