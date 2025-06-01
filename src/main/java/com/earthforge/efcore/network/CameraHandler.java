package com.earthforge.efcore.network;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class CameraHandler implements IMessageHandler<CameraPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public static void CameraChange(CameraPacket packetIn) {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = packetIn.getview();
    }


    @Override
    public IMessage onMessage(CameraPacket message, MessageContext ctx) {
        CameraChange(message);
        return null;
    }
}
