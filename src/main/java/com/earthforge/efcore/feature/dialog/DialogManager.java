package com.earthforge.efcore.feature.dialog;

import com.earthforge.efcore.api.IDialog;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;

public class DialogManager {
    private static DialogManager instance = new DialogManager();
    private final Map<EntityPlayerMP,Dialog> dialogs = new HashMap<>();


    public static DialogManager getInstance(){
        return instance;
    }
    public void addDialog(EntityPlayerMP player, Dialog dialog){
        dialogs.put(player, dialog);
    }

    public void levelX(EntityPlayerMP player,int page){
        Dialog dialog = dialogs.get(player);
        if (dialog != null) {
            if(dialog.enableScript){
            dialog.scripts.run("Level" + page, (IDialog) dialog);
            }
        }
    }
    public void dispose(Dialog dialog){
        EntityPlayerMP player = dialog.getPlayer().getMCEntity();
        dispose(player);
    }
    public void dispose(EntityPlayerMP player){
        dialogs.remove(player);
    }
}
