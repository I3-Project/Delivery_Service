package com.i3.delivery.domain.category.repository;

import com.i3.delivery.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
