package fr.mizore.core.configurator.app.interfaces;

import java.awt.event.ActionListener;

import fr.mizore.core.configurator.IsComponent;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;

public interface ApplicationDisplay extends IsComponent {

    void setDefaultCloseConfiguration(int configuration);

    void shows();

    void repack();

    void setConfigurator(ExecutorDisplay display);

    void addExitActionListener(ActionListener actionHandler);

    void setLoading(boolean isLoading);
}
