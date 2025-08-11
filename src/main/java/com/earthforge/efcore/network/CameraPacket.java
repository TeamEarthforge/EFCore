package com.earthforge.efcore.network;

import java.io.IOException;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class CameraPacket implements IMessage {

    private int view;

    public CameraPacket() {}

    public CameraPacket(int view) {
        this.view = view;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            readPacketData(new PacketBuffer(buf));
        } catch (IOException e) {}

    }
    @Override
    public void toBytes(ByteBuf buf) {
        try {
            writePacketData(new PacketBuffer(buf));
        } catch (IOException e) {}
    }
    public void readPacketData(PacketBuffer data) throws IOException {
        this.view = data.readInt();
    }
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeInt(this.view);
    }

    @SideOnly(Side.CLIENT)
    public int getview() {
        return this.view;
    }

}
