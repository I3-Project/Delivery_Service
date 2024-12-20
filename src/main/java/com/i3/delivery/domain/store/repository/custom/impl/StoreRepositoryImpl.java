package com.i3.delivery.domain.store.repository.custom.impl;

import com.i3.delivery.domain.store.dto.StoreInfoResponseDto;
import com.i3.delivery.domain.store.dto.StoreReviewMeta;
import com.i3.delivery.domain.store.dto.StoreReviewResponseDto;
import com.i3.delivery.domain.store.dto.StoreReviewResponsePage;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.custom.StoreRepositoryCustom;
import com.i3.delivery.domain.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.i3.delivery.domain.review.entity.QReview.review;
import static com.i3.delivery.domain.store.entity.QStore.store;

public class StoreRepositoryImpl extends QuerydslRepositorySupport implements StoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl (JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Store> findAll(String keyword, Pageable pageable){
        List<Store> stores = queryFactory
                .selectFrom(store)
                .where(orConditions(keyword))
                .orderBy(
                        store.name.asc(),
                        store.category.name.asc(),
                        store.status.asc()
                )
                .fetch();
        return stores;
    }

    // OR 조건을 만드는 메서드
    private BooleanExpression orConditions(String keyword) {
        return Expressions.anyOf(
                nameCon(keyword),
                categoryCon(keyword),
                statusCon(keyword)
        );
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
        return store.category.name.contains(keyword);
    }

    private BooleanExpression statusCon(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }

        for(StoreStatus status : StoreStatus.values()) {
            if (status.name().equals(keyword)) {
                return store.status.eq(status);
            }
        }
        return null;
    }

    @Override
    public StoreReviewResponsePage<StoreReviewResponseDto> findStoreReviewAvgAndReviews(String name, Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        JPAQuery<StoreReviewResponseDto> query = queryFactory
                .select(
                        Projections.constructor(
                                StoreReviewResponseDto.class,
                                store.ratingAvg,
                                review.id,
                                review.store.id,
                                review.rating,
                                review.content,
                                review.user.nickname,
                                review.createdAt
                        )
                )
                .from(store)
                .join(review)
                .on(store.id.eq(review.store.id))
                .where(store.name.eq(name));

        List<StoreReviewResponseDto> reviews = getQuerydsl().applyPagination(pageable,query).fetch();

        int totalCount = reviews.size();
        double sum = 0;
        for (StoreReviewResponseDto item : reviews) {
            sum += item.getRating();
        }

        double average = totalCount > 0 ? sum / totalCount : 0;

        queryFactory.update(store)
                .set(store.totalReviews, totalCount)
                .where(store.name.eq(name))
                .execute();

        PageImpl<StoreReviewResponseDto> page = new PageImpl<>(reviews, pageable, totalCount);

        StoreReviewMeta meta = new StoreReviewMeta(totalCount, (int)average);

        return new StoreReviewResponsePage<>(page,meta);
    }

    @Override
    public Page<StoreInfoResponseDto> findStores(User user, Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        JPAQuery<StoreInfoResponseDto> query = queryFactory
                .select(
                        Projections.fields(
                                StoreInfoResponseDto.class,
                                store.name,
                                store.description,
                                store.category.id,
                                store.user.id,
                                store.address,
                                store.phoneNumber,
                                store.status,
                                store.totalReviews,
                                store.ratingAvg
                        )
                )
                .from(store)
                .where(store.deletedAt.isNull());

        List<StoreInfoResponseDto> stores = getQuerydsl().applyPagination(pageable,query).fetch();

        long totalCount = stores.size();

        return new PageImpl<>(stores,pageable,totalCount);

    }

    public Page<StoreInfoResponseDto> findStoresMaster(User user, Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        JPAQuery<StoreInfoResponseDto> query = queryFactory
                .select(
                        Projections.constructor(
                                StoreInfoResponseDto.class,
                                store.name,
                                store.description,
                                store.category.id,
                                store.user.id,
                                store.address,
                                store.phoneNumber,
                                store.status,
                                store.totalReviews,
                                store.ratingAvg,
                                store.createdAt,
                                store.createdBy,
                                store.updatedAt,
                                store.updatedBy,
                                store.deletedAt,
                                store.deletedBy
                        )
                )
                .from(store);

        List<StoreInfoResponseDto> stores = getQuerydsl().applyPagination(pageable,query).fetch();

        long totalCount = stores.size();

        return new PageImpl<>(stores,pageable,totalCount);
    }
}
