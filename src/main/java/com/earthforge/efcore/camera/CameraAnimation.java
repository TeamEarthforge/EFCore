package com.earthforge.efcore.camera;

import com.earthforge.efcore.api.ICameraAnimation;
import com.earthforge.efcore.api.ICameraAnimationFrame;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CameraAnimation implements ICameraAnimation {
    private List<CameraAnimationFrame> frames;

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.frames);
    }
    public static CameraAnimation fromJson(String json){
        Gson gson =new Gson();
        CameraAnimation anim = new CameraAnimation();
        anim.frames = gson.fromJson(json, new TypeToken<List<CameraAnimationFrame>>(){}.getType());
        return anim;
    }
    public CameraAnimation(List<CameraAnimationFrame> frames) {
        this.frames = frames;
    }
    public CameraAnimation() {
        this.frames = new java.util.ArrayList<>();
    }
    @Override
    public ICameraAnimationFrame getFrame(int index) {
        return (ICameraAnimationFrame) frames.get(index);
    }

    public List<CameraAnimationFrame> getFrames() {
        return java.util.Collections.unmodifiableList(frames);
    }
    public CameraAnimationFrame getFrameInstance(int index) {
        return frames.get(index);
    }
    @Override
    public void addFrame(ICameraAnimationFrame frame) {
        frames.add((CameraAnimationFrame) frame);
    }
    @Override
    public void addFrame(){
        frames.add(new CameraAnimationFrame());
    }

}
