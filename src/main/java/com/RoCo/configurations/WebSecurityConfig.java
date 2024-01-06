package com.RoCo.configurations;

import com.RoCo.services.AccountServ.SiteUserServ;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig{
//    @Autowired
//    SiteUserServ userServ;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                //.csrf() Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии.
//                //.disable()
//                //.authorizeHttpRequests()
//                //Доступ только для не зарегистрированных пользователей
//                .Matchers("/registration").not().fullyAuthenticated()
//                //Доступ только для пользователей с ролью Администратор
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/news").hasRole("USER")
//                //Доступ разрешен всем пользователей
//                .antMatchers("/", "/resources/**").permitAll()
//                //Все остальные страницы требуют аутентификации
//                .anyRequest().authenticated()
//                .and()
//                //Настройка для входа в систему
//                .formLogin()
//                .loginPage("/login")
//                //Перенарпавление на главную страницу после успешного входа
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.csrf().disable();
        //Доступ только для не зарегистрированных пользователей
        //httpSecurity.authorizeRequests().antMatchers("/registration").not().fullyAuthenticated();
        //Доступ только для пользователей с ролью Администратор
        //httpSecurity.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        //Доступ только для пользователей с ролью Пользователь
        //httpSecurity.authorizeRequests().antMatchers("/profile").hasRole("USER");
        //Доступ разрешен всем подряд
        //httpSecurity.authorizeRequests().antMatchers("/", "/resources/**").permitAll();
        //Все остальные страницы требуют аутентификации
        httpSecurity.authorizeRequests().anyRequest().permitAll();
        //Настройка для входа в систему и перенарпавление на главную страницу после успешного входа
        //httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
        // Настройки для выхода из системы
        //httpSecurity.logout().permitAll().logoutSuccessUrl("/").deleteCookies("JSESSIONID");

        return httpSecurity.build();
    }

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userServ).passwordEncoder(bCryptPasswordEncoder());
//    }
}
