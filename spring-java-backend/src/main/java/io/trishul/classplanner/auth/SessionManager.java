package io.trishul.classplanner.auth;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
  private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();

  public void startSession(Long userId) {
    currentUser.set(userId);
  }

  public Long getCurrentUserId() {
    Long userId = currentUser.get();
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
