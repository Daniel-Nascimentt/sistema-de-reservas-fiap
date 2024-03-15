FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/*.jar ms-reservas.jar

ENV PG_HOST=meu-postgres
ENV PG_USER=postgres
ENV PG_PASSWORD=postgres
ENV MS_EMAIL=ms-email
ENV MS_CLIENTES=ms-clientes
ENV MS_SERVICOS=ms-servicos-opcionais
ENV MS_QUARTOS=ms-quartos

CMD ["java", "-jar", "ms-reservas.jar"]