package fr.mizore.core.configurator.executor.view;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.google.inject.Singleton;

import fr.mizore.core.configurator.configurator.interfaces.ConfiguratorDisplay;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;

@Singleton
public class ExecutorViewImpl extends JPanel implements ExecutorDisplay {

    private static final long serialVersionUID = -1277481901018736538L;

    private JButton configureButton;
    private JButton importWarFileButton;
    private JPanel configuratorPanel;

    private JButton logsButton;

    public ExecutorViewImpl() {
        super.setLayout(new MigLayout("fillx", "[right]rel[grow,fill]", "[]10[]"));

        // Configurator
        this.configuratorPanel = new JPanel();
        this.configuratorPanel.setLayout(new MigLayout());
        super.add(configuratorPanel, "height 100%, width 100%");

        // generation parameters
        JPanel configurationActionsPanel = new JPanel();
        configurationActionsPanel.setLayout(new MigLayout());

        JPanel executorConfigurationButtonsPanel = new JPanel();
        executorConfigurationButtonsPanel.setLayout(new MigLayout());
        executorConfigurationButtonsPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));

        this.importWarFileButton = new JButton();
        this.configureButton = new JButton("Configure");
        this.logsButton = new JButton("Show logs");

        executorConfigurationButtonsPanel.add(importWarFileButton, "width 100%, newline");
        executorConfigurationButtonsPanel.add(configureButton, "width 100%, newline");
        executorConfigurationButtonsPanel.add(logsButton, "width 100%, newline");

        configurationActionsPanel.add(executorConfigurationButtonsPanel, "height 100%, width 100%");
        super.add(configurationActionsPanel, "dock east");
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void shows() {
        super.setVisible(true);
    }

    @Override
    public void addConfigureAction(ActionListener action) {
        this.configureButton.addActionListener(action);
    }

    @Override
    public void addShowLogAction(ActionListener action) {
        this.logsButton.addActionListener(action);
    }

    @Override
    public void addImportFileChangedAction(ActionListener action) {
        this.importWarFileButton.addActionListener(action);
    }

    @Override
    public void setImportProject(String text, String title) {
        this.importWarFileButton.setText(text);
        this.importWarFileButton.setToolTipText(title);
    }

    @Override
    public void setConfigurator(ConfiguratorDisplay display) {
        configuratorPanel.removeAll();
        configuratorPanel.add(display.asComponent(), "height 100%, width 100%, wrap");
    }

}
