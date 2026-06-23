# Stage 1: Build
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

COPY src ./src
RUN ./mvnw package -DskipTests -B

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN addgroup --system fleet && adduser --system --ingroup fleet fleet

COPY --from=builder /app/target/*.jar app.jar

RUN chown -R fleet:fleet /app
USER fleet

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
