package com.earthforge.efcore.dialog.gui;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.dialog.data.DialogData;
import com.earthforge.efcore.dialog.data.DialogOption;
import com.earthforge.efcore.dialog.gui.components.*;
import com.earthforge.efcore.network.DialogPacket;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.*;

public class GuiDialog extends GuiScreen {
    List<DialogData> data;
    List<DialogOption> options;
    List<IComponent> components = new ArrayList<>();
    boolean isDirty;
    boolean isChosen;
    boolean hasOptions = false; // 是否有选项
    int page = 0;
    int level = 0;



    public void initGui() {
        super.initGui();
        this.isDirty = true;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void setOptions(List<DialogOption> options) {
        this.options = options;
        hasOptions=true;
    }
    public void clearOptions() {
        this.options = null;
        hasOptions = false;
    }

    public void drawScreen(int par1, int par2, float par3) {
        if (isDirty) {
            decomposeData();
            isDirty = false;
        }
        // 保存当前GL状态
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // 绘制背景
        // 替换原来的drawRect调用
        // 9-slice参数
        for (IComponent component : this.components) {
            if (component instanceof DialogPortrait) {
                component.render(this, par1, par2, par3);
            }
        }
        ResourceLocation dialogBg = new ResourceLocation("efcore:textures/gui/dialog.png");
        mc.getTextureManager().bindTexture(dialogBg);
        int sliceSize = 45; // 切片大小，可根据需要调整
        int left = (int) (width * 0.1);
        int top = (int) (height * 0.7);
        int right = (int) (width * 0.9);
        int bottom = height;
        int texWidth = 256; // 纹理实际宽度
        int texHeight = 256; // 纹理实际高度

        draw9SliceBackground(dialogBg, left, top, right, bottom, sliceSize, texWidth, texHeight);


        // 重置渲染状态
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        // 渲染组件
        for (IComponent component : this.components) {
            if (component instanceof DialogPortrait) {
                continue;
            }
            component.render(this, par1, par2, par3);
        }

        // 恢复GL状态
        GL11.glPopMatrix();
    }
    public void addData(List<DialogData> data) {
        this.data = data;
        page = 0;
        this.isDirty = true; // 设置为脏状态以便重新渲染
    }

    public void updateScreen() {
        super.updateScreen();

        for (IComponent component : this.components) {
            component.tick();
        }
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        for (IComponent component : this.components) {
            if (component instanceof DialogButton dialogButton) {
                if (par1 >= dialogButton.getX() && par1 <= dialogButton.getX() + dialogButton.getWidth() &&
                    par2 >= dialogButton.getY() && par2 <= dialogButton.getY() + dialogButton.getHeight()) {
                    // 处理按钮点击事件
                    JsonObject object = new JsonObject();
                    object.addProperty("type", "C2SOption");
                    object.addProperty("level", dialogButton.getLevel());
                    CommonProxy.getChancel().sendToServer(new DialogPacket(object.toString()));
                    this.isChosen = true;
                    this.mc.displayGuiScreen(null);
                    return;
                }
            }
        }
        for (IComponent component : this.components) {
            if (component instanceof DialogText dialogText) {
                if (dialogText.doCompletePage) {
                    page++;
                    if (page >= data.size()) {
                        this.mc.displayGuiScreen(null);
                    }
                    this.isDirty = true; // 设置为脏状态以便重新渲染
                } else {
                    dialogText.complete();
                }
            }
        }
    }
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        // 关闭对话框时发送关闭消息
        if(!isChosen){
            JsonObject object = new JsonObject();
            object.addProperty("type", "C2SOption");
            object.addProperty("level", level);
            CommonProxy.getChancel().sendToServer(new DialogPacket(object.toString()));
        }
    }

    private void decomposeData() {
        if (data == null || data.isEmpty() || page >= data.size()) {
            return; // 如果没有数据或页码超出范围，直接返回
        }
        this.components.clear();
        if(data.get(page).getPortrait()!=null){
            int baseSize = (int)(width*0.4); // 基础尺寸
            if (baseSize > (int)(height*0.65)){
                baseSize = (int)(height*0.65); // 如果基础尺寸过大，调整为高度的55%
            }
            int portraitX = 0;
            int portraitY = (int) (height * 0.35);
            if (data.get(page).getSide().equals("right")) {
                portraitX = width - baseSize;
            }

            // 新增纹理尺寸检测逻辑
            float scaleFactor;
            try {
                // 加载纹理获取实际尺寸
                IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(data.get(page).getPortrait());
                try (InputStream inputstream = resource.getInputStream()) {
                    BufferedImage image = ImageIO.read(inputstream);
                    int texWidth = image.getWidth();
                    int texHeight = image.getHeight();
                    // 计算缩放因子
                    float scaleX = (float) baseSize / texWidth;
                    float scaleY = (float) baseSize / texHeight;
                    scaleFactor = Math.min(scaleX, scaleY); // 取最小缩放因子以保持比例
                }
            } catch (Exception e) {
                scaleFactor = 1.0f; // 出错时使用默认值
            }

            components.add(new DialogPortrait(
                data.get(page).getPortrait(),
                portraitX,
                portraitY,
                0, 0,
                scaleFactor
            ));
        }


        // 名字标签 - 放在对话框内靠左位置
        // 名字标签
        int nameX, nameY;
        if (data.get(page).getSide().equals("right")) {
            nameX = (int) (width * 0.75);
            nameY = (int) (height * 0.72);
        } else {
            nameX = (int) (width * 0.15);
            nameY = (int) (height * 0.72);
        }

        components.add(new DialogLabel(
            nameX,
            nameY,
            data.get(page).getName()
        ));
                // 对话文本 - 放在名字下方，占据对话框大部分区域
        int textX = (int) (width * 0.15);
        int textY = (int) (height * 0.75);
        int textWidth = (int) (width * 0.7); // 文本区域宽度
        components.add(new DialogText(
            data.get(page).getText(),
            textX,
            textY,
            textWidth
        ));
        if (hasOptions&&page == data.size()-1) {
            int centerX = width / 2;
            int startY = (int) (height * 0.6); // 对话框中间往上一点的位置
            int optionIndex = 0;
            for (DialogOption option : options) {
                int optionY = startY - (optionIndex * 30); // 每个选项下移30像素
                components.add(new DialogButton(
                    new ResourceLocation("efcore:textures/gui/button_normal.png"),
                    new ResourceLocation("efcore:textures/gui/button_hover.png"),
                    centerX - 100,
                    optionY,
                    200,
                    option.getText(),
                    option.getLevel()
                ));
                optionIndex++;
            }
        }

    }
    private void drawRectangle(Tessellator tessellator, int x, int y, int width, int height,
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
    protected void draw9SliceBackground(ResourceLocation texture, int left, int top, int right, int bottom,
                                        int sliceSize, int texWidth, int texHeight) {
        mc.getTextureManager().bindTexture(texture);
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
}
