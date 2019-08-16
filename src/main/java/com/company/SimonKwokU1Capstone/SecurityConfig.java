package com.company.SimonKwokU1Capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource ds;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder enc = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username,authority from authorities where username = ?")
                .passwordEncoder(enc);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                //        Any staff member can update a product and CRUD on invoice
                //        CRUD on invoice
                .mvcMatchers(HttpMethod.GET,"/purchase" ).hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.GET,"/purchase/{id}" ).hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/purchase/{id}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.POST,"/purchase" ).hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/game/{id}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/console/{id}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/tshirt/{id}").hasAuthority("ROLE_STAFF")
                //        Only Managers and above can create new products and delete an invoice
                .mvcMatchers(HttpMethod.POST,"/game" ).hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST,"/console" ).hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST,"/tshirt" ).hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.DELETE,"/purchase/{id}").hasAuthority("ROLE_MANAGER")
                //        Only Admin users can delete a product.
                .mvcMatchers(HttpMethod.DELETE,"/game/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/console/{id}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/tshirt/{id}").hasAuthority("ROLE_ADMIN")
                //        Any user (both authenticated and unauthenticated) can search for products.
                .anyRequest().permitAll();

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/game")
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

}
