package se.solrike.cloud.serverless.dataload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.solrike.cloud.serverless.infomodel.Item;
import se.solrike.cloud.serverless.persistence.ItemRepository;

/**
 *
 */
@Configuration
public class LoadDatabase {

  private static final Logger sLogger = LoggerFactory.getLogger(LoadDatabase.class);

  // Spring Boot will run ALL CommandLineRunner beans once the application context is loaded.
  @Bean
  CommandLineRunner initDatabase(ItemRepository repository) {
    return args -> {
      sLogger.info("Preloading {}", repository.save(new Item("Bilbo Baggins")));
      sLogger.info("Preloading {}", repository.save(new Item("Frodo Baggins")));
    };
  }
}