package com.earthforge.efcore.api;

import com.earthforge.efcore.dialog.Dialog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import noppes.npcs.api.entity.IEntityLivingBase;
import noppes.npcs.api.entity.IPlayer;

import java.io.File;

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

}
