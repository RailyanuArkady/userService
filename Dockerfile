# # Используем базовый образ с OpenJDK
# FROM openjdk:17-jdk-slim
#  # Указываем рабочую директорию внутри контейнера
# WORKDIR /app
#  # Копируем JAR-файл в контейнер
# COPY build/libs/*.jar /app/user-service.jar
#  # Указываем команду для запуска приложения
# CMD ["java", "-jar", "user-service.jar"]
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]