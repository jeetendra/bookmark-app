package com.jeet.bookmarkapp.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class OrderCreateHandler {

    private final OrderService orderService;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = "order.created",
            groupId = "order.created.consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(Order payload) {
        log.info("OrderCreateHandler received payload: {}", payload);
        orderService.process(payload);
    }
}
