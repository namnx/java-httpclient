# java-httpclient
A simple project to test Java HTTP clients on native and container environment. 
This is used for troubleshooting Java HTTP clients running inside a container. 
In some rare cases, the HTTP requests (using Java) are successful in native environment, 
but failed when run inside a container.  

## Run the application and send requests
```shell
./gradlew bootrun
```

* Fetch a url using OkHttp:
```shell
curl localhost:8080/get1?url=https://httpbin.org/get
```

* Fetch a url using Java 11 HttpClient:
```shell
curl localhost:8080/get2?url=https://httpbin.org/get
```


## Build the container image using Spring Boot Gradle plugin
```shell
./gradlew bootBuildImage --imageName=java-httpclient-springboot
```

* Run this image in Docker:
```shell
docker run -ti --rm -p8080:8080 java-httpclient-springboot
```

* Run with network debugging:
```shell
docker run -ti --rm -p8080:8080 -e LOGGING_LEVEL_ROOT=DEBUG \
  -e JAVA_OPTS="-Djavax.net.debug=all" java-httpclient-springboot
```

## Build the container image using Jib
```shell
./gradlew jibDockerBuild --image=java-httpclient-jib
```

* Run this image in Docker:
```shell
docker run -ti --rm -p8080:8080 java-httpclient-jib
```

* Run with networking debugging:
```shell
docker run -ti --rm -p8080:8080 -e LOGGING_LEVEL_ROOT=DEBUG \
  -e JAVA_OPTS="-Djavax.net.debug=all" java-httpclient-jib
```


## Troubleshooting
* Get the running container-id:
```shell
docker ps
```

* Get a shell into a running container:
```shell
docker exec -it <container-id> /bin/bash
```