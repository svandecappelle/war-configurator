package fr.mizore.core.configurator.app.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import net.miginfocom.swing.MigLayout;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.mizore.core.configurator.ApplicationGlobalContainer;
import fr.mizore.core.configurator.InfiniteProgressPanel;
import fr.mizore.core.configurator.app.interfaces.ApplicationDisplay;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;

@Singleton
public class ApplicationDisplayImpl extends JFrame implements ApplicationDisplay {

    @Inject
    private ApplicationGlobalContainer globalContainer;

    @Inject
    private JLayeredPane layeredPane;

    private InfiniteProgressPanel loadingPanel;

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public ApplicationDisplayImpl() {
        super("Configurator");
        super.setLayout(new MigLayout("fillx"));
    }

    public void setDefaultCloseConfiguration(int configuration) {
        super.setDefaultCloseOperation(configuration);
    }

    @Override
    public void shows() {
        globalContainer.shows();
        this.setJMenuBar(globalContainer.getMenu());
        super.setContentPane(layeredPane);
        this.layeredPane.setLayout(new MigLayout("fillx"));
        super.setPreferredSize(new Dimension(800, 600));

        this.loadingPanel = new InfiniteProgressPanel("Waiting configuration...");
        this.layeredPane.add(loadingPanel, "pos 0px 0px 100% 100%");

        this.layeredPane.add(globalContainer.asComponent(), "height 100%, width 100%");

        super.pack();
        super.setVisible(true);
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void repack() {
        super.pack();
        super.setVisible(true);
    }

    @Override
    public void setConfigurator(ExecutorDisplay display) {
        this.globalContainer.setGenerator(display);
        super.pack();
        super.setVisible(true);
    }

    @Override
    public void addExitActionListener(ActionListener actionHandler) {
        this.globalContainer.addExitActionListener(actionHandler);
    }

    @Override
    public void setLoading(boolean isLoading) {
        this.loadingPanel.setVisible(isLoading);
        if (isLoading) {
            this.loadingPanel.start();
        } else {
            this.loadingPanel.stop();
        }
        repack();
    }
}
