package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersDAOImpl implements UsersDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> readAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User readUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User readUserByLogin(String login) {
        return (User) entityManager.createQuery("from User where email = :login")
                .setParameter("login", login).getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.createQuery("delete User where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public void creatUser(User user) {
        entityManager.persist(user);
    }
}
