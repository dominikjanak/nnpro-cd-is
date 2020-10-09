package cz.upce.nnpro.cd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ) {
        registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
        registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
    }

}