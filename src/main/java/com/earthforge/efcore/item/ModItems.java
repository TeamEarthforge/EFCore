package com.earthforge.efcore.item;

import net.minecraft.item.Item;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

    public static ItemCameraTest itemCameraTest = new ItemCameraTest();
    public static ItemDialogTest itemDialogTest = new ItemDialogTest();

    public ModItems(FMLPreInitializationEvent event) {
        register(itemCameraTest, "camera_test");
        register(itemDialogTest, "dialog_test");
    }

    private static void register(Item item, String name) {
        GameRegistry.registerItem(item, name);
    }
}
