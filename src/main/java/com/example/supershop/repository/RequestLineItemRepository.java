package com.example.supershop.repository;

import com.example.supershop.model.RequestLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLineItemRepository  extends JpaRepository<RequestLineItem,Long> {
}
