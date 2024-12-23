package com.jeet.bookmarkapp.order.handler;

import com.jeet.bookmarkapp.order.service.OrderService;
import com.jeet.bookmarkapp.order.message.OrderCreated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Slf4j
@Component
@KafkaListener(
        id = "orderConsumerClient",
        topics = "order.created",
        groupId = "order.created.consumer",
        containerFactory = "kafkaListenerContainerFactory"
)
public class OrderHandler {

    private final OrderService orderService;

    @KafkaHandler
    public void listen(OrderCreated payload) {
        log.info("OrderHandler received payload: {}", payload);
        try {
            orderService.process(payload);
        } catch (Exception e) {
            log.error("OrderHandler error");
        }
    }
}
