package by.shurik.preproject.task33.RESTful.dao.daoImpl;

import by.shurik.preproject.task33.RESTful.dao.RoleDao;
import by.shurik.preproject.task33.RESTful.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@Repository
public class RoleDaoImpl implements RoleDao {
    private Logger LOGGER = Logger.getLogger(RoleDaoImpl.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl() {
    }

    @Override
    public Role getRoleById(Long id) {
        System.out.println("RoleDaoImpl / EntityManager entityManager: " + entityManager);
        String hql = "FROM Role role WHERE role.id= :id";
        return (Role) entityManager.createQuery(hql).setParameter("id", id).getSingleResult();
    }
}

