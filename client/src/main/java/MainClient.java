import config.Client_ClientAppConfig;
import config.Client_MovieAppConfig;
import config.Client_RentalAppConfig;
import config.UiConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.Client_ClientService;
import service.Client_MovieService;
import service.Client_RentalService;
import ui.Console;

public class MainClient {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("config");
//        context.register(Client_ClientAppConfig.class);
//        context.register(Client_MovieAppConfig.class);
//        context.register(Client_RentalAppConfig.class);
//        context.register(UiConfig.class);
//        context.refresh();
        Client_ClientService client_clientService= context.getBean(Client_ClientService.class);
        Client_MovieService client_movieService=context.getBean(Client_MovieService.class);
        Client_RentalService client_rentalService=context.getBean(Client_RentalService.class);
        Console console=context.getBean(Console.class);
        console.setSerivces(client_movieService,client_clientService,client_rentalService);
        console.run();
    }
}
