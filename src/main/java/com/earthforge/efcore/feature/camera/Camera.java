package com.earthforge.efcore.feature.camera;

import com.earthforge.efcore.feature.animation.Animation;
import com.earthforge.efcore.feature.animation.AnimationFrame;
import com.earthforge.efcore.feature.animation.EasingUtils;
import com.earthforge.efcore.feature.camera.CameraManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Camera {
    private Vec3 position;
    private float pitch;
    private float yaw;
    private float roll;
    private long startTime;
    private boolean dirty = false;
    private Animation animation;
    private int currentAnimationIndex = 0;



    public Camera(){
        this.position = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.roll = 0.0f;
    }
    public Camera(Vec3 position, float pitch, float yaw, float roll){
        this.position = position;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }
    public void applyAnimation(Animation animation){
        this.animation = animation;
        this.currentAnimationIndex = 0;
        this.startTime = System.currentTimeMillis();
        this.dirty = true;
    }
    public void clear(){
        this.position = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        this.pitch = 0.0f;
        this.yaw = 0.0f;
        this.roll = 0.0f;
        this.dirty = false;
        this.animation = null;
        this.currentAnimationIndex = 0;
        this.startTime = 0;
    }
    /**
     * 更新摄像机状态。
     */
    public void applyToOpenGL(float partialTicks) {
        if(!this.dirty) {
            GL11.glRotatef(-this.roll, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-this.pitch, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-(this.yaw + 180.0F), 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef((float) -this.position.xCoord, (float) -this.position.yCoord, (float) -this.position.zCoord);
        }else{
            long currentTime = System.currentTimeMillis();
            AnimationFrame currentFrame = this.animation.getFrameInstance(this.currentAnimationIndex);
            float elapsed = (currentTime - this.startTime) / 1000.0F; // 转换为秒
            if (elapsed >= currentFrame.getTime()) {
                this.position = currentFrame.getPosition();
                this.pitch = currentFrame.getPitch();
                this.yaw = currentFrame.getYaw();
                this.roll = currentFrame.getRoll();
                if(this.currentAnimationIndex < this.animation.getFrames().size() - 1){
                    this.currentAnimationIndex++;
                    this.startTime = currentTime;
                    GL11.glTranslatef((float) -this.position.xCoord, (float) -this.position.yCoord, (float) -this.position.zCoord);
                    GL11.glRotatef(-this.roll, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-this.pitch, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-(this.yaw + 180.0F), 0.0F, 1.0F, 0.0F);
                }else{
                    clear();
                    CameraManager.getInstance().CameraInactive();
                }
            } else {
                float t = elapsed / this.animation.getFrameInstance(this.currentAnimationIndex).getTime();
                float easedT = EasingUtils.ease(t, currentFrame.getInterpolationType());
                float rp, rr, ryaw,rx, ry, rz;
                if(currentFrame.isUsingCurveInterpolation()){
                    Vec3 curvePoint = currentFrame.getCurvePosition(easedT, this.position);
                    rx = (float) curvePoint.xCoord;
                    ry = (float) curvePoint.yCoord;
                    rz = (float) curvePoint.zCoord;
                } else {
                    rx = (float) (this.position.xCoord + easedT * (currentFrame.getPosition().xCoord - this.position.xCoord));
                    ry = (float) (this.position.yCoord + easedT * (currentFrame.getPosition().yCoord - this.position.yCoord));
                    rz = (float) (this.position.zCoord + easedT * (currentFrame.getPosition().zCoord - this.position.zCoord));
                }
                rp = this.pitch + easedT * (currentFrame.getPitch() - this.pitch);
                rr = this.roll + easedT * (currentFrame.getRoll() - this.roll);
                ryaw = this.yaw + easedT * (currentFrame.getYaw() - this.yaw);
                GL11.glRotatef(-(rr), 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-(rp), 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-(ryaw + 180.0F), 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(
                    -(rx),
                    -(ry),
                    -(rz)
                );
            }
        }
    }
}
