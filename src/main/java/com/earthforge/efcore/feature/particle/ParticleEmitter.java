package com.earthforge.efcore.feature.particle;

import com.earthforge.efcore.feature.animation.Animation;
import com.earthforge.efcore.feature.animation.AnimationFrame;
import com.earthforge.efcore.feature.animation.EasingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import noppes.npcs.client.fx.CustomFX;
import noppes.npcs.scripted.ScriptParticle;

public class ParticleEmitter {
    private Vec3 position;
    private float pitch;
    private float yaw;
    private float roll;
    private long startTime;
    private Animation animation;
    private int currentAnimationIndex = 0;
    private EmitterType emitterType;
    private float radius = 0;
    private double speed;
    private int count = 1;
    private ScriptParticle particle;
    Minecraft mc = Minecraft.getMinecraft();
    private World world = mc.theWorld;;
    private EntityPlayer player = mc.thePlayer;


    public ParticleEmitter(Vec3 position, float pitch, float yaw, float roll,Animation animation,int count, String emitterType,
                           double speed,ScriptParticle particle,float radius) {
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        this.emitterType = EmitterType.valueOf(emitterType);
        this.speed = speed;
        this.count = count;
        this.particle = particle;
        this.radius = radius;
        this.animation = animation;
        this.currentAnimationIndex = 0;
        this.startTime = System.currentTimeMillis();
    }
    public void tick() {
            long currentTime = System.currentTimeMillis();
            AnimationFrame currentFrame = this.animation.getFrameInstance(this.currentAnimationIndex);
            float elapsed = (currentTime - this.startTime) / 1000.0F; // 转换为秒
            if (elapsed >= currentFrame.getTime()) {
                this.position = currentFrame.getPosition();
                this.pitch = currentFrame.getPitch();
                this.yaw = currentFrame.getYaw();
                this.roll = currentFrame.getRoll();
                if (this.currentAnimationIndex < this.animation.getFrames().size() - 1) {
                    this.currentAnimationIndex++;
                    this.startTime = currentTime;
                } else {
                    ParticleEmitterManager.getInstance().removeEmitter(this);
                }
            } else {
                float t = elapsed / this.animation.getFrameInstance(this.currentAnimationIndex).getTime();
                float easedT = EasingUtils.ease(t, currentFrame.getInterpolationType());
                if (currentFrame.isUsingCurveInterpolation()) {
                    this.position = currentFrame.getCurvePosition(easedT, this.position);
                } else {
                    double rx = this.position.xCoord + easedT * (currentFrame.getPosition().xCoord - this.position.xCoord);
                    double ry = this.position.yCoord + easedT * (currentFrame.getPosition().yCoord - this.position.yCoord);
                    double rz = this.position.zCoord + easedT * (currentFrame.getPosition().zCoord - this.position.zCoord);
                    this.position = Vec3.createVectorHelper(rx, ry, rz);
                }
                this.pitch = this.pitch + easedT * (currentFrame.getPitch() - this.pitch);
                this.roll = this.roll + easedT * (currentFrame.getRoll() - this.roll);
                this.yaw = this.yaw + easedT * (currentFrame.getYaw() - this.yaw);
        }
        switch (emitterType) {
            case CIRCLE:
                for (int i = 0; i < count; i++) {
                    double angle = 2 * Math.PI * i / count;
                    double x = position.xCoord + radius * Math.cos(angle);
                    double z = position.zCoord + radius * Math.sin(angle);
                    double y = position.yCoord;
                    CustomFX fx2 = CustomFX.fromScriptedParticle(particle, world, player);
                    fx2.posX = x;
                    fx2.posY = y;
                    fx2.posZ = z;
                    mc.effectRenderer.addEffect(fx2);
                }
                break;
            case BLAST:
                for (int i = 0; i < count; i++) {
                    CustomFX fx3 = CustomFX.fromScriptedParticle(particle, world, player);
                    fx3.posX = position.xCoord;
                    fx3.posY = position.yCoord;
                    fx3.posZ = position.zCoord;
                    fx3.motionX = (Math.random() - 0.5) * 2 * speed;
                    fx3.motionY = (Math.random() - 0.5) * 2 * speed;
                    fx3.motionZ = (Math.random() - 0.5) * 2 * speed;
                    mc.effectRenderer.addEffect(fx3);
                }
                break;
            case POINT:
                CustomFX fx = CustomFX.fromScriptedParticle(particle, world, player);
                double yawRad = Math.toRadians(yaw);
                double pitchRad = Math.toRadians(pitch);
                double cosPitch = Math.cos(pitchRad);
                double sinPitch = Math.sin(pitchRad);
                double cosYaw = Math.cos(yawRad);
                double sinYaw = Math.sin(yawRad);
                fx.posX = position.xCoord;
                fx.posY = position.yCoord;
                fx.posZ = position.zCoord;
                fx.motionX = -sinYaw * cosPitch * speed;
                fx.motionY = -sinPitch * speed;
                fx.motionZ = cosYaw * cosPitch * speed;
                mc.effectRenderer.addEffect(fx);
                break;
            default:
                break;
        }
    }

    public enum EmitterType {
        CIRCLE,
        BLAST,
        POINT
    }

}
