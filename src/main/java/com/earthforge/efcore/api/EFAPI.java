package com.earthforge.efcore.api;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.feature.animation.Animation;
import com.earthforge.efcore.feature.animation.AnimationFrame;
import com.earthforge.efcore.feature.dialog.Dialog;
import com.earthforge.efcore.feature.skillpool.SkillPool;
import com.earthforge.efcore.feature.skillpool.SkillPoolManager;
import com.earthforge.efcore.feature.skillpool.skill.FixedTimeSkill;
import com.earthforge.efcore.feature.skillpool.skill.HealthBasedSkill;
import com.earthforge.efcore.feature.skillpool.skill.ISkill;
import com.earthforge.efcore.feature.skillpool.skill.RandomTriggerSkill;
import com.earthforge.efcore.network.CameraAnimPacket;
import com.earthforge.efcore.network.CameraPacket;
import com.earthforge.efcore.network.ParticleEmiPacket;
import cpw.mods.fml.common.Loader;
import io.github.cruciblemc.necrotempus.NecroTempus;
import io.github.cruciblemc.necrotempus.api.actionbar.ActionBar;
import io.github.cruciblemc.necrotempus.api.title.TitleComponent;
import io.github.cruciblemc.necrotempus.api.title.TitleElement;
import io.github.cruciblemc.necrotempus.modules.features.actionbar.network.ActionBarPacket;
import io.github.cruciblemc.necrotempus.modules.features.title.network.TitlePacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import noppes.npcs.api.IParticle;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.ScriptParticle;


public class EFAPI extends AbstractEFAPI {
    private static final boolean isNecrotempusLoaded = Loader.isModLoaded("necrotempus");
    private static AbstractEFAPI Instance;

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
    public IAnimation newAnimation() {
        return new Animation();
    }

    @Override
    public IAnimationFrame newAnimationFrame() {
        return new AnimationFrame();
    }

    @Override
    public void sendCameraAnimation(IPlayer<EntityPlayerMP> player, IAnimation animation) {
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

    @Override
    public SkillPool getOrCreateSkillPool(ICustomNpc<EntityNPCInterface> npc) {
        return SkillPoolManager.getInstance().getOrCreate(npc.getMCEntity());
    }

    @Override
    public ISkill newFixedTimeSkill(String name, int time) {
        return new FixedTimeSkill(name, time);
    }

    @Override
    public ISkill newHealthBasedSkill(String name, float health) {
        return new HealthBasedSkill(name, health);
    }

    @Override
    public ISkill newRandomTriggerSkill(String name, double chance) {
        return new RandomTriggerSkill(name, chance);
    }

    @Override
    public void sendParticleEmi(IPlayer<EntityPlayerMP> player, double posX, double posY, double posZ, float pitch, float yaw, float roll, int count, String emitterType, IAnimation animation, double speed, IParticle particle, float radius) {
        if (particle instanceof ScriptParticle) {
            CommonProxy.getChancel().sendTo(new ParticleEmiPacket(posX, posY, posZ, pitch, yaw, roll, count, emitterType, animation.toJson(), speed, ((ScriptParticle) particle).writeToNBT(), radius), player.getMCEntity());
        }
    }

}
