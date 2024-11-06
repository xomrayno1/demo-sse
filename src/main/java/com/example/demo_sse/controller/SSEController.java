package com.example.demo_sse.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSEController {

	private Map<String, SseEmitter> deviceEmitters = new HashMap<>();
	
	@GetMapping(value = "/subscribe/{deviceId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter streamSSE(@PathVariable String deviceId) throws IOException, InterruptedException {
		final SseEmitter sseEmitter = new SseEmitter(50000l);
		 sseEmitter.onCompletion(() -> {
	            synchronized (this.deviceEmitters) {
	                this.deviceEmitters.remove(sseEmitter);
	            }
	        });
  
	     sseEmitter.onTimeout(()-> {
	            sseEmitter.complete();
	     });
	        
        // Put context in a map
		deviceEmitters.put(deviceId, sseEmitter);
	    return sseEmitter;
	}
	
	@GetMapping("/send")
	public ResponseEntity<String> sendEvent(@RequestParam String message, @RequestParam String deviceId) throws IOException {
		SseEmitter sseEmitter = deviceEmitters.get(deviceId);
		sseEmitter.send(message);
		sseEmitter.complete();
		return ResponseEntity.ok("send success");
	}
	
	 
	 
}
