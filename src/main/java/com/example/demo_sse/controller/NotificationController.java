package com.example.demo_sse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	
	@GetMapping
    public ResponseEntity<Long> notifications() {
        return ResponseEntity.ok(System.currentTimeMillis());
    }

}
