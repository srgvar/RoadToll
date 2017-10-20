package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import jdev.server.services.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@EnableWebSecurity
@ComponentScan({"jdev.server.services"})
@EnableAutoConfiguration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/res/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // Описание полномочий доступа к ресурсам

                .authorizeRequests()
                .antMatchers("/res/**").permitAll()  // ресурсы css, img, ...
                .antMatchers("/home","/error").authenticated() // домашняя страница
                .antMatchers("/payments/**", "/routes/**").hasRole("CLIENT")
                .antMatchers("/admin").hasRole("MANAGER")
                .antMatchers("/admin/registerClient/**").hasRole("MANAGER")
                .antMatchers("/admin/registerManager/**").hasRole("ROOT")
                .anyRequest().authenticated()
                .and()
             .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
             .logout()
                .permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

                /*// определение ролей для реализации полномочий доступа
                .inMemoryAuthentication()
                .withUser("client").password("client").roles("CLIENT")
                .and()
                .withUser("manager").password("manager").roles("MANAGER", "CLIENT")
                .and()
                .withUser("root").password("root").roles("ROOT", "MANAGER", "CLIENT");*/
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
