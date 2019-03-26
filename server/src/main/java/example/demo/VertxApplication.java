package example.demo;

import example.demo.verticle.ServerVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@Slf4j
@ComponentScan
@SpringBootApplication
public class VertxApplication {

    @Autowired
    private ServerVerticle serverVerticle;

	public static void main(String[] args) {
		SpringApplication.run(VertxApplication.class);
	}

	@PostConstruct
	public void deployServerVerticle() {
        Vertx.vertx().deployVerticle(serverVerticle, res -> {
            if (res.succeeded()) {
                System.out.println("Deployment succeed");
            } else {
                System.out.println("Deployment failed! " + res.cause());
            }
        });
	}
}
