package com.earthforge.efcore.network;

import com.earthforge.efcore.camera.CameraAnimation;
import com.earthforge.efcore.camera.CameraAnimationFrame;
import com.earthforge.efcore.camera.CameraManager;
import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;


public class CameraHandler implements IMessageHandler<CameraPacket, IMessage> {

    @SideOnly(Side.CLIENT)
    public static void CameraChange(CameraPacket packetIn) {
        if (packetIn.getview() <= 2) {
            Minecraft.getMinecraft().gameSettings.thirdPersonView = packetIn.getview();
            return;
        }
        if (packetIn.getview() == 3) {
            CameraManager.getInstance().CameraActive();
        }
        if (packetIn.getview() == 4) {
            CameraManager.getInstance().CameraInactive();
        }
    }


    @Override
    public IMessage onMessage(CameraPacket message, MessageContext ctx) {
        CameraChange(message);
        return null;
    }
}
