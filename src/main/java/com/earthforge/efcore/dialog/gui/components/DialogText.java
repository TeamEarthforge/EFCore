package com.earthforge.efcore.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import noppes.npcs.NoppesStringUtils;


import java.util.ArrayList;
import java.util.List;

public class DialogText implements IComponent{
    private List<String> line = new ArrayList();
    private int currentLine = 0;
    private int currentChar = 0;
    private int x;
    private int y;

    public boolean doCompletePage = false;
    FontRenderer font = Minecraft.getMinecraft().fontRenderer;



    public DialogText(String text, int x, int y, int width,Object... obs) {
        this.line = splitString(text,width,obs);
        this.x = x;
        this.y = y;

    }

    private List<String> splitString(String input, int lineLength, Object... obs) {
        String text = NoppesStringUtils.formatText(input, obs);
        StringBuilder currentLine = new StringBuilder();
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            // 处理换行符
            if (c == '\n' || c == '\r') {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine.setLength(0);
                }
                continue;
            }

            String testLine = currentLine.toString() + c;
            int width = font.getStringWidth(testLine);
            if (width > lineLength) {
                if (currentLine.length() == 0) {
                    currentLine.append(c);
                } else {
                    // 超宽时结束当前行
                    lines.add(currentLine.toString());
                    currentLine.setLength(0);
                    currentLine.append(c);
                }
            } else {
                currentLine.append(c);
            }
        }
        return lines;
    }

    public void nextChar() {
        if(currentChar < line.get(currentLine).length()) {
            currentChar++;
        }
        else {
            currentChar = 0;
            if(currentLine < line.size()-1) {
                currentLine++;
            }
        }
    }
    public void complete() {
        currentLine = line.size()-1;
        currentChar = line.get(currentLine).length();
    }



    @Override
    public void render(Gui gui,int mousex, int mousey, float partialTicks) {
        String gradualline = line.get(currentLine).substring(0, currentChar);
        for (int l = 0; l < currentLine; l++) {
            String text = line.get(l);
            gui.drawString(font, text, x, y + l * 10, 16777215);
        }
        gui.drawString(font, gradualline,x,y + currentLine*10,16777215);

        if(currentLine == line.size()-1 && currentChar == line.get(currentLine).length()) {
            doCompletePage = true;
        }
    }

    @Override
    public void tick() {
        nextChar();
    }
}
