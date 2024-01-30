package com.dange.tanmay.config;

import com.dange.tanmay.security.CustomWebAuthenticationDetailsSource;
import com.dange.tanmay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserService userService;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //for h2-console
        http.headers().frameOptions().disable();

        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/example/enableNewFeatures/**").hasRole("ADMIN")
                .antMatchers("/example/disableNewFeatures/**").hasRole("ADMIN")
                .antMatchers("/example/about").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/code/**").permitAll()
                .antMatchers("/h2-console/").permitAll()
                .and()
                .formLogin()
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(myAuthenticationSuccessHandler())
                .loginPage("/login");

        return http.build();

    }

    //Authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        List<UserDetails> listUsers = userService.getAllUsers().stream().map(x->x.castUserToUserDetails()).collect(Collectors.toList());
        return new InMemoryUserDetailsManager(listUsers);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}
