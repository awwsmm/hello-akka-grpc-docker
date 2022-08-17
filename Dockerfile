FROM sbtscala/scala-sbt:eclipse-temurin-17.0.3_1.7.1_2.13.8 as build
WORKDIR /agd/build
COPY src ./src
COPY build.sbt ./build.sbt
COPY project/build.properties ./project/build.properties
COPY project/plugins.sbt ./project/plugins.sbt
RUN sbt assembly

FROM arm64v8/eclipse-temurin:17.0.3_7-jre
COPY --from=build /agd/build/target/scala-2.13/out.jar /agd/run/out.jar
ENTRYPOINT ["java","-jar","./agd/run/out.jar"]
CMD []