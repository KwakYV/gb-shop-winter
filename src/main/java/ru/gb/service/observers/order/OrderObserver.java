package ru.gb.service.observers.order;

import ru.gb.api.order.dto.OrderDto;

public interface OrderObserver {
    public void send(OrderDto savedOrderDto);
}
