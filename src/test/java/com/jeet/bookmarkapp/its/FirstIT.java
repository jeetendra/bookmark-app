package com.jeet.bookmarkapp.its;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class FirstIT {
//    @Autowired
//    private WebTestClient webClient;

    @Container
    private static final GenericContainer<?> kafka = new GenericContainer<>(DockerImageName.parse("bitnami/kafka:latest"))
            .withExposedPorts(9092, 9094)
            .withEnv("KAFKA_CFG_NODE_ID", "0")
            .withEnv("KAFKA_CFG_PROCESS_ROLES", "controller,broker")
            .withEnv("KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP", "CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT")
            .withEnv("KAFKA_CFG_CONTROLLER_QUORUM_VOTERS", "0@localhost:9093")
            .withEnv("KAFKA_CFG_CONTROLLER_LISTENER_NAMES", "CONTROLLER")
            .withEnv("KAFKA_CFG_LISTENERS", "PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094")
            .withEnv("KAFKA_CFG_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:9092,EXTERNAL://localhost:9094")
            .waitingFor(Wait.forListeningPort());


    @Container
    private static final GenericContainer<?> mysql = new GenericContainer<>(DockerImageName.parse("mysql:8.1"))
            .withExposedPorts(3306)
            .withEnv("MYSQL_USER", "root-user")
            .withEnv("MYSQL_PASSWORD", "123456")
            .withEnv("MYSQL_DATABASE", "bookmark")
            .withEnv("MYSQL_ROOT_PASSWORD", "root")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {

        System.out.println("Kafka container port: " + kafka.getMappedPort(9092));
        System.out.println("Kafka container external port: " + kafka.getMappedPort(9094));
        // Kafka properties
        var kafkaHost = kafka.getHost();
        var kafkaPort = kafka.getMappedPort(9094); // External communication port
        registry.add("KAFKA_ENDPOINT", () -> kafkaHost + ":" + kafkaPort);

        kafka.withLogConsumer(outputFrame -> System.out.print(outputFrame.getUtf8String()));


        var mysqlHost = mysql.getHost();
        var mysqlPort = mysql.getMappedPort(3306);
        registry.add("DB_HOST", () -> mysqlHost);
        registry.add("DB_PORT", () -> mysqlPort);
        registry.add("DB_NAME", () -> "bookmark");
        registry.add("DB_USER", () -> "root-user");
        registry.add("DB_PASSWORD", () -> "123456");
    }

    @Test
    public void firstTest() {

    }

}
