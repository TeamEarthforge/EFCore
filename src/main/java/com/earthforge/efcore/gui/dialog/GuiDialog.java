package com.earthforge.efcore.gui.dialog;

import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.gui.dialog.data.DialogData;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import scala.Unit;

import javax.annotation.Nullable;
import java.util.List;


@SideOnly(Side.CLIENT)
public class GuiDialog extends GuiContainer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(EFCore.MODID, "textures/gui/dialog.png");
    protected boolean isClose = false;;
    public final EntityPlayer player;
    protected final List<Unit> options = Lists.newArrayList();
    public final ContainerDialog contaniner;
    public final String text;
    public final String image;
    public final String position;



    public GuiDialog(EntityPlayer player) {
           this(player,"left","","");
    }
    public GuiDialog(EntityPlayer player,String position,String text,@Nullable String image) {
        super(new ContainerDialog(player));
        this.player = player;
        this.contaniner = (ContainerDialog) super.inventorySlots;
        this.xSize = 176;
        this.ySize = 133;
        this.text = text;
        this.image= image;
        this.position =position;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        // 基础背景渲染
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2;
        int offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        // 根据位置调整对话框偏移
        switch(position) {
            case "left":
                offsetX = 10; // 左侧固定偏移
                break;
            case "right":
                offsetX = this.width - this.xSize - 10; // 右侧固定偏移
                break;
            case "center":
            default:
                // 保持居中不变
        }

        // 如果有自定义图片则渲染
        if (image != null && !image.isEmpty()) {
            ResourceLocation imageLoc = new ResourceLocation(EFCore.MODID, "textures/gui/" + image);
            this.mc.getTextureManager().bindTexture(imageLoc);
            this.drawTexturedModalRect(offsetX, offsetY - 30, 0, 0, 64, 64); // 图片在对话框上方
        }

        // 渲染文本
        this.fontRendererObj.drawSplitString(text, offsetX + 10, offsetY + 20, xSize - 20, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) { // 0代表左键
            // 在这里处理左键点击逻辑
            System.out.println("玩家在对话框内点击了左键");
            // 可以添加更多点击处理逻辑
        }
    }
}
