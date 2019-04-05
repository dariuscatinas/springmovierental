import config.Server_ClientAppConfig;
import config.Server_MovieAppConfig;
import config.Server_RentalAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainServer {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(Server_ClientAppConfig.class);
        context.register(Server_MovieAppConfig.class);
        context.register(Server_RentalAppConfig.class);
        context.refresh();
        System.out.println("server start...");

    }
}
