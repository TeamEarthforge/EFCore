package com.earthforge.efcore.dialog.gui;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.dialog.data.DialogData;
import com.earthforge.efcore.dialog.gui.components.DialogLabel;
import com.earthforge.efcore.dialog.gui.components.DialogPortrait;
import com.earthforge.efcore.dialog.gui.components.DialogText;
import com.earthforge.efcore.dialog.gui.components.IComponent;
import com.earthforge.efcore.network.DialogPacket;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import java.util.*;

public class GuiDialog extends GuiScreen {
    public List<DialogData> data;
    public List<IComponent> components = new ArrayList<>();
    private int page = 0;
    private int level = 0;


    public void initGui() {
        super.initGui();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
        drawRect((int) (width * 0.1), (int) (height * 0.7), (int) (width * 0.9), height, 0x80FFFFFF);
        for (IComponent component : this.components) {
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDisable(3008);
            component.render(this, par1, par2, par3);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            GL11.glEnable(3008);
        }
    }

    public void addData(List<DialogData> data) {
        this.data = data;
        page = 0;
        decomposeData();
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
            if (component instanceof DialogText dialogText) {
                if (dialogText.doCompletePage) {
                    page++;
                    if (page >= data.size()) {
                        JsonObject object = new JsonObject();
                        object.addProperty("type", "C2SOption");
                        object.addProperty("level", level);
                        this.mc.displayGuiScreen(null);
                        CommonProxy.getChancel().sendToServer(new DialogPacket(object.toString()));

                    }
                    decomposeData();
                } else {
                    dialogText.complete();
                }
            }
        }
    }

    private void decomposeData() {

        this.components.clear();
        if(data.get(page).getPortrait()!=null){
            int portraitSize = 280; // 立绘尺寸
            int portraitX = (int) (width * 0.05); // 比对话框左边界更靠左
            int portraitY = (int) (height * 0.45); // 从对话框向上延伸
            if (data.get(page).getSide().equals("right")) {

                portraitX = (int) (width * 0.75); // 比对话框右边界更靠右
                portraitY = (int) (height * 0.45); // 从对话框向上延伸
            }
            components.add(new DialogPortrait(
                data.get(page).getPortrait(),
                portraitX,
                portraitY,
                0, 0,
                portraitSize,
                portraitSize,
                1.0f
            ));
        }


        // 名字标签 - 放在对话框内靠左位置
        int nameX = (int) (width * 0.15);
        int nameY = (int) (height * 0.72);
        if (data.get(page).getSide().equals("right")) {
            nameX = (int) (width * 0.75 - mc.fontRenderer.getStringWidth(data.get(page).getName())); // 比对话框右边界更靠右
            nameY = (int) (height * 0.72); // 从对话框向上延伸
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
    }
}
