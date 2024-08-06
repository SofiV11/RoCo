package com.RoCo.configurations;


import com.RoCo.entities.Account.UserRole;
import com.RoCo.services.AccountServ.UserService;
import jakarta.persistence.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.authenticationProvider(authenticationProvider());
//    }

    @Basic
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Override
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
            http.authorizeHttpRequests(authorize ->
                            authorize
                                    .requestMatchers(mvcMatcherBuilder.pattern("/Blog/addPost")).hasAuthority(UserRole.ADMIN.name())
                                    //.requestMatchers("/Blog/addPost").hasAuthority(UserRole.ADMIN.name())
                                    .requestMatchers(mvcMatcherBuilder.pattern("/Catalog/add")).hasAuthority(UserRole.ADMIN.name())
                                    .requestMatchers(mvcMatcherBuilder.pattern("/MyAccount")).hasAnyAuthority(UserRole.CLIENT.name(), UserRole.ADMIN.name())
                                    .requestMatchers(mvcMatcherBuilder.pattern("/Account/edit")).hasAnyAuthority(UserRole.CLIENT.name(), UserRole.ADMIN.name())
                                    .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).hasAnyAuthority(UserRole.CLIENT.name(), UserRole.ADMIN.name())
//                                    .requestMatchers("/").permitAll()
//                                    .requestMatchers("/login").permitAll()
                                    .anyRequest().permitAll()
                    )
                    .formLogin(form ->
                            form
                                    .loginPage("/Account")
                                    .loginProcessingUrl("/Account")
                                    //.failureUrl("/Account-error")
                                    .defaultSuccessUrl("/Account")
                                    .permitAll()
                    )
                    .logout(logout ->
                            logout
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .logoutSuccessUrl("/Account")
                                    .deleteCookies("JSESSIONID")
                                    .invalidateHttpSession(true)


                    );

        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080")); // Замените "http://localhost:4200" на ваш frontend-домен
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
