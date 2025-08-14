package com.earthforge.efcore.feature.particle;

import com.earthforge.efcore.feature.camera.Camera;
import com.earthforge.efcore.feature.camera.CameraManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class ParticleEmitterManager {
    private static ParticleEmitterManager instance;
    private Set<ParticleEmitter> emitters = new HashSet<>();
    private ParticleEmitterManager() {}

    public static ParticleEmitterManager getInstance() {
        if (instance == null) {
            instance = new ParticleEmitterManager();
        }
        return instance;
    }
    public void addEmitter(ParticleEmitter emitter){
        emitters.add(emitter);
    }
    public void removeEmitter(ParticleEmitter emitter){
        emitters.remove(emitter);
    }
    public void tick(){
        for(ParticleEmitter emitter : new ArrayList<>(emitters)){
            emitter.tick();
        }
    }
}
