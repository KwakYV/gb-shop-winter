package ru.gb.service.observers.order;

import ru.gb.api.order.dto.OrderDto;

public interface OrderObservable {
    public void registerObserver(OrderObserver orderObserver);
    public void removeObserver(OrderObserver orderObserver);
    public void notifyObservers(OrderDto orderDto);
}
