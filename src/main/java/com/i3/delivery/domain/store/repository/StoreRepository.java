package com.i3.delivery.domain.store.repository;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.repository.custom.StoreRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {

}
