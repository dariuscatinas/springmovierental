package config;

import common.service.RentalService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import service.Server_RentalService;

public class Server_RentalAppConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("RentalService");
        exporter.setServiceInterface(RentalService.class);
        exporter.setService(rentalService());
        return exporter;
    }

    @Bean
    RentalService rentalService() {
        return new Server_RentalService();
    }
}
