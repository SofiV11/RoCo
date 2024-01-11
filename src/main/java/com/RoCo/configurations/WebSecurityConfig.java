package com.RoCo.configurations;

import com.RoCo.entities.Account.SiteUser;
import com.RoCo.services.AccountServ.SiteUserServ;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@EnableWebSecurity(debug = true)
@Configuration
//@RequiredArgsConstructor
public class WebSecurityConfig {
//    @Autowired
//    SiteUserServ userServ;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password(bCryptPasswordEncoder().encode("user1"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//    @Autowired
//    MvcRequestMatcher.Builder mvc;

//    @Bean
//    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
//        return new HiddenHttpMethodFilter();
//    }

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
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
//    @Bean
//    WebSecurityCustomizer ignoringCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher("/static/**"));
//    }

    //Регистрация точек входа
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

        //httpSecurity.authorizeRequests().anyRequest().permitAll();

//        httpSecurity.authorizeHttpRequests(authorizedHttpRequests
//                        -> authorizedHttpRequests
//                        //.requestMatchers("/MyAccount/**").authenticated()
//                        .anyRequest().permitAll())
//                ;

        httpSecurity.httpBasic(httpBasic -> {})
                    .authorizeHttpRequests(authorizeHttpReq ->
                                  authorizeHttpReq
                                          //.requestMatchers(AntPathRequestMatcher.antMatcher("/static")).permitAll()
                                                    .anyRequest().permitAll()


                    );

//
//        httpSecurity.httpBasic(Customizer.withDefaults())
//                    .formLogin(form -> form.loginPage("/Account").permitAll());


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
