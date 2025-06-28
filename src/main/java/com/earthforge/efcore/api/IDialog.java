package com.earthforge.efcore.api;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IPlayer;

import javax.annotation.Nullable;

public interface IDialog {
    public void fromJsonDialog(String filename);
    public IPlayer getPlayer();
    public void enableScript();
    public void show(IPlayer<EntityPlayerMP> player);
    public void sendText(String text, @Nullable String portrait);
    public void sendTextWithOptions(String text,String selects, @Nullable String portrait);
    public void dispose();

}
