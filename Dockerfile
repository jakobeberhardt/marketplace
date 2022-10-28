### Gradle caching stage ###
FROM gradle:7.4-jdk17-alpine as cache
RUN mkdir -p /cache /populate
ENV GRADLE_USER_HOME /cache
COPY build.gradle.kts /populate
WORKDIR /populate
RUN gradle clean build -i -x bootJar --stacktrace

### Build stage ###
FROM gradle:7.4-jdk17-alpine as builder
WORKDIR /marketplace
COPY --from=cache /cache /home/gradle/.gradle
COPY gradlew .s
COPY gradle gradle
COPY .gradle .gradle
COPY build.gradle.kts .
COPY src src
RUN gradle bootJar -DskipTests

### Final stage ###
FROM eclipse-temurin:17-jdk-alpine
COPY --from=builder /marketplace/build/libs/*.jar /
RUN ln -s $(find / -name *.jar) marketplace.jar
EXPOSE 8080
STOPSIGNAL SIGINT
ENTRYPOINT ["java", "-jar", "/marketplace.jar"]