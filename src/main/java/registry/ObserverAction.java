package registry;

import com.google.common.base.Preconditions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ObserverAction describes the @Subscribe annotation,
 * where target is the Observer, method is the Observer's method
 * Mainly used in {@link ObserverRegistry}
 */
public class ObserverAction {
    private Object target;
    private Method method;
    public ObserverAction(Object target, Method method) {
        this.target = Preconditions.checkNotNull(target);
        this.method = method;
        this.method.setAccessible(true);
    }
    public void execute(Object arg) {
        try {
            method.invoke(target, arg); // the target's (observer's) method will be triggered with arguments
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
