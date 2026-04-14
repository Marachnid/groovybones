FROM gradle:7.6.4-jdk17 AS builder
WORKDIR /app

# copy Gradle build system first for caching
COPY build.gradle gradle.properties ./
COPY gradle ./gradle
COPY gradlew gradlew.bat ./
RUN chmod +x gradlew


# pre-fetch dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy only code needed to build JAR
COPY grails-app ./grails-app

# build application
RUN ./gradlew clean bootJar --no-daemon


FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]