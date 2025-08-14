package com.earthforge.efcore.api;

public interface IAnimation {
    IAnimationFrame getFrame(int index);
    public void addFrame(IAnimationFrame frame);
    public void addFrame();
    public String toJson();
}
