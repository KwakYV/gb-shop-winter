package ru.gb.service.observers.order;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.api.order.dto.OrderDto;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderData implements OrderObservable{
    private List<OrderObserver> orderObservers;

    @Override
    public void registerObserver(OrderObserver orderObserver) {
            orderObservers.add(orderObserver);
    }

    @Override
    public void removeObserver(OrderObserver orderObserver) {
        orderObservers.remove(orderObserver);
    }

    @Override
    public void notifyObservers(OrderDto orderDto) {
        for (OrderObserver orderObserver : orderObservers) {
            orderObserver.send(orderDto);
        }
    }
}
