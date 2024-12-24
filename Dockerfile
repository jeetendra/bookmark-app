FROM bellsoft/liberica-openjdk-alpine

LABEL authors="jeetendra.2.singh"

WORKDIR /usr/share/app

COPY target/*.jar app.jar

CMD ["java", "-jar", "app.jar" ]
