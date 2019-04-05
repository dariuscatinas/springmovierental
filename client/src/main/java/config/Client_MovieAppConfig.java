package config;

import common.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.Client_MovieService;

public class Client_MovieAppConfig {

    @Bean
    Client_MovieService clientServiceMovie() {
        return new Client_MovieService();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(MovieService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/MovieService");
        return rmiProxyFactoryBean;
    }

}
