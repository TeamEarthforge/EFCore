package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DialogPortrait implements IComponent {
    private ResourceLocation location;
    private int x;
    private int y;
    private int width; // 纹理切片的宽度
    private int height; // 纹理切片的高度
    private float scale;
    private int textureX; // 纹理切片起始X坐标
    private int textureY; // 纹理切片起始Y坐标
    private int totalWidth; // 纹理的实际总宽度
    private int totalHeight; // 纹理的实际总高度

    public DialogPortrait(ResourceLocation location, int x, int y, int textureX, int textureY, int width, int height, float scale) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.textureX = textureX;
        this.textureY = textureY;

        try {
            getWidthHeight();
        } catch (IOException e) {
            e.printStackTrace();
            this.totalWidth = 256;
            this.totalHeight = 256;
        }
    }
    public DialogPortrait(ResourceLocation location, int x, int y, int textureX, int textureY, float scale) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.textureX = textureX;
        this.textureY = textureY;

        try {
            getWidthHeight();
            this.width = totalWidth; // 默认使用整个纹理宽度
            this.height = totalHeight; // 默认使用整个纹理高度
        } catch (IOException e) {
            e.printStackTrace();
            this.totalWidth = 256;
            this.totalHeight = 256;
            this.width = 256; // 默认宽度
            this.height = 256; // 默认高度
        }
    }

    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        TextureManager textureMgr = Minecraft.getMinecraft().getTextureManager();
        textureMgr.bindTexture(this.location);

        if (totalWidth <= 0 || totalHeight <= 0) {
            System.err.println("DialogPortrait: Invalid texture dimensions for " + this.location);
            return;
        }

        // 计算纹理坐标 (UV)
        float u1 = (float) this.textureX / (float) this.totalWidth;
        float v1 = (float) this.textureY / (float) this.totalHeight;
        float u2 = u1 + (float) this.width / (float) this.totalWidth;
        float v2 = v1 + (float) this.height / (float) this.totalHeight;

        GL11.glPushMatrix();
        // 移动到绘制位置
        GL11.glTranslatef(this.x, this.y, 0.0F);
        // 应用缩放。对于2D GUI，Z轴缩放设为1即可
        GL11.glScalef(this.scale, this.scale, 1.0F);

        // 设置颜色和混合模式以支持透明度
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        // 【关键修复】 修正顶点的绘制顺序。
        // 正确的四边形顶点顺序应为连续的环绕（顺时针或逆时针），例如：左上 -> 左下 -> 右下 -> 右上。
        // 你之前的顺序 (左下 -> 左上 -> 右下 -> 右上) 是错误的，它会形成一个自相交的“领结”形状，
        // 导致OpenGL只能正确渲染其中的一个三角形。

        // 顶点坐标从(0,0)到(width, height)，因为我们已经通过glTranslatef和glScalef设置了位置和缩放。
        // UV坐标将纹理切片映射到这些顶点上。

        // 左上角
        tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)u1, (double)v1);
        // 左下角
        tessellator.addVertexWithUV(0.0, (double)this.height, 0.0, (double)u1, (double)v2);
        // 右下角
        tessellator.addVertexWithUV((double)this.width, (double)this.height, 0.0, (double)u2, (double)v2);
        // 右上角
        tessellator.addVertexWithUV((double)this.width, 0.0, 0.0, (double)u2, (double)v1);

        tessellator.draw();

        // 渲染完毕后最好禁用混合，避免影响其他部分的渲染
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void tick() {
        // 如果有动画或状态更新，在这里实现
    }

    private void getWidthHeight() throws IOException {
        InputStream inputstream = null;
        try {
            IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(this.location);
            inputstream = iresource.getInputStream();
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            this.totalWidth = bufferedimage.getWidth();
            this.totalHeight = bufferedimage.getHeight();
        } finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
    }
}
