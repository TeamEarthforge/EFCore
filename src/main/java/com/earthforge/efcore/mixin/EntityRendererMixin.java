package com.earthforge.efcore.mixin;

import com.earthforge.efcore.feature.camera.CameraManager;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Shadow
    private float prevCamRoll;
    @Shadow
    private float camRoll;
    @Inject(
        method = "Lnet/minecraft/client/renderer/EntityRenderer;orientCamera(F)V",
            at = @At(value = "HEAD"),cancellable = true)
    private void onOrientCamera(float partialTicks,CallbackInfo ci) {
        CameraManager cm = CameraManager.getInstance();
        if(cm.isCameraEnabled()){
            cm.getCamera().applyToOpenGL(partialTicks);
            ci.cancel();
        }
    }
}
