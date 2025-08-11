package com.earthforge.efcore.api;

import com.earthforge.efcore.dialog.Dialog;
import io.github.cruciblemc.necrotempus.api.bossbar.BossBar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import noppes.npcs.api.entity.IEntityLivingBase;
import noppes.npcs.api.entity.IPlayer;

import java.io.File;
import java.util.UUID;

public abstract class AbstractEFAPI {
    private static AbstractEFAPI instance = null;
    public static AbstractEFAPI Instance() {
        if (instance != null) {
            return instance;
        } else {
            try {
                Class<?> c = Class.forName("com.earthforge.efcore.api.EFAPI");
                instance = (AbstractEFAPI) c.getMethod("Instance").invoke(null);

            } catch (Exception ignored) {}
            return instance;
        }
    }
    public abstract void changePlayerCamera(IPlayer<EntityPlayerMP> player,int camera);
    public abstract void displayDialog(IPlayer<EntityPlayerMP> player, int dialog);
    public abstract IDialog newDialog();
    public abstract ICameraAnimation newCameraAnimation();
    public abstract ICameraAnimationFrame newCameraAnimationFrame();
    public abstract void sendCameraAnimation(IPlayer<EntityPlayerMP> player, ICameraAnimation animation);
    public abstract void sendActionbar(IPlayer<EntityPlayerMP> player, String text,int time);
    public abstract void removeActionbar(IPlayer<EntityPlayerMP> player);
    public abstract void sendTitle(IPlayer<EntityPlayerMP> player, String title, String subtitle, int fadeIn, int stay, int fadeOut);
    public abstract void removeTitle(IPlayer<EntityPlayerMP> player);
}
