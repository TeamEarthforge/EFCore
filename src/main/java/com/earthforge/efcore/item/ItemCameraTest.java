package com.earthforge.efcore.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.network.CameraPacket;

public class ItemCameraTest extends Item {

    public ItemCameraTest() {
        super();
        this.setUnlocalizedName("itemCameraTest");
        this.setTextureName("efcore:itemCameraTest");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {

            CommonProxy.getChancel()
                .sendTo(new CameraPacket(2), (EntityPlayerMP) player);

        }
        return itemStack; // 保持物品交互后状态不变
    }
}
