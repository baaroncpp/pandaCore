package com.fredastone.pandacore;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredastone.pandacore.constants.RoleName;
import com.fredastone.pandacore.entity.Role;
import com.fredastone.pandacore.entity.User;
import com.fredastone.pandacore.entity.UserRole;
import com.fredastone.security.JwtAuthenticationRequest;
import com.fredastone.security.JwtTokenUtil;
import com.fredastone.security.JwtUser;
import com.fredastone.security.JwtUserFactory;
import com.fredastone.security.service.JwtUserDetailsService;


//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class AuthenticationRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

     @MockBean
     private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @Before
    public void setup() {
    	
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @WithAnonymousUser
    public void successfulAuthenticationWithAnonymousUser() throws Exception {

        JwtAuthenticationRequest jwtAuthenticationRequest = new JwtAuthenticationRequest("user", "password");

        mvc.perform(post("/auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(jwtAuthenticationRequest)))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void successfulRefreshTokenWithUserRole() throws Exception {

    	Role authority = new Role();
        authority.setId((short)0);
       // authority.setName(RoleName.ROLE_USER);
        
        UserRole tUserRoles = new UserRole();
        //tUserRoles.setTRole(authority);
        
        List<UserRole> authorities = Arrays.asList(tUserRoles);

        User user = new User();
        user.setUsername("username");
        //user.setUserRoles(authorities);
        user.setIsactive(Boolean.TRUE);
        user.setPasswordreseton(new Date(System.currentTimeMillis() + 1000 * 1000));

        JwtUser jwtUser = JwtUserFactory.create(user);

        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(user.getUsername());

        when(jwtUserDetailsService.loadUserByUsername(eq(user.getUsername()))).thenReturn(jwtUser);

        when(jwtTokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);

        mvc.perform(get("/refresh")
            .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a.87780879798.77989898989"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void successfulRefreshTokenWithAdminRole() throws Exception {

       	Role authority = new Role();
        authority.setId((short)0);
        authority.setName(RoleName.ROLE_MANAGER);
        
        
        UserRole tUserRoles = new UserRole();
        //tUserRoles.setTRole(authority);
        
        List<UserRole> authorities = Arrays.asList(tUserRoles);

        User mockUser = new User();
        mockUser.setUsername("admin");
       // mockUser.setUserRoles(authorities);
        mockUser.setIsactive(Boolean.TRUE);
        mockUser.setPasswordreseton(new Date(System.currentTimeMillis() + 1000 * 1000));

        JwtUser jwtUser = JwtUserFactory.create(mockUser);

        when(jwtTokenUtil.getUsernameFromToken(any())).thenReturn(mockUser.getUsername());

        when(jwtUserDetailsService.loadUserByUsername(eq(mockUser.getUsername()))).thenReturn(jwtUser);

        when(jwtTokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);

        mvc.perform(get("/refresh")
            .header("Authorization", "Bearer 5d1103e-b3e1-4ae9-b606-46c9c1bc915a"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    public void shouldGetUnauthorizedWithAnonymousUser() throws Exception {

        mvc.perform(get("/refresh"))
            .andExpect(status().isUnauthorized());
    }
    
}

