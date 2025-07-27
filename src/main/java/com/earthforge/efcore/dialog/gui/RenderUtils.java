package com.earthforge.efcore.dialog.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class RenderUtils {
    public static void draw9SliceBackground(ResourceLocation texture, int left, int top, int right, int bottom,
                                        int sliceSize, int texWidth, int texHeight) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        int x = left;
        int y = top;
        int w = right - left;
        int h = bottom - top;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        float uScale = 1f / texWidth;
        float vScale = 1f / texHeight;

        // 左上角
        drawRectangle(tessellator, x, y, sliceSize, sliceSize, 0, 0, sliceSize, sliceSize, uScale, vScale);
        // 上边缘
        drawRectangle(tessellator, x + sliceSize, y, w - 2 * sliceSize, sliceSize, sliceSize, 0, texWidth - 2 * sliceSize, sliceSize, uScale, vScale);
        // 右上角
        drawRectangle(tessellator, x + w - sliceSize, y, sliceSize, sliceSize, texWidth - sliceSize, 0, sliceSize, sliceSize, uScale, vScale);
        // 左边缘
        drawRectangle(tessellator, x, y + sliceSize, sliceSize, h - 2 * sliceSize, 0, sliceSize, sliceSize, texHeight - 2 * sliceSize, uScale, vScale);
        // 中心
        drawRectangle(tessellator, x + sliceSize, y + sliceSize, w - 2 * sliceSize, h - 2 * sliceSize, sliceSize, sliceSize, texWidth - 2 * sliceSize, texHeight - 2 * sliceSize, uScale, vScale);
        // 右边缘
        drawRectangle(tessellator, x + w - sliceSize, y + sliceSize, sliceSize, h - 2 * sliceSize, texWidth - sliceSize, sliceSize, sliceSize, texHeight - 2 * sliceSize, uScale, vScale);
        // 左下角
        drawRectangle(tessellator, x, y + h - sliceSize, sliceSize, sliceSize, 0, texHeight - sliceSize, sliceSize, sliceSize, uScale, vScale);
        // 下边缘
        drawRectangle(tessellator, x + sliceSize, y + h - sliceSize, w - 2 * sliceSize, sliceSize, sliceSize, texHeight - sliceSize, texWidth - 2 * sliceSize, sliceSize, uScale, vScale);
        // 右下角
        drawRectangle(tessellator, x + w - sliceSize, y + h - sliceSize, sliceSize, sliceSize, texWidth - sliceSize, texHeight - sliceSize, sliceSize, sliceSize, uScale, vScale);
        tessellator.draw();
    }
    private static void drawRectangle(Tessellator tessellator, int x, int y, int width, int height,
                               int u, int v, int texWidth, int texHeight, float uScale, float vScale) {
        float u1 = u * uScale;
        float v1 = v * vScale;
        float u2 = (u + texWidth) * uScale;
        float v2 = (v + texHeight) * vScale;

        tessellator.addVertexWithUV(x, y + height, 0, u1, v2);
        tessellator.addVertexWithUV(x + width, y + height, 0, u2, v2);
        tessellator.addVertexWithUV(x + width, y, 0, u2, v1);
        tessellator.addVertexWithUV(x, y, 0, u1, v1);
    }
}
