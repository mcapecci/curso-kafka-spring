package com.mcapecci.cursokafkaspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class CursoKafkaSpringApplication {
	private static final Logger log = LoggerFactory.getLogger(CursoKafkaSpringApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(CursoKafkaSpringApplication.class, args);
	}
	
	@KafkaListener(topics = "mcapecci-topic", groupId = "mcapecci-group")
	public void listen(String message) {
	    log.info("Message Received: " + message);
	}

}
