package fr.mizore.core.configurator.executor.interfaces;

import java.awt.event.ActionListener;

import fr.mizore.core.configurator.IsComponent;
import fr.mizore.core.configurator.configurator.interfaces.ConfiguratorDisplay;

public interface ExecutorDisplay extends IsComponent {
    void shows();

    void addConfigureAction(ActionListener action);

    void addImportFileChangedAction(ActionListener action);

    void setImportProject(String string, String title);

    void setConfigurator(ConfiguratorDisplay display);

    void addShowLogAction(ActionListener action);

}
