package config;

import common.service.RentalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.Client_RentalService;

@Configuration
public class Client_RentalAppConfig {

    @Bean
    Client_RentalService clientServiceRental(){
        return new Client_RentalService();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanRental(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(RentalService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/RentalService");
        return rmiProxyFactoryBean;
    }
}
