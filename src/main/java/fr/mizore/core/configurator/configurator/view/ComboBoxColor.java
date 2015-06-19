package fr.mizore.core.configurator.configurator.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxColor<T extends ComboColored> extends JComboBox<T> {

    private static final long serialVersionUID = -9196277133992340751L;

    public ComboBoxColor() {
        this.setRenderer(new CellColorRenderer());
    }

    private class CellColorRenderer extends JLabel implements ListCellRenderer<T> {
        private static final long serialVersionUID = 6119117349816248724L;

        public CellColorRenderer() {
            setOpaque(true);
        }

        public void setForeground(Color fg) {
        }

        public void setBackground(Color bg) {
        }

        public void trueSetForeground(Color fg) {
            super.setForeground(fg);
        }

        public void trueSetBackground(Color bg) {
            super.setBackground(bg);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value.getText() != null) {
                setText(value.getText());
            } else {
                setText("-");
            }
            if (index == -1) {
                ComboColored scolor = (ComboColored) getSelectedItem();
                trueSetBackground(scolor.getColor());
                // trueSetForeground(scolor.getColor());
                return this;

            }
            trueSetBackground(value.getColor());
            // trueSetForeground(value.getColor());
            return this;
        }
    }

}