package com.earthforge.efcore.api;

import com.earthforge.efcore.camera.CameraAnimationFrame;

public interface ICameraAnimation {
    ICameraAnimationFrame getFrame(int index);
    public void addFrame(ICameraAnimationFrame frame);
    public void addFrame();
    public String toJson();
}
