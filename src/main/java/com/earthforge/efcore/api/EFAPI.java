package com.earthforge.efcore.api;

import com.earthforge.efcore.CommonProxy;
import com.earthforge.efcore.dialog.Dialog;
import com.earthforge.efcore.dialog.data.DialogData;
import com.earthforge.efcore.network.CameraPacket;
import com.earthforge.efcore.network.DialogPacket;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.api.entity.IPlayer;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EFAPI extends AbstractEFAPI {
    private static AbstractEFAPI Instance;

    private EFAPI() {}

    public static AbstractEFAPI Instance() {
        if (EFAPI.Instance == null) {
            Instance = new EFAPI();
        }
        return Instance;
    }

    @Override
    public void changePlayerCamera(IPlayer<EntityPlayerMP> player, int camera) {
        CommonProxy.getChancel().sendTo(new CameraPacket(camera), player.getMCEntity());
    }
    @Override
    public void displayDialog(IPlayer<EntityPlayerMP> player,int dialog)  {
        EntityPlayerMP playerMP = player.getMCEntity();
    }

    @Override
    public IDialog newDialog() {
        return new Dialog();
    }
}
