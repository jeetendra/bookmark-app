package com.jeet.bookmarkapp.order.service;

import com.jeet.bookmarkapp.order.message.OrderCreated;
import com.jeet.bookmarkapp.order.message.OrderDispatched;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(OrderCreated orderCreated) throws Exception {
        log.info("Processing payload: {}", orderCreated);

        OrderDispatched dispatched = new OrderDispatched();
        dispatched.setOrderId(orderCreated.getId());

        kafkaProducer.send("order.dispatched", dispatched).get();

    }
}
