package com.earthforge.efcore.network;

import com.earthforge.efcore.dialog.DialogGui;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

public class DialogHandler implements IMessageHandler<DialogPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public static void ApplyDialog(DialogPacket packetIn) {
        Minecraft mc = Minecraft.getMinecraft();
        DialogGui gui = new DialogGui(mc.currentScreen,packetIn.getData());
        mc.displayGuiScreen(gui);
    }
    @SideOnly(Side.SERVER)
    public static void ApplyDialog(DialogPacket packetIn, MessageContext ctx) {

    }
    @Override
    public IMessage onMessage(DialogPacket message, MessageContext ctx) {
        ApplyDialog(message);
        return null;
    }
}
