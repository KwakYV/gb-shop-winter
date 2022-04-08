package ru.gb.service.observers.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.autoconfig.instrument.messaging.SleuthMessagingProperties;
import org.springframework.stereotype.Component;
import ru.gb.api.order.dto.OrderDto;

@Component
@AllArgsConstructor
@Slf4j
public class OrderObserverPool {
    private final JmsOrderObserver jmsOrderObserver;
    private final OrderData orderData;

    public void register(){
        orderData.registerObserver(jmsOrderObserver);
    }

    public void notify(OrderDto orderDto){
        orderData.notifyObservers(orderDto);
    }
}
