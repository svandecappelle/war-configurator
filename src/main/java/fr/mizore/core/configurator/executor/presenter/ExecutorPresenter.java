package fr.mizore.core.configurator.executor.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.mizore.core.configurator.Presenter;
import fr.mizore.core.configurator.configurator.presenter.ConfiguratorPresenter;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;
import fr.mizore.core.configurator.log.presenter.LogPresenter;

@Singleton
public class ExecutorPresenter extends Presenter<ExecutorDisplay> {

    protected static final String DEFAULT_LOCATION_TEXT = "<Import WAR File>";
    protected static final Logger logger = Logger.getLogger("Executor");

    protected String warFile;

    @Inject
    private ConfiguratorPresenter configurator;

    @Inject
    private LogPresenter loggerPresenter;

    @Inject
    public ExecutorPresenter(ExecutorDisplay display) {
        super(display);
    }

    @Override
    public void onBind() {
        configurator.bind();
        loggerPresenter.bind();

        this.display.addImportFileChangedAction(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setMultiSelectionEnabled(false);
                FileNameExtensionFilter wars = new FileNameExtensionFilter("Distribuable war product", "war");
                chooser.addChoosableFileFilter(wars);
                chooser.setFileFilter(wars);

                int rVal = chooser.showSaveDialog(display.asComponent());
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    display.setImportProject("... " + chooser.getSelectedFile().getName().toString(), chooser.getSelectedFile().toString());
                    warFile = chooser.getSelectedFile().toString() + "/";

                    configurator.init(chooser.getSelectedFile());
                    configurator.revealDisplay();
                    display.setConfigurator(configurator.getDisplay());

                } else if (rVal == JFileChooser.CANCEL_OPTION) {
                    display.setImportProject(DEFAULT_LOCATION_TEXT, null);
                    warFile = null;
                }
            }
        });

        this.display.addConfigureAction(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (warFile != null) {
                    logger.info("Start configuration : " + warFile);
                    configurator.configure();
                } else {
                    logger.warning("Configuration failed: due to unselected project war.");
                }
            }
        });

        this.display.addShowLogAction(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loggerPresenter.revealDisplay();
            }
        });

    }

    @Override
    public void onUnBind() {
        // Nothing to do
    }

    @Override
    public void onRevealDisplay() {
        display.setImportProject(DEFAULT_LOCATION_TEXT, null);
        this.display.shows();
    }

}
