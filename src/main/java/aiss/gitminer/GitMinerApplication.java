package aiss.gitminer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "GitMiner API",
				version = "1.0",
				description = "Mining tool for Git project platforms"
		),
		servers = @Server(url = "http://localhost:8080")
)

@SpringBootApplication
public class GitMinerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitMinerApplication.class, args);
	}

}
