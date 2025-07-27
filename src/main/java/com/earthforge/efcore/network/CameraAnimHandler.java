package com.earthforge.efcore.network;

import com.earthforge.efcore.camera.CameraAnimation;
import com.earthforge.efcore.camera.CameraManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CameraAnimHandler implements IMessageHandler<CameraAnimPacket, IMessage> {

    @Override
    public IMessage onMessage(CameraAnimPacket message, MessageContext ctx) {
        if (ctx.side.isClient()) {
            CameraAnimation anim = CameraAnimation.fromJson(message.getData());
            CameraManager.getInstance().getCamera().applyAnimation(anim);
        }
        return null;
    }
}
