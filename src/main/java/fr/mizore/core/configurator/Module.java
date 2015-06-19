package fr.mizore.core.configurator;

import com.google.inject.AbstractModule;

import fr.mizore.core.configurator.app.interfaces.ApplicationDisplay;
import fr.mizore.core.configurator.app.view.ApplicationDisplayImpl;
import fr.mizore.core.configurator.configurator.interfaces.ConfiguratorDisplay;
import fr.mizore.core.configurator.configurator.view.ConfiguratorViewImpl;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;
import fr.mizore.core.configurator.executor.view.ExecutorViewImpl;
import fr.mizore.core.configurator.log.interfaces.LogDisplay;
import fr.mizore.core.configurator.log.view.LogViewImpl;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(ApplicationDisplay.class).to(ApplicationDisplayImpl.class);
        bind(ConfiguratorDisplay.class).to(ConfiguratorViewImpl.class);
        bind(ExecutorDisplay.class).to(ExecutorViewImpl.class);
        bind(LogDisplay.class).to(LogViewImpl.class);
    }

}
