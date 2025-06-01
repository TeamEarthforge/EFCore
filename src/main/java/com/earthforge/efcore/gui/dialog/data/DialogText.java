package com.earthforge.efcore.gui.dialog.data;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DialogText {
    private String name;
    StringBuilder currentLine = new StringBuilder();

    private int lineWidth;
    public ResourceLocation image;
    public List<String> lines = new ArrayList();

    public DialogText(String name, String text, int lineWidth) {
        lines.add("");
        this.name = name;
        this.lineWidth = lineWidth;
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        for (char c : text.toCharArray()) {
            String testStr = currentLine.toString() + c;
            if (font.getStringWidth(testStr) > lineWidth) {
                lines.add(currentLine.toString()); // 保存当前行
                currentLine = new StringBuilder(String.valueOf(c)); // 新行以当前字符开始
            } else {
                currentLine.append(c);
            }
        }

        if (currentLine.length()>0) {
            lines.add(currentLine.toString());
        }
    }
    public String getName() {return name;}

}
