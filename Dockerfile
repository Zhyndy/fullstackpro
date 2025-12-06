# Этап 1: Сборка (Build)
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
# Даем права на выполнение градлу и собираем проект, пропуская тесты для скорости
RUN chmod +x gradlew && ./gradlew build -x test

# Этап 2: Запуск (Run)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Копируем собранный JAR файл из предыдущего этапа
COPY --from=build /app/build/libs/*.jar app.jar
# Открываем порт 8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]