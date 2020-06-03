package by.shurik.preproject.task33.RESTful.service.serviceImpl;

import by.shurik.preproject.task33.RESTful.dao.RoleDao;
import by.shurik.preproject.task33.RESTful.model.Role;
import by.shurik.preproject.task33.RESTful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }
}
