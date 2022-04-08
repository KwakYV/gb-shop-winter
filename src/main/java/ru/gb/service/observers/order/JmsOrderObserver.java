package ru.gb.service.observers.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.gb.api.events.OrderEvent;
import ru.gb.api.order.dto.OrderDto;
import ru.gb.config.JmsConfig;

@Component
@AllArgsConstructor
@Slf4j
public class JmsOrderObserver implements OrderObserver{

    private final JmsTemplate jmsTemplate;

    @Override
    public void send(OrderDto savedOrderDto) {
        jmsTemplate.convertAndSend(JmsConfig.ORDER_CHANGED, new OrderEvent(savedOrderDto));
    }
}
