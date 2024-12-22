package com.jeet.bookmarkapp.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {
    public void process(Order payload) {
        log.info("Processing payload: {}", payload);
    }
}
