package config;

import org.springframework.context.annotation.Bean;
import ui.Console;

public class UiConfig {

    @Bean
    Console console(){
        return new Console();
    }
}
