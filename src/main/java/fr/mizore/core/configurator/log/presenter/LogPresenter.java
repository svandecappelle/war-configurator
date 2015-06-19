package fr.mizore.core.configurator.log.presenter;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.mizore.core.configurator.Presenter;
import fr.mizore.core.configurator.log.interfaces.LogDisplay;

@Singleton
public class LogPresenter extends Presenter<LogDisplay> {

    private Handler handlerLogger;
    protected static final Logger logger = Logger.getLogger("Executor");

    @Inject
    public LogPresenter(LogDisplay display) {
        super(display);
    }

    @Override
    public void onBind() {
        this.handlerLogger = new Handler() {

            @Override
            public void publish(LogRecord arg0) {
                LogPresenter.this.display.addLog(arg0.getMessage());
            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };

        logger.addHandler(this.handlerLogger);
    }

    @Override
    public void onUnBind() {
        // TODO Auto-generated method stub
        logger.removeHandler(this.handlerLogger);
    }

    @Override
    public void onRevealDisplay() {
        // TODO Auto-generated method stub
        display.shows();
    }

}
