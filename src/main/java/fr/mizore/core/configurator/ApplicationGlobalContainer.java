package fr.mizore.core.configurator;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ApplicationGlobalContainer {

    private JMenuItem exit;

    private JMenuBar menu;
    private JPanel componentContainer;

    public ApplicationGlobalContainer() {
        this.menu = new JMenuBar();
        JMenu file = new JMenu("File");
        this.exit = new JMenuItem();
        this.exit.setText("Exit");
        file.add(this.exit);
        this.menu.add(file);

        this.componentContainer = new JPanel();
        this.componentContainer.setLayout(new MigLayout());
    }

    public Component asComponent() {
        return this.componentContainer;
    }

    public JMenuBar getMenu() {
        return menu;
    }

    public void addExitActionListener(ActionListener actionHandler) {
        this.exit.addActionListener(actionHandler);
    }

    public void shows() {
        this.componentContainer.setVisible(true);
        // this.componentContainer.setSize(800, 600);
    }

    public void setGenerator(IsComponent display) {
        this.componentContainer.removeAll();
        this.componentContainer.add(display.asComponent(), "height 100%, width 100%");
    }
}