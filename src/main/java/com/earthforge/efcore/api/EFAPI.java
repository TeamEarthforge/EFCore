package com.earthforge.efcore.api;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.camera.CameraAnimation;
import com.earthforge.efcore.camera.CameraAnimationFrame;
import com.earthforge.efcore.dialog.Dialog;
import com.earthforge.efcore.network.CameraAnimPacket;
import com.earthforge.efcore.network.CameraPacket;
import cpw.mods.fml.common.Loader;
import io.github.cruciblemc.necrotempus.NecroTempus;
import io.github.cruciblemc.necrotempus.api.actionbar.ActionBar;
import io.github.cruciblemc.necrotempus.api.actionbar.ActionBarManager;
import io.github.cruciblemc.necrotempus.api.bossbar.BossBar;
import io.github.cruciblemc.necrotempus.api.bossbar.BossBarColor;
import io.github.cruciblemc.necrotempus.api.bossbar.BossBarManager;
import io.github.cruciblemc.necrotempus.api.bossbar.BossBarType;
import io.github.cruciblemc.necrotempus.api.title.TitleComponent;
import io.github.cruciblemc.necrotempus.api.title.TitleElement;
import io.github.cruciblemc.necrotempus.modules.features.actionbar.network.ActionBarPacket;
import io.github.cruciblemc.necrotempus.modules.features.bossbar.network.BossBarPacket;
import io.github.cruciblemc.necrotempus.modules.features.bossbar.server.BossBarManagerServer;
import io.github.cruciblemc.necrotempus.modules.features.title.network.TitlePacket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import noppes.npcs.api.entity.IPlayer;

import java.util.UUID;


public class EFAPI extends AbstractEFAPI {
    private static AbstractEFAPI Instance;
    private static final boolean isNecrotempusLoaded = Loader.isModLoaded("necrotempus");

    private EFAPI() {
    }

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
    public void displayDialog(IPlayer<EntityPlayerMP> player, int dialog) {
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

    @Override
    public void sendActionbar(IPlayer<EntityPlayerMP> player, String text, int time) {
        if (!isNecrotempusLoaded) {
            return;
        }
        NecroTempus.DISPATCHER.sendTo(new ActionBarPacket(new ActionBar(time, new ChatComponentText(text)), ActionBarPacket.PacketType.SET), player.getMCEntity());
    }

    @Override
    public void removeActionbar(IPlayer<EntityPlayerMP> player) {
        if (!isNecrotempusLoaded) {
            return;
        }
        NecroTempus.DISPATCHER.sendTo(new ActionBarPacket(new ActionBar(0, new ChatComponentText("")), ActionBarPacket.PacketType.REMOVE), player.getMCEntity());
    }

    @Override
    public void sendTitle(IPlayer<EntityPlayerMP> player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (!isNecrotempusLoaded) {
            return;
        }
        TitleComponent component = new TitleComponent();
        component.addElement(TitleElement.titleOf(new ChatComponentText(title)));
        component.addElement(TitleElement.subtitleOf(new ChatComponentText(subtitle)));
        component.setFadeIn(fadeIn);
        component.setStay(stay);
        component.setFadeOut(fadeOut);
        NecroTempus.DISPATCHER.sendTo(new TitlePacket(component, TitlePacket.PacketType.SET), player.getMCEntity());
    }

    @Override
    public void removeTitle(IPlayer<EntityPlayerMP> player) {
        if (!isNecrotempusLoaded) {
            return;
        }
        NecroTempus.DISPATCHER.sendTo(new TitlePacket(new TitleComponent(), TitlePacket.PacketType.REMOVE), player.getMCEntity());
    }

}
