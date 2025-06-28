package com.earthforge.efcore.dialog;

import com.earthforge.efcore.api.IDialog;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IPlayer;

import java.util.HashMap;
import java.util.Map;

public class DialogManager {
    public static DialogManager instance = new DialogManager();
    private final Map<EntityPlayerMP,Dialog> dialogs = new HashMap<>();


    public static DialogManager getInstance(){
        return instance;
    }
    public void start(EntityPlayerMP player,Dialog dialog){
        dialogs.put(player,dialog);
        dialog.scripts.run("start",(IDialog)dialog);
    }
    private void levelX(Dialog dialog,int page){
        dialog.scripts.run("Level"+page,(IDialog)dialog);
    }
    public void dispose(Dialog dialog){
        EntityPlayerMP player = dialog.getPlayer().getMCEntity();
        dispose(player);
    }
    public void dispose(EntityPlayerMP player){
        dialogs.remove(player);
    }
}
