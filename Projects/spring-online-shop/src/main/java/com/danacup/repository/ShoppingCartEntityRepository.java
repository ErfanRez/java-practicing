package com.danacup.repository;

import com.danacup.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShoppingCartEntityRepository extends JpaRepository<ShoppingCartEntity, Long>, JpaSpecificationExecutor<ShoppingCartEntity> {
}