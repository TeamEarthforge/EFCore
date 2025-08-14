package com.earthforge.efcore.network;

import com.earthforge.efcore.feature.animation.Animation;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Vec3;
import noppes.npcs.scripted.ScriptParticle;

import java.io.IOException;

public class ParticleEmiPacket implements IMessage {
    private double posX, posY, posZ;
    private float pitch, yaw, roll;
    private int count;
    private String emitterType,animation;
    private double speed;
    private float radius;
    private NBTTagCompound particle;
    @Override
    public void fromBytes(ByteBuf buf) {
        try{
            readPacketData(new PacketBuffer(buf));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try{
            writePacketData(new PacketBuffer(buf));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public ParticleEmiPacket(){}
    public ParticleEmiPacket(double posX, double posY, double posZ, float pitch, float yaw,
                             float roll, int count, String emitterType, String animation, double speed, NBTTagCompound particle, float radius){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.count = count;
        this.emitterType = emitterType;
        this.animation = animation;
        this.speed = speed;
        this.particle = particle;
        this.radius = radius;
    }
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeDouble(posX);
        data.writeDouble(posY);
        data.writeDouble(posZ);
        data.writeFloat(pitch);
        data.writeFloat(yaw);
        data.writeFloat(roll);
        data.writeInt(count);
        data.writeStringToBuffer(emitterType);
        data.writeStringToBuffer(animation);
        data.writeDouble(speed);
        data.writeNBTTagCompoundToBuffer(particle);
        data.writeFloat(radius);
    }
    public void readPacketData(PacketBuffer data) throws IOException {
        posX = data.readDouble();
        posY = data.readDouble();
        posZ = data.readDouble();
        pitch = data.readFloat();
        yaw = data.readFloat();
        roll = data.readFloat();
        count = data.readInt();
        emitterType = data.readStringFromBuffer(32767);
        animation = data.readStringFromBuffer(32767);
        speed = data.readDouble();
        particle = data.readNBTTagCompoundFromBuffer();
        radius = data.readFloat();
    }

    public Vec3 getPosition(){
        return Vec3.createVectorHelper(posX, posY, posZ);
    }
    public float getPitch(){
        return pitch;
    }
    public float getYaw(){
        return yaw;
    }
    public float getRoll(){
        return roll;
    }
    public int getCount(){
        return count;
    }
    public String getEmitterType(){
        return emitterType;
    }
    public Animation getAnimation(){
        return Animation.fromJson(animation);
    }
    public double getSpeed(){
        return speed;
    }
    public ScriptParticle getParticle(){
        return ScriptParticle.fromNBT(particle);
    }
    public float getRadius(){
        return radius;
    }




}
