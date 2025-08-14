package com.earthforge.efcore.api;

import com.earthforge.efcore.feature.skillpool.SkillPool;
import com.earthforge.efcore.feature.skillpool.skill.ISkill;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.IParticle;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.entity.EntityNPCInterface;
import noppes.npcs.scripted.ScriptParticle;

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
    public abstract IAnimation newAnimation();
    public abstract IAnimationFrame newAnimationFrame();
    public abstract void sendCameraAnimation(IPlayer<EntityPlayerMP> player, IAnimation animation);
    public abstract void sendActionbar(IPlayer<EntityPlayerMP> player, String text,int time);
    public abstract void removeActionbar(IPlayer<EntityPlayerMP> player);
    public abstract void sendTitle(IPlayer<EntityPlayerMP> player, String title, String subtitle, int fadeIn, int stay, int fadeOut);
    public abstract void removeTitle(IPlayer<EntityPlayerMP> player);
    public abstract SkillPool getOrCreateSkillPool(ICustomNpc<EntityNPCInterface> npc);
    public abstract ISkill newFixedTimeSkill(String name,int time);
    public abstract ISkill newHealthBasedSkill(String name,float health);
    public abstract ISkill newRandomTriggerSkill(String name,double chance);
    public abstract void sendParticleEmi(IPlayer<EntityPlayerMP> player, double posX, double posY, double posZ, float pitch, float yaw, float roll, int count, String emitterType, IAnimation animation, double speed, IParticle particle, float radius);
}
