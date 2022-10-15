package se.solrike.cloud.serverless.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import se.solrike.cloud.serverless.common.RestResponseEntityExceptionHandler;
import se.solrike.cloud.serverless.controller.ItemController;
import se.solrike.cloud.serverless.dataload.LoadDatabase;
import se.solrike.cloud.serverless.infomodel.Item;
import se.solrike.cloud.serverless.persistence.ItemRepository;

@SpringBootApplication
@EnableWebMvc

// See https://github.com/awslabs/aws-serverless-java-container/wiki/Quick-start---Spring-Boot2
// Avoid component scan and only import the component and configuration you need explicitly
// Before deploying your application to AWS Lambda, you should consider switching from the @ComponentScan
// annotation to direct @Import of your classes.
// use the @Autowired annotation to load the bean from a class you previously declared in your @Import annotation.

@Import({ LoadDatabase.class, ItemController.class, RestResponseEntityExceptionHandler.class })
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { ItemRepository.class })
@EntityScan(basePackageClasses = { Item.class })
@EnableJpaAuditing()

public class Application {

  /*
   * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
   */
  @Bean
  HandlerMapping handlerMapping() {
    return new RequestMappingHandlerMapping();
  }

  /*
   * Create required HandlerAdapter, to avoid several default HandlerAdapter instances being created
   */
  @Bean
  HandlerAdapter handlerAdapter() {
    return new RequestMappingHandlerAdapter();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
