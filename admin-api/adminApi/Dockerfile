FROM openjdk:8-jre
ENV ADMIN_API /adminApi
WORKDIR $ADMIN_API
COPY admin-api-0.0.1-SNAPSHOT.jar $ADMIN_API/adminApi.jar
EXPOSE 8081
ENTRYPOINT ["nohup", "java", "-jar"]
CMD ["adminApi.jar", "&"]