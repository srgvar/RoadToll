package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/home", "/error").authenticated()
                .antMatchers("/payments/**", "/routes/**").hasRole("CLIENT")
                .antMatchers("/registerClient").hasRole("MANAGER")
                .antMatchers("/registerManager").hasRole("ROOT")
                .anyRequest().hasRole("CLIENT")
                .and()
                .authorizeRequests()
                .antMatchers("/tracker", "/test").hasRole("TRACKER")
                .anyRequest().authenticated()
                .and().httpBasic()
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
        auth
                .inMemoryAuthentication()
                .withUser("client").password("client").roles("CLIENT")
                .and()
                .withUser("tracker").password("tracker").roles("TRACKER")
                .and()
                .withUser("manager").password("manager").roles("MANAGER", "CLIENT")
                .and()
                .withUser("root").password("root").roles("ROOT", "MANAGER", "CLIENT");

    }

}
