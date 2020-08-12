package com.example.supershop.repository;

import com.example.supershop.model.RequestLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLineItemRepository  extends JpaRepository<RequestLineItem,Long> {
}
