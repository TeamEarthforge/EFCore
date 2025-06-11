package com.earthforge.efcore.gui.dialog;


import com.earthforge.efcore.gui.dialog.data.DialogData;
import com.earthforge.efcore.gui.dialog.data.DialogText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;


import java.util.ArrayList;
import java.util.List;

public class DialogGui extends GuiScreen {
    private GuiScreen parentscreen;
    private List<DialogText> textlist = new ArrayList<>(); // 初始化列表
    private int page = 0;
    private int line = 0;
    private String gradualtext = "";
    private int size;
    private int lastScreenWidth = 0;
    private DialogData data;
    private DialogText currentBlock;
    private boolean doCompleteLine = false;
    private boolean doCompletePage = false;
    public DialogGui(GuiScreen parent) {
        parentscreen = parent;
    }

    public DialogGui(GuiScreen parent, DialogData data) {
        parentscreen = parent;
        this.data = data;

    }


    public void initGui() {
        super.initGui();
    }

    public void drawScreen(int par1, int par2, float par3) {

        if(width != lastScreenWidth) {
            data.parts().forEach(part -> {
                DialogText block = new DialogText(data.getCharacter(part.characterid()).name(),part.content(),(int)(width*0.7));
                textlist.add(block);
            });
            this.lastScreenWidth = width;
        }
        super.drawScreen(par1, par2, par3);
        drawString(fontRendererObj, String.format("You are pointing to: (§o%d§r,§o%d§r)", par1, par2),(int)(width*0.05), (int)(height*0.9), 0xFFFFFF);
        drawString(fontRendererObj, String.format("width&height(§o%d§r,§o%d§r)", width, height),(int)(width*0.05), (int)(height*0.9) +20, 0xFFFFFF);
        //Minecraft mc = Minecraft.getMinecraft();
        //mc.renderEngine.bindTexture(new ResourceLocation(EFCore.MODID,"textures/gui/dialog.png"));
        //func_152125_a((int)(width*0.1), (int)(height*0.1),0,0,566,92,566,92,566,92);

        drawRect((int)(width*0.1), (int)(height*0.7), (int)(width*0.9), height, 0x80FFFFFF);

        currentBlock = textlist.get(page);


        String name = currentBlock.getName();
        int namewidth = fontRendererObj.getStringWidth(name);
        drawString(fontRendererObj, name, (int)(width*0.1), (int)(height*0.7), 0xFF1);


        for (int l = 0; l < line; l++) {
            String text = currentBlock.lines.get(l);
            drawString(fontRendererObj, text, (int)(width*0.15), (int)(height*0.75) + l * 10, 0x000);
        }
        if (line==0){
            nextLine();
        }


        if (line >= currentBlock.lines.size()) {
            doCompletePage = true;
            return;
        }


        String currentLine = currentBlock.lines.get(line);
        size = currentLine.length();

        if (gradualtext.length() < size) {
            gradualtext = currentLine.substring(0, gradualtext.length() + 1);
        }

        // 绘制当前行
        drawString(fontRendererObj, gradualtext, (int)(width*0.15), (int)(height*0.75) + line * 10, 0x000);

        // 检查是否完成当前行
        if (gradualtext.length() == size) {
            doCompleteLine = false; // 重置完成状态
            nextLine();
        }

    }
    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (textlist.isEmpty() || page >= textlist.size() || currentBlock == null) return;

        if (par3 == 0) { // 左键点击
            if (doCompletePage) {
                // 当前段落已完成：翻到下一页
                nextPage();
            } else if (gradualtext.length() < currentBlock.lines.get(line).length()) {
                // 立即完成当前行
                gradualtext = currentBlock.lines.get(line);
            } else if (doCompleteLine){
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
            doCompleteLine = true;
        }
    }

    private void nextPage() {
        page++;
        line = 0;
        gradualtext = "";
        doCompleteLine = false;

        // 检查对话是否结束
        if (page >= textlist.size()) {
            this.mc.displayGuiScreen(this.parentscreen);
        }
    }

    private void rebuildTextLayouts() {
        int maxWidth = (int)(width * 0.7);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
