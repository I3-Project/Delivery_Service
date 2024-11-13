package com.i3.delivery.domain.store.repository.custom;

import com.i3.delivery.domain.store.entity.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findAll(String keyword);
}
