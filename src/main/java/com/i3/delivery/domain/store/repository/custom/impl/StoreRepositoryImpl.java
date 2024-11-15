package com.i3.delivery.domain.store.repository.custom.impl;

import com.i3.delivery.domain.store.dto.StoreReviewResponseDto;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.custom.StoreRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.i3.delivery.domain.review.entity.QReview.review;
import static com.i3.delivery.domain.store.entity.QStore.store;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO Paging (+ Sorting)
    @Override
    public List<Store> findAll(String keyword){
        List<Store> stores = queryFactory
                .selectFrom(store)
            // TODO .or 보다 여러개 파라미터 사용하는것 고민해보기 (메소드 추출로 eqCatetory 네이밍 where category = "category")
                .where(nameCon(keyword)
                        .or(categoryCon(keyword)
                                .or(statusCon(keyword))))
                .orderBy(
                        store.name.asc(),
                        store.category.asc(),
                        store.status.asc()
                )
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

        for(StoreStatus status : com.i3.delivery.domain.store.enums.StoreStatus.values()) {
            if (status.name().equals(keyword)) {
                return store.status.eq(status);
            }
        }
        return null;
    }

    @Override
    public List<StoreReviewResponseDto> findStoreAvgAndReviews(String name) {

        List<StoreReviewResponseDto> reviews = queryFactory
                .select(
                        Projections.constructor(
                                StoreReviewResponseDto.class,
                                store.ratingAvg,
                                review.reviewId,
                                review.store.id,
                                review.rating,
                                review.content,
//                                review.userName,
                                review.createdAt
                        )
                )
                .from(store)
                .join(review)
                .on(store.id.eq(review.store.id))
                .where(store.name.eq(name))
                .fetch();

        long totalCount = reviews.size();

        double average = reviews.stream()
                .map(StoreReviewResponseDto::getRating)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        queryFactory.update(store)
                .set(store.ratingAvg, (int)average)
                .where(store.name.eq(name))
                .execute();

        queryFactory.update(store)
                .set(store.totalReviews, (int)totalCount)
                .where(store.name.eq(name))
                .execute();

        return reviews;
    }


}
