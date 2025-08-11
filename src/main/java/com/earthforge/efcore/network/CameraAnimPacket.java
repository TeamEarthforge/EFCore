package com.earthforge.efcore.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class CameraAnimPacket implements IMessage {
    private String data;



    public CameraAnimPacket() {}
    public CameraAnimPacket(String data) {
        this.data = data;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        try{
            this.data =new PacketBuffer(buf).readStringFromBuffer(32767);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void toBytes(ByteBuf buf)  {
        try{
            new PacketBuffer(buf).writeStringToBuffer(data);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public String getData() {
        return data;
    }
}
