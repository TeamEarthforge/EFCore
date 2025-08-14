package com.earthforge.efcore.feature.dialog.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import noppes.npcs.NoppesStringUtils;
import org.lwjgl.opengl.GL11;


import java.util.ArrayList;
import java.util.List;

public class DialogText implements IComponent{
    private List<String> line = new ArrayList();
    private int currentLine = 0;
    private int currentChar = 0;
    private int x;
    private int y;
    private float scale;


    public boolean doCompletePage = false;
    FontRenderer font = Minecraft.getMinecraft().fontRenderer;



    public DialogText(String text, int x, int y, int width,int height,Object... obs) {
        scale = (float) height /36;
        this.line = splitString(text,(int)(width/scale),obs);
        this.x = x;
        this.y = y;

    }

    private List<String> splitString(String input, int lineLength, Object... obs) {
        String text = NoppesStringUtils.formatText(input, obs);
        List<String> lines = new ArrayList<>();

        // 按换行符分割段落
        String[] paragraphs = text.split("\n|\r");

        for (String para : paragraphs) {
            StringBuilder currentLine = new StringBuilder();
            StringBuilder activeFormats = new StringBuilder(); // 保存所有活跃的格式代码

            // 逐个字符处理
            for (int i = 0; i < para.length(); i++) {
                char c = para.charAt(i);
                String testLine = currentLine.toString() + c;

                // 检查是否是格式代码
                if (c == '§' && i + 1 < para.length()) {
                    char formatCode = para.charAt(i + 1);
                    activeFormats.append(c).append(formatCode); // 添加到活跃格式
                    currentLine.append(c).append(formatCode); // 立即应用到当前行
                    i++; // 跳过下一个字符
                    continue;
                }

                // 检查行宽
                if (font.getStringWidth(testLine) <= lineLength) {
                    currentLine.append(c);
                } else {
                    // 如果当前行不为空，先保存
                    if (currentLine.length() > 0) {
                        lines.add(currentLine.toString());
                    }
                    // 开始新行，并添加所有活跃格式代码
                    currentLine = new StringBuilder(activeFormats.toString() + c);
                }
            }

            // 添加最后一行
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
            }
        }

        return lines;
    }

    // 判断是否是中文标点



    public void nextChar() {
        if (line.isEmpty()) {
            return;
        }

        // 确保当前行有效
        if (currentLine >= line.size()) {
            currentLine = line.size() - 1;
        }

        String current = line.get(currentLine);

        // 移动到下一个字符
        currentChar++;

        // 检查是否需要换行
        if (currentChar > current.length()) {
            currentChar = 0;
            currentLine++;

            // 检查是否所有行都显示完毕
            if (currentLine >= line.size()) {
                currentLine = line.size() - 1;
                currentChar = current.length();
                doCompletePage = true;
                return;
            }
        }
    }
    public void complete() {
        if (line.isEmpty()) {
            return; // 空列表保护
        }
        currentLine = line.size() - 1;
        currentChar = line.get(currentLine).length(); // 确保currentLine有效
    }


    @Override
    public void render(Gui gui, int mousex, int mousey, float partialTicks) {
        if (line.isEmpty()) {
            return;
        }

        // 确保当前行有效
        if (currentLine >= line.size()) {
            currentLine = line.size() - 1;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(x,y, 0.0F);
        GL11.glScalef(scale, scale, 1.0F);
        // 渲染所有已完成的行
        for (int l = 0; l < currentLine; l++) {
            String text = line.get(l);
            font.drawString( text, 0,   l * 10, 0);
        }

        // 渲染当前行的部分文本
        if (currentLine < line.size()) {
            String current = line.get(currentLine);
            String gradualline = current.substring(0, Math.min(currentChar, current.length()));
            font.drawString(gradualline, 0, currentLine * 10, 0);
        }
        GL11.glPopMatrix();
        // 检查是否完成整个页面
        if (currentLine == line.size() - 1 && currentChar >= line.get(currentLine).length()) {
            doCompletePage = true;
        }

    }

    @Override
    public void tick() {
        nextChar();
    }
}
