package com.earthforge.efcore.api;

public interface IAnimationFrame {
    float getPitch();
    float getYaw();
    float getRoll();
    void enableCurveInterpolation(float x, float y, float z);
    void setTime(float time);
    void setPosition(float x, float y, float z);
    void moveLocal(float dx, float dy, float dz);
    void moveGlobal(float dx, float dy, float dz);
    void rotatePitch(float deltaPitch);
    void rotateYaw(float deltaYaw);
    void rotateRoll(float deltaRoll);
    void setRotation(float pitch, float yaw, float roll);
    void setInterpolationType(String type);
    float getTime();
}
