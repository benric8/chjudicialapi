package pe.gob.pj.hjudicial.service.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.pj.hjudicial.dao.dto.seguridad.MovUsuario;
import pe.gob.pj.hjudicial.dao.dto.seguridad.Role;
import pe.gob.pj.hjudicial.dao.dto.seguridad.User;
import pe.gob.pj.hjudicial.dao.entity.seguridad.MaeRol;
import pe.gob.pj.hjudicial.dao.utils.UtilsProject;
import pe.gob.pj.hjudicial.dao.repository.SeguridadServicioDao;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SeguridadServicioDao seguridadServicioDao;	

	@Override
    @Transactional(transactionManager = "txManagerSeguridad", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("******** REGISTRANDO ACCESOS EN SPRING SECURITY DINAMICAMENTE ********");
		User user=new User();		
		try {
			MovUsuario u=seguridadServicioDao.recuperaInfoUsuario(username, "");
			if(u != null && u.getId() > 0) {
				user.setId(u.getId());
				user.setName(u.getcUsuario());
				user.setPassword(passwordEncoder.encode(u.getcClave()));
				List<Role> roles= new ArrayList<Role>();
				List<MaeRol> rolesB = seguridadServicioDao.recuperarRoles(username, "");
				if(rolesB != null) {
					int i = 1;
					for (MaeRol r : rolesB) {
						Role rol= new Role();
						rol.setId(i);
						rol.setName(r.getCRol());
						roles.add(rol);
						i++;
					}
				}
				user.setRoles(roles);
			} else {
				throw new Exception("Usuario con ID:  " + username + " not found");
			}				
		} catch (Exception e) {
			logger.debug("ERROR AL RECUPERAR USUARIO Y ROLES PARA SPRING SECURITY: " + UtilsProject.convertExceptionToString(e));			
			e.printStackTrace();
			new UsernameNotFoundException("Usuario con ID:  " + username + " not found");
		}	
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), getAuthorities(user));
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
}