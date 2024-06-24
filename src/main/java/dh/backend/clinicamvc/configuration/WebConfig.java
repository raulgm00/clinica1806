package dh.backend.clinicamvc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* Esta clase sirve para quitar la politica de CORS que comunica el backend con el frontend
* Sirve solo si el Backend y el Frontend viven en lugares separados
* En caso de que vivan en el mismo server , esta configuracion se documenta para el buen funcionamiento de la app
* */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500/")       // Server de Frontend
                .allowedMethods("GET", "POST", "PUT","DELETE")  // Operaciones permitidas
                .allowedHeaders("*")                            // permite todos los headers
                .allowCredentials(true);                        // Permite credeciales
    }
}
