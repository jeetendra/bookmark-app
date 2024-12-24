package com.jeet.bookmarkapp.integration;

import com.jeet.bookmarkapp.config.KafkaConfig;
import com.jeet.bookmarkapp.order.message.DispatchPreparing;
import com.jeet.bookmarkapp.order.message.OrderCreated;
import com.jeet.bookmarkapp.order.message.OrderDispatched;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@SpringBootTest(classes = {KafkaConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@EmbeddedKafka(controlledShutdown = true)

public class OrderIntegrationTest {

    public static final String ORDER_CREATED_TOPIC = "order.created";
    public static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    public static final String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";

    @Autowired
    private KafkaTestListener kafkaTestListener;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @BeforeEach
    public void setUp() {
        kafkaTestListener.orderDispatchedCounter.set(0);
//        embeddedKafka.addTopics(ORDER_CREATED_TOPIC);
        registry.getListenerContainers().stream().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
        });
    }

    //    @Test
    public void test() throws Exception {
        OrderCreated orderCreated = OrderCreated.builder().id(UUID.randomUUID()).item(UUID.randomUUID().toString()).build();
        sendMessage(ORDER_CREATED_TOPIC, orderCreated);

        await().atMost(10, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS).until(kafkaTestListener.orderDispatchedCounter::get, equalTo(1));


    }

    private void sendMessage(String topic, Object data) throws Exception {
        kafkaTemplate.send(MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()).get();
    }

    @Configuration
    static class TestConfig {

        @Bean
        public KafkaTestListener testListener() {
            return new KafkaTestListener();
        }
    }

    static class KafkaTestListener {
        AtomicInteger dispatchPreparingCounter = new AtomicInteger(0);
        AtomicInteger orderDispatchedCounter = new AtomicInteger(0);

        @KafkaListener(topics = OrderIntegrationTest.ORDER_DISPATCHED_TOPIC, groupId = "KafkaIntegrationTest")
        void orderDispatched(@Payload OrderDispatched payload) {
            log.info("Order dispatched: {}", payload);
            orderDispatchedCounter.incrementAndGet();
            log.info("Order dispatched: {}", orderDispatchedCounter.get());
        }

        @KafkaListener(topics = OrderIntegrationTest.DISPATCH_TRACKING_TOPIC, groupId = "KafkaIntegrationTest")
        void receivedDispatched(@Payload DispatchPreparing payload) {
            log.info("Order dispatched: {}", payload);
            dispatchPreparingCounter.incrementAndGet();
            log.info("Order dispatched: {}", dispatchPreparingCounter.get());
        }
    }
}

