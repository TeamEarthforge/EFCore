package com.earthforge.efcore.network;

import com.earthforge.efcore.dialog.data.DialogData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import noppes.npcs.util.JsonException;

import java.io.IOException;

public class DialogPacket implements IMessage {
    private DialogData data;



    public DialogPacket() {}
    public DialogPacket(DialogData data) {
        this.data = data;

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        try{
            this.data = DialogData.readFromNBT(new PacketBuffer(buf).readNBTTagCompoundFromBuffer());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void toBytes(ByteBuf buf)  {
        try{
            new PacketBuffer(buf).writeNBTTagCompoundToBuffer(this.data.writeToNBT());
        }catch(JsonException | IOException e){
            throw new RuntimeException(e);
        }
    }

    public DialogData getData() {
        return data;
    }
}
