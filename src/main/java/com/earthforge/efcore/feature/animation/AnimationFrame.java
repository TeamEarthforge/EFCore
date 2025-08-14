package com.earthforge.efcore.feature.animation;

import com.earthforge.efcore.api.IAnimationFrame;
import net.minecraft.util.Vec3;

import javax.vecmath.Vector3f;

public class AnimationFrame implements IAnimationFrame {
    private InterpolationType interpolationType;
    private Vec3 position;
    private float pitch;
    private float yaw;
    private float roll;
    private float time;//秒
    private boolean useCurve = false; // 是否使用曲线插值
    private Vec3 curvePoint;

    public AnimationFrame() {
        this.position = Vec3.createVectorHelper(5.0F, 5.0F, 0.0F);
        this.pitch = 0.0F;
        this.yaw = 0.0F;
        this.roll = 0.0F;
        this.time = 3.0F; // 默认时间为1秒
        this.interpolationType = InterpolationType.LINEAR; // 默认插值类型
    }

    public Vec3 getCurvePosition(float t, Vec3 startPosition) {
        if (!useCurve || curvePoint == null) {
            return position;
        }
        // 使用贝塞尔曲线计算位置
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float x = (float) (uu * startPosition.xCoord +
            2 * u * t * curvePoint.xCoord +
            tt * position.xCoord);

        float y = (float) (uu * startPosition.yCoord +
            2 * u * t * curvePoint.yCoord +
            tt * position.yCoord);

        float z = (float) (uu * startPosition.zCoord +
            2 * u * t * curvePoint.zCoord +
            tt * position.zCoord);
        return Vec3.createVectorHelper(x, y, z);
    }

    @Override
    public void enableCurveInterpolation(float x, float y, float z) {
        enableCurveInterpolation(Vec3.createVectorHelper(x, y, z));
    }

    public void enableCurveInterpolation(Vec3 curvePoint) {
        this.useCurve = true;
        this.curvePoint = curvePoint;
    }

    public void disableCurveInterpolation() {
        this.useCurve = false;
        this.curvePoint = null;
    }

    public boolean isUsingCurveInterpolation() {
        return useCurve;
    }

    /**
     * 设置摄像机的绝对位置。
     *
     * @param x X坐标
     * @param y Y坐标
     * @param z Z坐标
     */
    @Override
    public void setPosition(float x, float y, float z) {
        this.position.xCoord = x;
        this.position.yCoord = y;
        this.position.zCoord = z;
    }

    /**
     * 移动摄像机，相对于其当前朝向移动。
     * 假设：正Z轴为前方，正X轴为右方，正Y轴为上方。
     *
     * @param dx 沿X轴的移动量 (左右)
     * @param dy 沿Y轴的移动量 (上下)
     * @param dz 沿Z轴的移动量 (前后)
     */
    @Override
    public void moveLocal(float dx, float dy, float dz) {
        // 将局部移动转换为世界坐标系的移动
        Vector3f forward = new Vector3f(
            (float) Math.sin(Math.toRadians(this.yaw)),
            0.0F,
            (float) -Math.cos(Math.toRadians(this.yaw))
        );
        Vector3f right = new Vector3f(
            (float) Math.cos(Math.toRadians(this.yaw)),
            0.0F,
            (float) Math.sin(Math.toRadians(this.yaw))
        );

        // 计算新的位置
        this.position.xCoord += forward.x * dz + right.x * dx;
        this.position.yCoord += dy; // Y轴直接加上dy
        this.position.zCoord += forward.z * dz + right.z * dx;
    }

    /**
     * 移动摄像机，沿世界坐标轴移动。
     *
     * @param dx 世界X轴上的移动量
     * @param dy 世界Y轴上的移动量
     * @param dz 世界Z轴上的移动量
     */
    @Override
    public void moveGlobal(float dx, float dy, float dz) {
        this.position.xCoord += dx;
        this.position.yCoord += dy;
        this.position.zCoord += dz;
    }

    /**
     * 旋转摄像机的俯仰角 (上下看)。
     *
     * @param deltaPitch 角度增量（度）
     */
    @Override
    public void rotatePitch(float deltaPitch) {
        this.pitch += deltaPitch;
        if (this.pitch > 90.0F) this.pitch = 90.0F;
        if (this.pitch < -90.0F) this.pitch = -90.0F;
    }

    /**
     * 旋转摄像机的偏航角 (左右看)。
     *
     * @param deltaYaw 角度增量（度）
     */
    @Override
    public void rotateYaw(float deltaYaw) {
        this.yaw += deltaYaw;
        this.yaw %= 360.0F;
        if (this.yaw < 0) this.yaw += 360.0F;
    }

    /**
     * 旋转摄像机的横滚角 (倾斜)。
     *
     * @param deltaRoll 角度增量（度）
     */
    @Override
    public void rotateRoll(float deltaRoll) {
        this.roll += deltaRoll;
        this.roll %= 360.0F;
        if (this.roll < 0) this.roll += 360.0F;
    }

    /**
     * 设置摄像机的绝对旋转角度。
     *
     * @param pitch 俯仰角 (度)
     * @param yaw   偏航角 (度)
     * @param roll  翻滚角 (度)
     */
    @Override
    public void setRotation(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        if (this.pitch > 90.0F) this.pitch = 90.0F;
        if (this.pitch < -90.0F) this.pitch = -90.0F;
        this.yaw %= 360.0F;
        if (this.yaw < 0) this.yaw += 360.0F;
        this.roll %= 360.0F;
        if (this.roll < 0) this.roll += 360.0F;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getRoll() {
        return roll;
    }

    public Vec3 getPosition() {
        return position;
    }

    @Override
    public float getTime() {
        return time;
    }

    @Override
    public void setTime(float time) {
        this.time = time;
    }

    public InterpolationType getInterpolationType() {
        return interpolationType;
    }

    @Override
    public void setInterpolationType(String type) {
        switch (type.toLowerCase()) {
            case "linear":
                this.interpolationType = InterpolationType.LINEAR;
                break;
            case "ease_in_sine":
                this.interpolationType = InterpolationType.EASE_IN_SINE;
                break;
            case "ease_out_sine":
                this.interpolationType = InterpolationType.EASE_OUT_SINE;
                break;
            case "ease_in_out_sine":
                this.interpolationType = InterpolationType.EASE_IN_OUT_SINE;
                break;
            case "ease_in_quad":
                this.interpolationType = InterpolationType.EASE_IN_QUAD;
                break;
            case "ease_out_quad":
                this.interpolationType = InterpolationType.EASE_OUT_QUAD;
                break;
            case "ease_in_out_quad":
                this.interpolationType = InterpolationType.EASE_IN_OUT_QUAD;
                break;
            case "ease_in_cubic":
                this.interpolationType = InterpolationType.EASE_IN_CUBIC;
                break;
            case "ease_out_cubic":
                this.interpolationType = InterpolationType.EASE_OUT_CUBIC;
                break;
            case "ease_in_out_cubic":
                this.interpolationType = InterpolationType.EASE_IN_OUT_CUBIC;
                break;
            case "ease_in_quart":
                this.interpolationType = InterpolationType.EASE_IN_QUART;
                break;
            case "ease_out_quart":
                this.interpolationType = InterpolationType.EASE_OUT_QUART;
                break;
            case "ease_in_out_quart":
                this.interpolationType = InterpolationType.EASE_IN_OUT_QUART;
                break;
            case "ease_in_quint":
                this.interpolationType = InterpolationType.EASE_IN_QUINT;
                break;
            case "ease_out_quint":
                this.interpolationType = InterpolationType.EASE_OUT_QUINT;
                break;
            case "ease_in_out_quint":
                this.interpolationType = InterpolationType.EASE_IN_OUT_QUINT;
                break;
            case "ease_in_expo":
                this.interpolationType = InterpolationType.EASE_IN_EXPO;
                break;
            case "ease_out_expo":
                this.interpolationType = InterpolationType.EASE_OUT_EXPO;
                break;
            case "ease_in_out_expo":
                this.interpolationType = InterpolationType.EASE_IN_OUT_EXPO;
                break;
            case "ease_in_elastic":
                this.interpolationType = InterpolationType.EASE_IN_ELASTIC;
                break;
            case "ease_out_elastic":
                this.interpolationType = InterpolationType.EASE_OUT_ELASTIC;
                break;
            case "ease_in_out_elastic":
                this.interpolationType = InterpolationType.EASE_IN_OUT_ELASTIC;
                break;
            case "ease_out_back":
                this.interpolationType = InterpolationType.EASE_OUT_BACK;
                break;
            case "ease_in_back":
                this.interpolationType = InterpolationType.EASE_IN_BACK;
                break;
            case "ease_in_out_back":
                this.interpolationType = InterpolationType.EASE_IN_OUT_BACK;
                break;
            case "ease_out_bounce":
                this.interpolationType = InterpolationType.EASE_OUT_BOUNCE;
                break;
            case "ease_in_bounce":
                this.interpolationType = InterpolationType.EASE_IN_BOUNCE;
                break;
            case "ease_in_out_bounce":
                this.interpolationType = InterpolationType.EASE_IN_OUT_BOUNCE;
                break;
            default:
                break;
        }
    }

    public enum InterpolationType {
        LINEAR, // 线性插值
        EASE_IN_SINE, // 正弦缓入
        EASE_OUT_SINE, // 正弦缓出
        EASE_IN_OUT_SINE, // 正弦缓入缓出
        EASE_IN_QUAD, // 二次缓入
        EASE_OUT_QUAD, // 二次缓出
        EASE_IN_OUT_QUAD, // 二次缓入缓出
        EASE_IN_CUBIC, // 三次缓入
        EASE_OUT_CUBIC, // 三次缓出
        EASE_IN_OUT_CUBIC, // 三次缓入缓出
        EASE_IN_QUART, // 四次缓入
        EASE_OUT_QUART, // 四次缓出
        EASE_IN_OUT_QUART, // 四次缓入缓出
        EASE_IN_QUINT, // 五次缓入
        EASE_OUT_QUINT, // 五次缓出
        EASE_IN_OUT_QUINT, // 五次缓入缓出
        EASE_IN_EXPO, // 指数缓入
        EASE_OUT_EXPO, // 指数缓出
        EASE_IN_OUT_EXPO, // 指数缓入缓出
        EASE_IN_CIRC, // 圆形缓入
        EASE_OUT_CIRC, // 圆形缓出
        EASE_IN_OUT_CIRC, // 圆形缓入缓出
        EASE_IN_BACK, // 回弹缓入
        EASE_OUT_BACK, // 回弹缓出
        EASE_IN_OUT_BACK, // 回弹缓入缓出
        EASE_IN_BOUNCE, // 弹跳缓入
        EASE_OUT_BOUNCE, // 弹跳缓出
        EASE_IN_OUT_BOUNCE, // 弹跳缓入缓出
        EASE_IN_ELASTIC, // 弹性缓入
        EASE_OUT_ELASTIC, // 弹性缓出
        EASE_IN_OUT_ELASTIC, // 弹性缓入缓出
    }
}
