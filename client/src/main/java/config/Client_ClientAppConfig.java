package config;

import common.service.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.Client_ClientService;

public class Client_ClientAppConfig {

    @Bean
    Client_ClientService clientServiceClient() {
        return new Client_ClientService();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(ClientService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/ClientService");
        return rmiProxyFactoryBean;
    }
}
