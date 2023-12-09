package com.qualle.truegain.client;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;
import com.qualle.truegain.client.api.User;
import com.qualle.truegain.client.api.Workout;

import java.util.List;

public class InMemoryBackendClient {

    private static User user;

    static {
        user = new User();
        user.setEmail("antonsamoilo@gmail.com");

    }

    public static User getUser() {
        return user;
    }

}
