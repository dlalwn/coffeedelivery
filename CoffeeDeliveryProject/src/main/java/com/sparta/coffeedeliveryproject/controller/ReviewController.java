package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.ReviewRequestDto;
import com.sparta.coffeedeliveryproject.dto.ReviewResponseDto;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{cafeId}/orders/{orderId}/reviews")
    public ReviewResponseDto createReview(@PathVariable Long cafeId,
                                          @PathVariable Long orderId,
                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){

        return reviewService.createReview(cafeId, orderId, requestDto, userDetails.getUser());
    }

    @GetMapping("/{cafeId}/reviews")
    public List<ReviewResponseDto> getReviewCafe(@PathVariable Long cafeId){

        return reviewService.getReviewCafe(cafeId);
    }

    @PutMapping("/reviews/{reviewId}")
    public ReviewResponseDto updateReview(@PathVariable Long reviewId,
                                          @Valid @RequestBody ReviewRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){

        return reviewService.updateReview(reviewId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return reviewService.deleteReview(reviewId, userDetails.getUser());
    }

}
