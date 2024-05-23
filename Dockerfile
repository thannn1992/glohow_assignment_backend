
#Which "official Java image" ?
FROM openjdk:23-oraclelinux8
#working directory
WORKDIR /app
RUN microdnf install dos2unix && microdnf clean all

#copy from your Host(Pc, Laptop) to container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

#Run this inside the image
RUN ./mvnw dependency:go-offline

#Change permission for mvnw script
RUN chmod a+x ./mvnw

# Convert line endings for mvnw script
RUN dos2unix ./mvnw

RUN ls -la /app

COPY src ./src

#Run inside container
CMD ["./mvnw", "spring-boot:run"]