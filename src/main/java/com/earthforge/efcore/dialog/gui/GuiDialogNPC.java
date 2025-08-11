package com.earthforge.efcore.dialog.gui;

import noppes.npcs.entity.EntityNPCInterface;

import java.util.ArrayList;

public class GuiDialogNPC extends GuiDialog {
    EntityNPCInterface npc;

    public GuiDialogNPC(EntityNPCInterface npc) {
        super();
        this.data = new ArrayList<>();
        this.components = new ArrayList<>();
        this.isDirty = true;
        this.page = 0;
        this.setLevel(0);
        this.npc = npc;
    }

    @Override
    public void initGui() {
        super.initGui();
        // 在这里可以添加NPC特有的初始化逻辑
    }
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);
    }

    // 可以添加更多NPC特有的方法和逻辑
}
