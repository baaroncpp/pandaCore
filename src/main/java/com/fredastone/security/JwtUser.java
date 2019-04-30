package com.fredastone.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by stephan on 20.03.16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Setter @Getter
	private String id;
	
	@Setter
    private  String username;
    @Setter
    private String password;
    
    @Setter @Getter
    private Date lastPasswordResetDate;
    @Setter
    private List<GrantedAuthority> authorities;
    
    @Setter
    private boolean isAccountActive;
    
    @Setter
    private boolean isAccountApproved;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.isAccountApproved;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		 return this.isAccountActive;
	}

}
