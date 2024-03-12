FROM openjdk:17

WORKDIR /app

COPY target/*.jar /app/ms-cliente.jar

ENV PG_HOST=jdbc:postgresql://localhost:5432/clientes
ENV PG_USER=postgres
ENV PG_PASSWORD=postgres

CMD ["java", "-jar", "ms-cliente.jar"]