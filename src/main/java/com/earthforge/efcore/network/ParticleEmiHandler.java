package com.earthforge.efcore.network;

import com.earthforge.efcore.feature.particle.ParticleEmitter;
import com.earthforge.efcore.feature.particle.ParticleEmitterManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ParticleEmiHandler implements IMessageHandler<ParticleEmiPacket, IMessage> {
    @Override
    public IMessage onMessage(ParticleEmiPacket message, MessageContext ctx) {
        if(ctx.side.isClient()){
            ParticleEmitter emitter = new ParticleEmitter(message.getPosition(),message.getPitch(),message.getYaw(),message.getRoll(),
                message.getAnimation(),message.getCount(),message.getEmitterType(),message.getSpeed(),message.getParticle(),message.getRadius());
            ParticleEmitterManager.getInstance().addEmitter(emitter);
        }
        return null;
    }
}
