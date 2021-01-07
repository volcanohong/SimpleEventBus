package registry;

import java.util.List;

public interface IObserverRegistry {
    void register(Object observer);

    void unregister(Object observer);

    List<ObserverAction> getMatchedObserverActions(Object arg);
}
