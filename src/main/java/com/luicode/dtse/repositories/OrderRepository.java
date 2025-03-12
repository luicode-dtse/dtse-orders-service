package com.luicode.dtse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luicode.dtse.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
