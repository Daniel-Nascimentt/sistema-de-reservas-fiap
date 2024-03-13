FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/*.jar ms-quartos.jar

ENV PG_HOST=meu-postgres
ENV PG_USER=postgres
ENV PG_PASSWORD=postgres

CMD ["java", "-jar", "ms-quartos.jar"]