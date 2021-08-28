package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleByName(String role) {
        return (Role) entityManager.createQuery("from Role where role = :role")
                .setParameter("role", role).getSingleResult();
    }
}
