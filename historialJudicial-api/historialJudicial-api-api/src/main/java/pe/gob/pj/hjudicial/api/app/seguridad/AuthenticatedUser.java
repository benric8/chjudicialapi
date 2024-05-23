package pe.gob.pj.hjudicial.api.app.seguridad;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import pe.gob.pj.hjudicial.dao.dto.seguridad.Role;
import pe.gob.pj.hjudicial.dao.dto.seguridad.User;

import lombok.Getter;
import lombok.Setter;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private User user;

	public AuthenticatedUser(User user) {
		super(user.getEmail(), user.getPassword(), getAuthorities(user));
		this.user = user;
	}
	
	/**
	* Descripción : Permite obtener los permisos del usuario
	* @param User user - datos del usuario 
	* @return Collection<GrantedAuthority> - lista de autorizaciones 
	* @exception Captura excepcion generica
	*/
	private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
		Set<String> roleAndPermissions = new HashSet<>();
		List<Role> roles = user.getRoles();
		for (Role role : roles) {
			roleAndPermissions.add(role.getName());
		}
		String[] roleNames = new String[roleAndPermissions.size()];
		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(roleAndPermissions.toArray(roleNames));
		return authorities;
	}
}
