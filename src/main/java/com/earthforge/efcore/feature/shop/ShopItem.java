package com.earthforge.efcore.feature.shop;

import akka.japi.Pair;
import net.minecraft.item.ItemStack;



public class ShopItem {
    private final boolean buyable;
    private final ItemStack item;
    private final int price;

    public ShopItem(boolean buyable, ItemStack item, int price) {
        this.buyable = buyable;
        this.item = item;
        this.price = price;
    }

    public boolean getBuyable() {
        return buyable;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }
}
