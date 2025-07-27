package com.earthforge.efcore.api;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.camera.CameraAnimation;
import com.earthforge.efcore.camera.CameraAnimationFrame;
import com.earthforge.efcore.dialog.Dialog;
import com.earthforge.efcore.network.CameraAnimPacket;
import com.earthforge.efcore.network.CameraPacket;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IPlayer;


public class EFAPI extends AbstractEFAPI {
    private static AbstractEFAPI Instance;

    private EFAPI() {}

    public static AbstractEFAPI Instance() {
        if (EFAPI.Instance == null) {
            Instance = new EFAPI();
        }
        return Instance;
    }


    @Override
    public void changePlayerCamera(IPlayer<EntityPlayerMP> player, int camera) {
        CommonProxy.getChancel().sendTo(new CameraPacket(camera), player.getMCEntity());
    }
    @Override
    public void displayDialog(IPlayer<EntityPlayerMP> player,int dialog)  {
        EntityPlayerMP playerMP = player.getMCEntity();
    }

    @Override
    public IDialog newDialog() {
        return new Dialog();
    }

    @Override
    public ICameraAnimation newCameraAnimation() {
        return new CameraAnimation();
    }

    @Override
    public ICameraAnimationFrame newCameraAnimationFrame() {
        return new CameraAnimationFrame();
    }

    @Override
    public void sendCameraAnimation(IPlayer<EntityPlayerMP> player, ICameraAnimation animation) {
        CommonProxy.getChancel().sendTo(new CameraAnimPacket(animation.toJson()), player.getMCEntity());
    }

}
