# Этап 1: Сборка (Build)
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# --- ИЗМЕНЕНИЕ ЗДЕСЬ ---
# Копируем содержимое папки lab05 в текущую папку контейнера (/app)
COPY lab05 . 
# -----------------------

# Даем права на выполнение и собираем
RUN chmod +x gradlew && ./gradlew build -x test

# Этап 2: Запуск (Run)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Копируем JAR. Путь остается прежним, так как сборка произошла внутри /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]