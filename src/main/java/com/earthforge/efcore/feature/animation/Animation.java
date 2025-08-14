package com.earthforge.efcore.feature.animation;

import com.earthforge.efcore.api.IAnimation;
import com.earthforge.efcore.api.IAnimationFrame;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Animation implements IAnimation {
    private List<AnimationFrame> frames;

    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.frames);
    }
    public static Animation fromJson(String json){
        Gson gson =new Gson();
        Animation anim = new Animation();
        anim.frames = gson.fromJson(json, new TypeToken<List<AnimationFrame>>(){}.getType());
        return anim;
    }
    public Animation(List<AnimationFrame> frames) {
        this.frames = frames;
    }
    public Animation() {
        this.frames = new java.util.ArrayList<>();
    }
    @Override
    public IAnimationFrame getFrame(int index) {
        return (IAnimationFrame) frames.get(index);
    }

    public List<AnimationFrame> getFrames() {
        return java.util.Collections.unmodifiableList(frames);
    }
    public AnimationFrame getFrameInstance(int index) {
        return frames.get(index);
    }
    @Override
    public void addFrame(IAnimationFrame frame) {
        frames.add((AnimationFrame) frame);
    }
    @Override
    public void addFrame(){
        frames.add(new AnimationFrame());
    }

}
