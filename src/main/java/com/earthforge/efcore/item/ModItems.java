package com.earthforge.efcore.item;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {
    public static ItemCameraTest itemCameraTest = new ItemCameraTest();

    public ModItems(FMLPreInitializationEvent event) {
        register(itemCameraTest,"camera_test");
    }
    private static void register(Item item,String name) {
        GameRegistry.registerItem(item, name);
    }
}
