package com.dange.tanmay.security;

import com.dange.tanmay.dao.User;
import com.dange.tanmay.service.GoogleAuthenticatorService;
import com.dange.tanmay.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
    public class CustomAuthenticationProvider implements AuthenticationProvider {

    public static final String ROLE = "ROLE_";

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String verificationCode
                = ((CustomWebAuthenticationDetails) auth.getDetails())
                .getVerificationCode();


        //Fetching user from Database
        User user = userService.getUserByUsername(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!user.getPassword().equals(auth.getCredentials())){
            throw new BadCredentialsException("Invalid username or password");
        }

        if (user.getMfaEnabled() != null && user.getMfaEnabled()) {
            //First check if user provided a verification code or not and if provided, is that a valid int number
            if(!StringUtils.isNumeric(verificationCode)) {
                throw new BadCredentialsException("Invalid verification code");
            }

            if (!googleAuthenticatorService.validate(auth.getName(), Integer.parseInt(verificationCode))){
                throw new BadCredentialsException("Invalid verification code");
            }
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role: user.getRole().split(",")) {
            authorities.add(new SimpleGrantedAuthority(ROLE +role));
        }

        return new UsernamePasswordAuthenticationToken(
                user, auth.getCredentials(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
