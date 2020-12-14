package edu.phystech.lab;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserAuthorization {
    private static Map<String, String> users = new ConcurrentHashMap<String, String>();

    public static void addUser(String username, String password) {
        users.put(username, password);
    }

    public static boolean isPasswordCorrect(String username, String password) {
        return users.get(username).equals(password);
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }
}
