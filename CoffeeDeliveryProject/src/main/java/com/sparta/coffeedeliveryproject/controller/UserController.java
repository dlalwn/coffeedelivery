package com.sparta.coffeedeliveryproject.controller;

import com.sparta.coffeedeliveryproject.dto.*;
import com.sparta.coffeedeliveryproject.security.UserDetailsImpl;
import com.sparta.coffeedeliveryproject.service.CafeService;
import com.sparta.coffeedeliveryproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CafeService cafeService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {

        MessageResponseDto responseDto = userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(@RequestBody LogingRequestDto logingRequestDto, HttpServletResponse response) {

        MessageResponseDto loginMessage = userService.login(logingRequestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(loginMessage);
    }

    @PutMapping("/profile")
    public UserProfileEditResponseDto editProfile(@RequestBody UserProfileEditRequestDto userProfileEditRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.editProfile(userProfileEditRequestDto, userDetails.getUser().getUserId());
    }

    @PostMapping("/loggout")
    public MessageResponseDto logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.logout(userDetails.getUser().getUserId());
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<MessageResponseDto> reissueToken(HttpServletRequest request, HttpServletResponse response) {

        MessageResponseDto responseDto = userService.reissueToken(request, response);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/cafes/like")
    public ResponseEntity<Page<CafeResponseDto>> getUserLikeCafes(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Page<CafeResponseDto> responseDtoList = userService.getUserLikeCafe(page - 1, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

    @GetMapping("/reviews/like")
    public ResponseEntity<Page<ReviewResponseDto>> getUserLikeReview(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Page<ReviewResponseDto> responseDtoList = userService.getUserLikeReview(page - 1, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }

}