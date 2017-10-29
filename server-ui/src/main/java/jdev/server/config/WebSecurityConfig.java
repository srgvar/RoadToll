package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import jdev.server.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    WebSecurityConfig(@Autowired UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }

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
                .antMatchers("/admin").hasAnyRole("MANAGER","ROOT")
                .antMatchers("/admin/registerClient/**").hasAnyRole("MANAGER","ROOT")
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
