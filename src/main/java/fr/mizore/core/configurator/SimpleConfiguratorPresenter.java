package fr.mizore.core.configurator;

public abstract class SimpleConfiguratorPresenter<T extends IsComponent> extends Presenter<T> {

    public SimpleConfiguratorPresenter(T display) {
        super(display);
    }

    @Override
    public void onBind() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUnBind() {
        // TODO Auto-generated method stub

    }

    public abstract void configure();
}
