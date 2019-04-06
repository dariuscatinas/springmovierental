package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ui.Console;

@Configuration
public class UiConfig {

    @Bean
    Console console(){
        return new Console();
    }
}
