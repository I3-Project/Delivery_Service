package com.i3.delivery.domain.store.repository.custom.impl;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.Status;
import com.i3.delivery.domain.store.repository.custom.StoreRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.i3.delivery.domain.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Store> findAll(String keyword){
        List<Store> stores = queryFactory
                .selectFrom(store)
                .where(nameCon(keyword)
                        .or(categoryCon(keyword)
                                .or(statusCon(keyword))))
                .fetch();
        return stores;
    }

    private BooleanExpression nameCon(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return store.name.contains(keyword);
    }

    private BooleanExpression categoryCon(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        return store.category.contains(keyword);
    }

    private BooleanExpression statusCon(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        for(Status status : Status.values()) {
            if (status.name().equals(keyword)) {
                return store.status.eq(status);
            }
        }
        return null;
//        return store.status.in(Status.valueOf(keyword));
//        return store.status.eq(Status.valueOf(keyword));
    }


}
