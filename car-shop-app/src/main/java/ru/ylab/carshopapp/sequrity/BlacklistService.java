package ru.ylab.carshopapp.sequrity;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * This service is crucial for handling scenarios where users log out or when tokens need to be
 * invalidated before their natural expiration. By maintaining a blacklist, the application can
 * ensure that these tokens cannot be reused to access protected resources.
 */
@Component
public class BlacklistService {

    private final Set<String> blacklist = new HashSet<>();

    /**
     * Adds a token to the blacklist.
     *
     * @param token the JWT token to be blacklisted
     */
    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    /**
     * Checks if a token is blacklisted.
     *
     * @param token the JWT token to check
     * @return {@code true} if the token is blacklisted, {@code false} otherwise
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
