package com.earthforge.efcore.gui.dialog;

import com.earthforge.efcore.gui.dialog.data.DialogData;
import com.earthforge.efcore.gui.dialog.data.DialogText;
import net.minecraft.client.gui.GuiScreen;
import noppes.npcs.client.TextBlockClient;

import java.util.ArrayList;
import java.util.List;

public class DialogGui extends GuiScreen {
    private GuiScreen parentscreen;
    private List<DialogText> textlist = new ArrayList<>(); // 初始化列表
    private int page = 0;
    private int line = 0;
    private String gradualtext = "";
    private int size;
    private DialogText currentBlock;
    private boolean doComplete = false;
    public DialogGui(GuiScreen parent) {
        parentscreen = parent;
    }

    public DialogGui(GuiScreen parent, DialogData data) {
        parentscreen = parent;
        data.parts().forEach(part -> {
            DialogText block = new DialogText(data.getCharacter(part.characterid()).name(),part.content(),200);
            textlist.add(block);
        });
    }


    public void initGui() {
        super.initGui();
    }
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);

        // 检查对话是否结束
        if (textlist.isEmpty() || page >= textlist.size()) {
            drawCenteredString(fontRendererObj, "对话结束", width / 2, height / 2, 0xFFFF00);
            return;
        }

        currentBlock = textlist.get(page);

        // 绘制说话人名字
        String name = currentBlock.getName();
        int namewidth = fontRendererObj.getStringWidth(name);
        drawString(fontRendererObj, name, width / 2 - namewidth / 2, height / 2 - 100, 0xFFFFFF);

        // 绘制已完成的行
        for (int l = 0; l < line; l++) {
            String text = currentBlock.lines.get(l);
            drawString(fontRendererObj, text, width / 2 - fontRendererObj.getStringWidth(text) / 2, height / 2 - 50 + l * 10, 0xFFFFFF);
        }
        if (line==0){
            nextLine();
        }

        // 检查当前段落是否完成
        if (line >= currentBlock.lines.size()) {
            doComplete = true;
            // 绘制继续提示
            String continueText = "点击继续";
            drawCenteredString(fontRendererObj, continueText, width / 2, height / 2 + 50, 0xFFFF00);
            return;
        }

        // 处理当前行逐字显示
        String currentLine = currentBlock.lines.get(line);
        size = currentLine.length();

        if (gradualtext.length() < size) {
            gradualtext = currentLine.substring(0, gradualtext.length() + 1);
        }

        // 绘制当前行
        drawCenteredString(fontRendererObj, gradualtext, width / 2, height / 2 - 50 + line * 10, 0xFFFFFF);

        // 检查是否完成当前行
        if (gradualtext.length() == size) {
            doComplete = false; // 重置完成状态
            nextLine();
        }

    }
    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (textlist.isEmpty() || page >= textlist.size() || currentBlock == null) return;

        if (par3 == 0) { // 左键点击
            if (doComplete) {
                // 当前段落已完成：翻到下一页
                nextPage();
            } else if (gradualtext.length() < currentBlock.lines.get(line).length()) {
                // 立即完成当前行
                gradualtext = currentBlock.lines.get(line);
            } else {
                // 完成当前行后：移动到下一行
                nextLine();
            }
        }
    }

    private void nextLine() {
        line++;
        gradualtext = "";

        // 检查是否完成当前段落
        if (line >= currentBlock.lines.size()) {
            doComplete = true;
        }
    }

    private void nextPage() {
        page++;
        line = 0;
        gradualtext = "";
        doComplete = false;

        // 检查对话是否结束
        if (page >= textlist.size()) {
            this.mc.displayGuiScreen(this.parentscreen);
        }
    }
}
