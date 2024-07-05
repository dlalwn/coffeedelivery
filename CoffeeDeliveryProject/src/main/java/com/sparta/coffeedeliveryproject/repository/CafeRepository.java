package com.sparta.coffeedeliveryproject.repository;

import com.sparta.coffeedeliveryproject.entity.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    Optional<Cafe> findByCafeName(String cafeName);

    @Query("SELECT c "
            + "FROM Cafe c "
            + "JOIN CafeLike cl ON c.cafeId = cl.cafe.cafeId "
            + "WHERE cl.user.userId = :userId ")
    Page<Cafe> findLikeCafeByUserUserId(Long userId, Pageable pageable);
}
