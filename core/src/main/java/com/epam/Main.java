package com.epam;

import com.epam.dao.api.UserDao;
import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;

/**
 * Created by infinity on 22.02.16.
 */
public class Main {

    public static void main(String[] args) {
        User user = new User();
        user.setFirstName("Olll");
        user.setLastName("34343");
        user.setEmail("3434@dffdf.cvom");
        user.setPassword("sdsfsdfs");
        user.setLibrarian(false);
        UserDao userDao = new UserDaoImpl();
        userDao.create(user);
    }
}
