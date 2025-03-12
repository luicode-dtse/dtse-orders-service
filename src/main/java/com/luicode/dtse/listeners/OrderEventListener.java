package com.luicode.dtse.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luicode.dtse.enums.OrderStatus;
import com.luicode.dtse.events.OrderCompletedEvent;
import com.luicode.dtse.events.OrderFailedEvent;
import com.luicode.dtse.services.OrderService;

@Component
public class OrderEventListener {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "order.completed.queue")
    public void handleOrderCompleted(OrderCompletedEvent event) {
        System.out.println("Received OrderCompletedEvent for Order ID: " + event.getOrderId());
        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.COMPLETED);
    }

    @RabbitListener(queues = "order.failed.queue")
    public void handleOrderFailed(OrderFailedEvent event) {
        System.out.println("Received OrderFailedEvent for Order ID: " + event.getOrderId());
        orderService.updateOrderStatus(event.getOrderId(), OrderStatus.FAILED);
    }
}
