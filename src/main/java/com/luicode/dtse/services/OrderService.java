package com.luicode.dtse.services;

import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.luicode.dtse.dtos.CreateOrderRequest;
import com.luicode.dtse.entities.Order;
import com.luicode.dtse.enums.OrderStatus;
import com.luicode.dtse.events.OrderCreatedEvent;
import com.luicode.dtse.repositories.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
            .customerId(request.getCustomerId())
            .totalAmount(request.getTotalAmount())
            .status(OrderStatus.PENDING)
            .build();
        
        Order savedOrder = orderRepository.save(order);

        // Publish an OrderCreatedEvent to RabbitMQ
        rabbitTemplate.convertAndSend("order.exchange", "order.created", 
            new OrderCreatedEvent(savedOrder.getId(), savedOrder.getCustomerId(), savedOrder.getTotalAmount()));

        return savedOrder;
    }

    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }


    @Transactional
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
