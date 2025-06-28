package com.earthforge.efcore.dialog.gui;

import com.earthforge.efcore.dialog.gui.components.DialogText;
import com.earthforge.efcore.dialog.gui.components.IComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class GuiDialog extends GuiScreen {
    public List<List<IComponent>> components;
    private int page = 0;


    public void initGui() {
        super.initGui();
    }

    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);

        for (IComponent component : this.components.get(page)){
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDisable(3008);
            component.render(this,par1, par2, par3);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            GL11.glEnable(3008);
        }
    }

    public void updateScreen() {
        super.updateScreen();
        texts.get(page).nextChar();
    }
    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if(texts.get(page).doCompletePage){
            page++;
        }else{
            texts.get(page).complete();
        }
    }

}
