import com.google.common.util.concurrent.MoreExecutors;
import registry.IObserverRegistry;
import registry.ObserverAction;
import registry.ObserverRegistry;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Synchronous event bus
 */
public class SimpleEventBus {
    private Executor executor;
    private IObserverRegistry registry = new ObserverRegistry();

    public SimpleEventBus() {
        /*
          MoreExecutors.directExecutor() is provided by Google Guava in single thread
         */
        this(MoreExecutors.directExecutor());
    }

    protected SimpleEventBus(Executor executor) {
        this.executor = executor;
    }

    /**
     * Register an observer
     *
     * @param object observer
     */
    public void register(Object object) {
        registry.register(object);
    }

    /**
     * Unregister an observer
     *
     * @param object observer
     */
    public void unregister(Object object) {
        registry.unregister(object);
    }

    /**
     * Publish an event
     *
     * @param event argument
     */
    public void post(Object event) {
        List<ObserverAction> observerActions = registry.getMatchedObserverActions(event);
        for (ObserverAction observerAction : observerActions) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observerAction.execute(event);
                }
            });
        }
    }

}
