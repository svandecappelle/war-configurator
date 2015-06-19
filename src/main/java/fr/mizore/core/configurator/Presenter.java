package fr.mizore.core.configurator;

public abstract class Presenter<T extends IsComponent> {

    protected T display;
    private boolean isBound = false;

    public T getDisplay() {
        return display;
    }

    public Presenter(T display) {
        this.display = display;
    }

    public void bind() {
        if (!isBound) {
            isBound = true;
            this.onBind();
        }
    }

    public void unbind() {
        if (isBound) {
            isBound = false;
            this.onUnBind();
        }
    }

    public void revealDisplay() {
        this.onRevealDisplay();
    }

    public abstract void onBind();

    public abstract void onUnBind();

    public abstract void onRevealDisplay();
}
