package com.earthforge.efcore.gui;

import com.earthforge.efcore.EFCore;
import com.earthforge.efcore.gui.dialog.ContainerDialog;
import com.earthforge.efcore.gui.dialog.GuiDialog;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ModScreens implements IGuiHandler {

    public static final int DIALOG = 0;
    public ModScreens() {
        NetworkRegistry.INSTANCE.registerGuiHandler(EFCore.MODID, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == DIALOG) {
            return new ContainerDialog(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == DIALOG) {
            return new GuiDialog( player);
        }
        return null;
    }
}
