package com.earthforge.efcore.api;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IPlayer;

import javax.annotation.Nullable;
import java.util.List;

public interface IDialog {
    public IPlayer getPlayer();
    public void enableScript();
    public void show(IPlayer<EntityPlayerMP> player);
    public void clear();
    public void clearPage(int page);
    public void addText(String name, String text, @Nullable String portrait, String side);
    public void addOptions(List<String> options, List<Integer> levels);
    public void dispose();
    public void setDefaultLevel(int level);
    public void fromJsonDialog(String name) throws Exception;


}
