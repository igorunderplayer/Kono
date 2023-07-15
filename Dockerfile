FROM openjdk:22-jdk

COPY . /app

WORKDIR /app

RUN microdnf install findutils
RUN ./gradlew --no-daemon build shadowJar

COPY config.properties /app/build/libs/config.properties

WORKDIR /app/build/libs

CMD ["java", "-jar", "KonoBot.jar"]
