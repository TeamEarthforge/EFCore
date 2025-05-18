package com.earthforge.efcore.gui.dialog;

import com.earthforge.efcore.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerDialog extends Container {
    protected final EntityPlayer player;
    public ContainerDialog(EntityPlayer player) {
        this.player = player;
    }
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return new ItemStack(ModItems.itemDialogTest).isItemEqual(player.getCurrentEquippedItem());
    }
}
