# Build
Since Tomcat is excluded from the path it will not be possible to start this Spring boot app locally.
To do that remove the lines in build.gradle that excludes tomcat.


# Testing at AWS Lambda Consol
The lambda "endpoint" this Spring boot app exposes is se.solrike.cloud.serverless.StreamLambdaHandler::handleRequest
which will transform the payload to a HttpRequest to send in to SPring MVC.

When testing in AWS Lamda console create a test event of "apigateway-aws-proxy" which is in essence a http request.
In that template you can fill in the URL/Path you want to invoke.



# Source code
The package structure does not follows Spring's recommended layout of one package per feature/domain concept.
See https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.structuring-your-code

Instead it has one package per function and the reason is to avoid unnecessary package scans. In a lambda setup startup time is important.
