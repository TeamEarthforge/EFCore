package com.earthforge.efcore.item;

import com.earthforge.efcore.feature.dialog.data.DialogData;
import com.google.gson.Gson;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.io.*;

public class ItemDialogTest extends Item {

        public ItemDialogTest() {
            super();
            this.setUnlocalizedName("itemDialogTest");
            this.setTextureName("efcore:itemDialogTest");
        }

    public DialogData getDialogData() {
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/efcore/dialog/abc.json");
            if (stream == null) {
                throw new FileNotFoundException("Could not find dialog file");
            }
            Reader reader = new InputStreamReader(stream);
            DialogData data = new Gson().fromJson(reader, DialogData.class);
            reader.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dialog data", e);
        }
    }

        @Override
        public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
        }
        return itemStack;
    }
}


