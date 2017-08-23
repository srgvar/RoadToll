package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {
    /** Описание вида (html - страниц) сервера  */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("pages/home");
        registry.addViewController("/home").setViewName("pages/home");
        registry.addViewController("/login").setViewName("pages/login");
        registry.addViewController("/admin").setViewName("pages/admin");
        registry.addViewController("/routes").setViewName("pages/routes");
        registry.addViewController("/routes/**").setViewName("pages/routeN");
        registry.addViewController("/payments").setViewName("pages/payments");
        registry.addViewController("/payments/**").setViewName("pages/paymentN");
        registry.addViewController("/admin/registerClient").setViewName("pages/admin/regCli");
        registry.addViewController("/admin/registerClient/**").setViewName("pages/admin/regCliN");
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


}
