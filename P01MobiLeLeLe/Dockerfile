# Използваме за базов образ Amazon Corretto с Java 17
FROM amazoncorretto:17.0.6

# Папка, в която ще се изпълняват командите
WORKDIR /app

# Копиране на JAR файла от локалната машина до контейнера
COPY target/P01MobiLeLeLe.jar app.jar

# Команда, която се изпълнява при стартиране на контейнера
ENTRYPOINT ["java", "-jar", "app.jar"]