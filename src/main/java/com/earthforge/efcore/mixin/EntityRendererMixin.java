package com.earthforge.efcore.mixin;

import com.earthforge.efcore.camera.CameraManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Inject(method = "Lnet/minecraft/client/renderer/EntityRenderer;orientCamera(F)V",
            at = @At(value = "HEAD"))
    private void onOrientCamera(float partialTicks,CallbackInfo ci) {
        if(CameraManager.getInstance().isCameraEnabled()){
            return;
        }
    }
}
