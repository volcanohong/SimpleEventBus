import annotation.Subscribe;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UsageExample {

    @Test
    public void test() throws Exception {
        UserRegistrationService service = new UserRegistrationService();
        service.register("Amy", "any");
    }
}

class UserRegistrationService {

    private static final int DEFAULT_EVENTBUS_THREAD_POOL_SIZE = 20;

    private final List<IRegistrationObserver> observers = Arrays.asList(new RegPromotionObserver(),
            new RegNotificationObserver());

    private SimpleEventBus eventBus;

    public UserRegistrationService() {
        eventBus = new SimpleEventBus(); // Sync events
        //Async events
//        eventBus = new AsyncSimpleEventBus(Executors.newFixedThreadPool(DEFAULT_EVENTBUS_THREAD_POOL_SIZE));
        setRegObservers(observers);
    }

    public void setRegObservers(List<IRegistrationObserver> observers) {
        for (Object observer : observers) {
            eventBus.register(observer);
        }
    }

    public Long register(String username, String password) {
        long userId = this.register(new User(username, password));
        eventBus.post(userId);
        return userId;
    }

    private Long register(User user) {
        System.out.println("Registered user: " + user.username);
        user.userId = 1000L;
        return user.userId;
    }
}

class User {
    Long userId;
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

interface IRegistrationObserver {
    void handleRegSuccess(Long userid);
}

class RegPromotionObserver implements IRegistrationObserver {
    @Subscribe
    @Override
    public void handleRegSuccess(Long userId) {
        System.out.println("Add promotion to user: " + userId);
    }
}

class RegNotificationObserver implements IRegistrationObserver {
    @Subscribe
    @Override
    public void handleRegSuccess(Long userId) {
        System.out.println("Send notification to user: " + userId);
    }
}
