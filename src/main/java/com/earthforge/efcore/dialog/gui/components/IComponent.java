package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.gui.Gui;

public interface IComponent {
    void render(Gui gui, int mousex, int mousey, float partialTicks);
}
