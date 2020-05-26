package by.shurik.preproject.task33.RESTful.config;


import by.shurik.preproject.task33.RESTful.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private RoleService roleService;

    @Autowired
    public LoginSuccessHandler(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals((roleService.getRoleById(1L)).getName())) {
                httpServletResponse.sendRedirect("/admin/welcome");
                break;
            } else if (grantedAuthority.getAuthority().equals((roleService.getRoleById(2L)).getName())) {
                httpServletResponse.sendRedirect("/user");
                break;
            } else httpServletResponse.sendRedirect("/test");
        }
    }
}



