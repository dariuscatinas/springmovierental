package config;


import common.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;
import service.Server_ClientService;

public class Server_ClientAppConfig {

    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("ClientService");
        exporter.setServiceInterface(ClientService.class);
        exporter.setService(clientService());
        return exporter;
    }

    @Bean
    ClientService clientService() {
        return new Server_ClientService();
    }

}
