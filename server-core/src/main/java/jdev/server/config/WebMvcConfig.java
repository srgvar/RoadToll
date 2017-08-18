package jdev.server.config;

/*
 * Created by srgva on 13.08.2017.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry vcRegistry) {
        vcRegistry.addViewController("/login").setViewName("login");
        vcRegistry.addViewController("/home").setViewName("home");
        vcRegistry.addViewController("/").setViewName("home");
        vcRegistry.addViewController("/routes").setViewName("routes");
        vcRegistry.addViewController("/error").setViewName("error");
        vcRegistry.addViewController("/payments").setViewName("payments");
        vcRegistry.addViewController("/registerClient").setViewName("registerClient");
        vcRegistry.addViewController("/registerManager").setViewName("registerManager");
    }

}
