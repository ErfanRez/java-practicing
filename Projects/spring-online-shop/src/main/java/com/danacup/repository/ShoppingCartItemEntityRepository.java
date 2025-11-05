package com.danacup.repository;

import com.danacup.entity.ShoppingCartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShoppingCartItemEntityRepository extends JpaRepository<ShoppingCartItemEntity, Long>, JpaSpecificationExecutor<ShoppingCartItemEntity> {
}