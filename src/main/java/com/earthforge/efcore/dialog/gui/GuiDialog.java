package com.earthforge.efcore.dialog.gui;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.dialog.data.DialogData;
import com.earthforge.efcore.dialog.gui.components.DialogPortrait;
import com.earthforge.efcore.dialog.gui.components.DialogText;
import com.earthforge.efcore.dialog.gui.components.IComponent;
import com.earthforge.efcore.network.DialogPacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import java.util.*;

public class GuiDialog extends GuiScreen {
    public List<DialogData> data;
    public List<IComponent> components;
    private int page = 0;
    private int level = 0;


    public void initGui() {
        super.initGui();
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void drawScreen(int par1, int par2, float par3) {
        super.drawScreen(par1, par2, par3);

        for (IComponent component : this.components){
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDisable(3008);
            component.render(this,par1, par2, par3);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            GL11.glEnable(3008);
        }
    }
    public void addData(List<DialogData> data){
        this.data =data;
        page = 0;
        decomposeData();
    }
    public void updateScreen() {
        super.updateScreen();
        for(IComponent component : this.components){
            component.tick();
        }
    }
    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        for(IComponent component : this.components){
            if(component instanceof DialogText dialogText){
                if(dialogText.doCompletePage){
                    page++;
                    if(page >= data.size()){
                        this.mc.displayGuiScreen(null);
                        CommonProxy.getChancel().sendToServer(new DialogPacket());

                    }
                    decomposeData();
                }else {
                    dialogText.complete();
                }
            }
        }
    }
    private void decomposeData() {

        this.components.clear();
            components.add(new DialogText(data.get(page).getText(),this.width/2-100,this.height/2-100,200));
            components.add(new DialogPortrait(data.get(page).getPortrait(),this.width/2-100,this.height/2-100,0,0,200,200,1.0f));

    }
}
