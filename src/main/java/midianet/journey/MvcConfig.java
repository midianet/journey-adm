package midianet.journey;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/**").setViewName("index");
        //registry.addViewController("/login").setViewName("login");
        //registry.addViewController("/logout").setViewName("login");
//        registry.addViewController("/resposta-tipo").setViewName("index");
//        registry.addViewController("/resposta-tipo-form/**").setViewName("index");
//        registry.addViewController("/resposta").setViewName("index");
//        registry.addViewController("/resposta-form/**").setViewName("index");
//        registry.addViewController("/pergunta").setViewName("index");
//        registry.addViewController("/pergunta-form/**").setViewName("index");
//        registry.addViewController("/parametro").setViewName("index");
//        registry.addViewController("/parametro-form/**").setViewName("index");
    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/"};

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

}