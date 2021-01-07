import java.util.concurrent.Executor;

/**
 * Asynchronous event bus
 */
public class AsyncSimpleEventBus extends SimpleEventBus {
    public AsyncSimpleEventBus(Executor executor) {
        super(executor);
    }
}
