package fr.mizore.core.configurator.app.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.mizore.core.configurator.Presenter;
import fr.mizore.core.configurator.app.interfaces.ApplicationDisplay;
import fr.mizore.core.configurator.executor.interfaces.ExecutorDisplay;
import fr.mizore.core.configurator.executor.presenter.ExecutorPresenter;

@Singleton
public class PresenterApplication extends Presenter<ApplicationDisplay> {

    @Inject
    private ExecutorPresenter executor;

    @Inject
    public PresenterApplication(ApplicationDisplay display) {
        super(display);
    }

    @Override
    public void onBind() {
        this.display.setDefaultCloseConfiguration(JFrame.EXIT_ON_CLOSE);

        this.display.addExitActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });

        this.executor.bind();
    }

    @Override
    public void onRevealDisplay() {
        this.executor.revealDisplay();
        this.display.setConfigurator((ExecutorDisplay) this.executor.getDisplay());
        this.display.shows();
    }

    public void loadingScreen(boolean isLoading) {
        display.setLoading(isLoading);
    }

    @Override
    public void onUnBind() {
        // TODO Auto-generated method stub

    }

    public void repack() {
        display.repack();
    }

}
