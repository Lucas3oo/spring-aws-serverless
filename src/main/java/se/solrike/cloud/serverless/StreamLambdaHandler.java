package se.solrike.cloud.serverless;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import se.solrike.cloud.serverless.app.Application;

public class StreamLambdaHandler implements RequestStreamHandler {
  private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> sHandler;

  // This means AWS Lambda executes the code as it starts the JVM, giving you better performance out of the gate.
  static {
    try {
      // sHandler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);

      // For applications that take longer than 10 seconds to start, use the async builder:
      sHandler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>().defaultProxy()
          .asyncInit()
          .springBootApplication(Application.class)
          .buildAndInitialize();

    }
    catch (ContainerInitializationException e) {
      throw new RuntimeException("Could not initialize Spring Boot application", e);
    }
  }

  public StreamLambdaHandler() {
    // we enable the timer for debugging. This SHOULD NOT be enabled in production.
    // Timer.enable();
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    sHandler.proxyStream(inputStream, outputStream, context);
  }
}