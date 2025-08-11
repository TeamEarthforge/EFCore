package com.earthforge.efcore.shop;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScoreObjective;
import noppes.npcs.scripted.scoreboard.ScriptScoreboardObjective;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<ShopItem> items;
    private String currencyName;
    private Shop(){
        items = new ArrayList<ShopItem>();
    }
    private void addItem(ShopItem item)
    {
        items.add(item);
    }
    public void sendShop(EntityPlayerMP player){
        //TODO
    }
    public void buy(EntityPlayerMP player, int itemIndex){
        //TODO
    }


}
