package com.earthforge.efcore.item;

import com.earthforge.efcore.dialog.data.DialogData;
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
            try {
                DialogData data = getDialogData();
                if (data.parts() == null || data.parts().isEmpty()) {
                    System.out.println("对话数据为空");
                    return itemStack;
                }

                DialogData.DialogPart firstLine = data.parts().get(0);
                if (firstLine == null) {
                    System.out.println("firstline is null");
                    return itemStack;
                }

                DialogData.CharacterData speaker = data.getCharacter(firstLine.characterid());
                if (speaker == null) {
                    System.out.println("cannot find ID: " + firstLine.characterid());
                    return itemStack;
                }

                System.out.println(speaker.name() + ": " + firstLine.content());
                System.out.println("image: " + speaker.getImage(firstLine.emotion()));
                System.out.println("position: " + firstLine.defaultPosition(data));
            } catch (Exception e) {
                System.out.println("something wrong: " + e.getMessage());
                e.printStackTrace();
            }
        /*{
            //BlockPos pos = new BlockPos(player.posX, player.posY, player.posZ);
            //int id = ModScreens.DIALOG;
            //player.openGui(EFCore.instance, id, world,BlockPos.ORIGIN.getX(), BlockPos.ORIGIN.getY(), BlockPos.ORIGIN.getZ());
            DialogData data = getDialogData();
            DialogData.DialogPart firstLine = data.parts().get(0);
            DialogData.CharacterData speaker = data.getCharacter(firstLine.characterid());

            System.out.println(speaker.name() + ": " + firstLine.content());
            System.out.println("立绘: " + speaker.getImage(firstLine.emotion()));
            System.out.println("位置: " + firstLine.defaultPosition(data));

        }*/
        }
        return itemStack;
    }
}


