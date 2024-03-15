FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17

ENV HOST_CLIENTES=http://ms-clientes:8081/ms-cliente
ENV HOST_QUARTOS=http://ms-quartos:8083/ms-quartos
ENV HOST_RESERVAS=http://ms-reservas:8084/ms-reservas
ENV HOST_SERVICOS=http://ms-servicos-opcionais:8082/ms-servicos

WORKDIR /app

COPY --from=builder /app/target/*.jar ms-gateway.jar

CMD ["java", "-jar", "ms-gateway.jar"]