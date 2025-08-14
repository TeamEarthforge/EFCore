package com.earthforge.efcore.feature.shop;

import com.google.gson.Gson;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<ShopItem> items;
    private String currencyName;
    private Shop(){
        items = new ArrayList<>();
    }
    public Shop(String currencyName, List<ShopItem> items){
        this.currencyName = currencyName;
        this.items = items;
    }
    private void addItem(ShopItem item)
    {
        items.add(item);
    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public void sendShop(EntityPlayerMP player){
        //TODO
    }
    public void buy(EntityPlayerMP player, int itemIndex){
        if (itemIndex >= 0 && itemIndex < items.size()) {
            ShopItem shopItem = items.get(itemIndex);
            if (shopItem.getBuyable()) {
                int playerScore = player.getWorldScoreboard().func_96529_a(player.getCommandSenderName(), player.getWorldScoreboard().getObjective(currencyName)).getScorePoints();
                if (playerScore >= shopItem.getPrice()) {
                    player.inventory.addItemStackToInventory(shopItem.getItem().copy());
                    player.addScore(-shopItem.getPrice());
                }
            }
        }
    }
    public void dispose(){

    }


}
