FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

ENV SPRING_MAIL_PASS=sua_pass

WORKDIR /app

COPY --from=builder /app/target/*.jar ms-email.jar

CMD ["java", "-jar", "ms-email.jar"]