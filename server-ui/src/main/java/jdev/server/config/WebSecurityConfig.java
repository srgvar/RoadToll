package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
        auth     // определение ролей для реализации полномочий доступа
                .inMemoryAuthentication()
                .withUser("client").password("client").roles("CLIENT")
                .and()
                .withUser("manager").password("manager").roles("MANAGER", "CLIENT")
                .and()
                .withUser("root").password("root").roles("ROOT", "MANAGER", "CLIENT");

    }

}
