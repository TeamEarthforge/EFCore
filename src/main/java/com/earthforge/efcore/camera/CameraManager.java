package com.earthforge.efcore.camera;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CameraManager {
    private static CameraManager instance;
    private boolean isCameraEnabled = false;
    private CameraManager() {}

    public static CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    public void setupCamera() {
        if (!isCameraEnabled) {
            isCameraEnabled = true;
        }
    }
    public boolean isCameraEnabled() {
        return isCameraEnabled;
    }

}
