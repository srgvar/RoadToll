package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import jdev.dto.repo.PointsDbRepository;
import jdev.users.repo.RolesRepository;
import jdev.users.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories({"jdev.dto", "jdev.users"})
@ComponentScan({"jdev.server.services",
                "jdev.server.controllers"})

@EntityScan(basePackageClasses = {jdev.dto.PointDTO.class,
        jdev.users.User.class,
        jdev.users.UserRole.class})
class WebMvcConfig extends WebMvcConfigurerAdapter {
    /** Описание вида (html - страниц) сервера  */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("pages/home");
        //registry.addViewController("/home").setViewName("pages/home");
        registry.addViewController("/login").setViewName("pages/login");
        registry.addViewController("/admin").setViewName("pages/admin");
        registry.addViewController("/routes").setViewName("pages/routes");
        registry.addViewController("/routes/**").setViewName("pages/routeN");
        registry.addViewController("/payments").setViewName("pages/payments");
        registry.addViewController("/payments/**").setViewName("pages/paymentN");
        //registry.addViewController("/admin/registerClient").setViewName("pages/admin/regCli");
        //registry.addViewController("/admin/registerClient/**").setViewName("pages/admin/client");
        registry.addViewController("/admin/registerManager").setViewName("pages/admin/regMan");
        registry.addViewController("/admin/registerManager/**").setViewName("pages/admin/regManN");
        registry.addViewController("/error").setViewName("error");
    }
    /** ресурсы css, img, ... */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry rcRegistry) {
        ResourceHandlerRegistration resourceRegistration = rcRegistry
        .addResourceHandler("/res/**")
                .addResourceLocations("classpath:/templates/res/");
    }


    //@Autowired
    public PointsDbRepository pointsDbRepository;

    //@Autowired
    public RolesRepository rolesRepository;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
