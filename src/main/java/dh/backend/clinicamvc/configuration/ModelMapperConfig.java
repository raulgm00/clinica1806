package dh.backend.clinicamvc.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la clase que define un bean de ModelMapper para la conversión y mapeo entre objetos.
 * ModelMapper es una biblioteca útil para mapear automáticamente propiedades entre dos objetos.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Define un bean de ModelMapper que puede ser inyectado de manera automática en otras partes de la aplicación.
     *
     * @return una instancia de {@link ModelMapper}.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}