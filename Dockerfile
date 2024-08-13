# Используем базовый образ Maven с JDK 17
FROM maven:3.8.5-openjdk-17

# Устанавливаем рабочую директорию
WORKDIR /ESDP_xfood

# Копируем все файлы в рабочую директорию
COPY . .

# Собираем проект Maven и пропускаем тесты
RUN mvn clean install -DskipTests

# Определяем команду для запуска приложения
CMD ["mvn", "spring-boot:run"]



