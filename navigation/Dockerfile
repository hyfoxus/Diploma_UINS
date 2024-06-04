# Используйте образ с JDK 11
FROM openjdk:18-ea-1-jdk-slim

# Установите рабочую директорию
WORKDIR /app

# Скопируйте файл jar вашего приложения в контейнер
COPY build/libs/navigation-0.0.1-SNAPSHOT.jar app.jar

# Укажите команду запуска вашего приложения
ENTRYPOINT ["java", "-jar", "app.jar"]

# Укажите порт, который будет слушать ваше приложение
EXPOSE 8080