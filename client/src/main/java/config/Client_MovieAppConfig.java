package config;

import common.service.MovieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.Client_MovieService;

@Configuration
public class Client_MovieAppConfig {

    @Bean
    Client_MovieService clientServiceMovie() {
//        System.out.println("creat movie service");
        return new Client_MovieService();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanMovie() {
//        System.out.println("intrat aici??");
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(MovieService.class);
        rmiProxyFactoryBean
                .setServiceUrl("rmi://localhost:1099/MovieService");
//        System.out.println("???");
        return rmiProxyFactoryBean;
    }

}
