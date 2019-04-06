import config.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainServer {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(ServerConfig.class);
        context.refresh();
        System.out.println("server start...");

    }
}
