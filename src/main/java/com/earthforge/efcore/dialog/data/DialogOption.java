package com.earthforge.efcore.dialog.data;

public class DialogOption {
    private String text;
    private int level;
    public DialogOption(String text, int level) {
        this.text = text;
        this.level = level;
    }
    public String getText() {
        return text;
    }
    public int getLevel() {
        return level;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
