package fr.mizore.core.configurator.configurator.view;

import java.awt.Color;

public class ComboColored {
    private Color color = Color.WHITE;
    private String text;

    public ComboColored(String text) {
        this.setText(text);
    }

    public ComboColored(String text, Color color) {
        this.setText(text);
        this.setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
