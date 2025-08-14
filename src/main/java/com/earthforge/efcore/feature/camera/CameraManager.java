package com.earthforge.efcore.feature.camera;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CameraManager {
    private static CameraManager instance;
    private boolean isCameraEnabled = false;
    private Camera camera;
    private CameraManager() {}

    public static CameraManager getInstance() {
        if (instance == null) {
            instance = new CameraManager();
        }
        return instance;
    }

    public void CameraActive() {
            isCameraEnabled = true;
    }
    public void CameraInactive() {
            isCameraEnabled = false;
    }
    public boolean isCameraEnabled() {
        return isCameraEnabled;
    }
    public Camera getCamera() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }

}
