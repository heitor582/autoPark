package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private final UserDAO userDAO;

    public UserService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(final String username, final String password) {
        return userDAO.login(username, password);
    }

    public boolean create(final String username, final String password, final boolean isAdmin){
        final User user  = User.create(username, password, isAdmin);
        return userDAO.insert(user);
    }
}
