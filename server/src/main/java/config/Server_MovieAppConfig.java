package config;

import common.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import service.Server_MovieService;

public class Server_MovieAppConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("MovieService");
        exporter.setServiceInterface(MovieService.class);
        exporter.setService(movieService());
        return exporter;
    }

    @Bean
    MovieService movieService() {
        return new Server_MovieService();
    }
}
