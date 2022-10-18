# Spring AWS Serverless

A small demo project to run Spring boot as a AWS lambda function. All the normal Spring features works like Spring Data and Spring MVC. See <https://github.com/awslabs/aws-serverless-java-container/wiki/Quick-start---Spring-Boot2>

The component can't be run locally since Tomcat isn't included in the runtime. In runtime the JAR will have AWS lambda classes on the classpath that will take care to convert the HTTP request to a format that Spring MVC understand.

The service is deployed using AWS Serverless Application Model (SAM) <https://github.com/aws/serverless-application-model>.

# Build
Since Tomcat is excluded from the path it will not be possible to start this Spring boot app locally.
To do that remove the lines in build.gradle that excludes tomcat.


# Testing at AWS Lambda Consol
The lambda "endpoint" this Spring boot app exposes is `se.solrike.cloud.serverless.StreamLambdaHandler::handleRequest`
which will convert the payload to a HttpRequest to send in to Spring MVC.

When testing in AWS Lamda console create a test event of "apigateway-aws-proxy" which is in essence a http request.
In that template you can fill in the URL/Path you want to invoke.

# Deployment
The `build.gradle` has tasks to deploy the lambda and create an API gateway using the Cloudformation template under the folder `aws-cloudformation` based on AWS SAM.


Once deployed you can access the service typically via:

    curl https://<API_ID>.execute-api.<AWS_REGION>.amazonaws.com/api/v1/items
    curl https://<API_ID>.execute-api.<AWS_REGION>.amazonaws.com/api/v1/items/1

    curl --header "Content-Type: application/json" \
      --request POST \
      --data '{"itemDescription":"Merry"}' \
      https://<API_ID>.execute-api.<AWS_REGION>.amazonaws.com/api/v1/items


# Source code
The package structure does not follows Spring's recommended layout of one package per feature/domain concept.
See <https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code>

Instead it has one package per function and the reason is to avoid unnecessary package scans. In a lambda setup startup time is important.

# Payload format 1.0 versus 2.0
For "RestApi" applications which used payload format 1.0 use AwsProxyRequest and AwsProxyResponse.
For "HttpApi" the default payload format is 2.0 which means HttpApiV2ProxyRequest and AwsProxyResponse shall be used.
But it is possible to configure the HttpApi version of API Gateway to use 1.0 format.

see <https://github.com/awslabs/aws-serverless-java-container/wiki#lambda-handler-classes>

# Misc
Guide to chose between "REST API" and "HTTP API"  <https://docs.aws.amazon.com/apigateway/latest/developerguide/http-api-vs-rest.html>

HTTP API is suppose to be the cheaper and faster but less flexible alternative.


# Endpoints timeout

The default and maximal API Gateway timeout is used: 30s. Ensure to keep function timeout below 29s. Otherwise, you may observe successful lambda invocations reported with 503 status code.



