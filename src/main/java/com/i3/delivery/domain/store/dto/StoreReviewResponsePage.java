package com.i3.delivery.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@Data
public class StoreReviewResponsePage<T> {

    private Page<T> reviews;
    private StoreReviewMeta meta;

    public StoreReviewResponsePage(Page<T> reviews, StoreReviewMeta meta) {
        this.reviews = reviews;
        this.meta = meta;
    }
}
