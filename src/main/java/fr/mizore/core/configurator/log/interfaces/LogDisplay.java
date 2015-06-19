package fr.mizore.core.configurator.log.interfaces;

import fr.mizore.core.configurator.IsComponent;

public interface LogDisplay extends IsComponent {

    void shows();

    void addLog(String message);

}
