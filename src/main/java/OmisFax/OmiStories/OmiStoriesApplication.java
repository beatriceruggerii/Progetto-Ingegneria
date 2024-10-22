package OmisFax.OmiStories;

import OmisFax.OmiStories.Repositories.PartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OmiStoriesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OmiStoriesApplication.class, args);
    }
}
