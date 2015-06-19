package fr.mizore.core.configurator;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.mizore.core.configurator.app.presenter.PresenterApplication;

public class Generator {

    private PresenterApplication applicationPresenter;

    public Generator() {
        Injector injector = Guice.createInjector(new Module());
        this.applicationPresenter = injector.getInstance(PresenterApplication.class);
    }

    private void start() {
        this.applicationPresenter.bind();
        this.applicationPresenter.revealDisplay();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Generator generator = new Generator();
        generator.start();
    }

}
