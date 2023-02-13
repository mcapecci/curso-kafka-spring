package com.mcapecci.cursokafkaspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class CursoKafkaSpringApplication implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(CursoKafkaSpringApplication.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CursoKafkaSpringApplication.class, args);
	}

	@KafkaListener(topics = "mcapecci-topic", groupId = "mcapecci-group")
	public void listen(String message) {
		log.info("Message Received: " + message);
	}

	@Override
	public void run(String... args) throws Exception {
		kafkaTemplate.send("mcapecci-topic", "sample message");
	}

}
