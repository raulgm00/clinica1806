package dh.backend.clinicamvc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para manejar Cross-Origin Resource Sharing (CORS) en la aplicación.
 * Permite controlar qué orígenes pueden comunicarse con el backend.
 * Es especialmente útil cuando el frontend y el backend residen en servidores separados.
 *
 * Se debe modificar adecuadamente dependiendo del entorno de despliegue.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura los ajustes de CORS para el entorno de desarrollo local.
     * Permite solicitudes desde el origen especificado y con métodos HTTP específicos.
     *
     * @param registry El registro de CORS utilizado para aplicar las configuraciones de CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500/")       // Origen permitido (frontend local)
                //.allowedOrigins("*")                          // Permite peticiones de cualquier origen (no recomendado en producción)
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos HTTP permitidos
                .allowedHeaders("*")                            // Permite todos los encabezados
                .allowCredentials(true);                        // Permite el uso de credenciales en solicitudes
    }

    /*
     * Configuración para despliegue en Railway (u otro entorno de nube)
     * Descomentar y ajustar según el entorno de producción.
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")                           // Permite peticiones de cualquier origen
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
//                .allowedHeaders("*")                            // Permite todos los encabezados
//                .allowCredentials(true);                       // Permite el uso de credenciales en solicitudes
//    }
}