package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UsersDAO;
import web.model.User;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersDAO userDAO;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Autowired
    public UsersServiceImpl(UsersDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public List<User> readAllUsers() {
        return userDAO.readAllUsers();
    }

    @Override
    @Transactional
    public User readUser(int id) {
        return userDAO.readUser(id);
    }

    @Override
    @Transactional
    public User readUserByLogin(String login) {
        return userDAO.readUserByLogin(login);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!user.getPassword().equals(userDAO.readUser(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public void creatUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.creatUser(user);
    }
}
