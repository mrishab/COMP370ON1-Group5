package io.trishul.classplanner.auth;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private static final ThreadLocal<String> currentUser = new ThreadLocal<>();

    public void startSession(String userId) {
        currentUser.set(userId);
    }

    public String getCurrentUserId() {
        String userId = currentUser.get();
        if (userId == null) {
            throw new IllegalStateException("No user session found");
        }
        return userId;
    }

    public void endSession() {
        currentUser.remove();
    }

    public boolean hasActiveSession() {
        return currentUser.get() != null;
    }
}
