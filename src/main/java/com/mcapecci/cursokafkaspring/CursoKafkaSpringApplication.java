package com.mcapecci.cursokafkaspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

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
		String message = "sample message";
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("mcapecci-topic", message);

		future.addCallback(new KafkaSendCallback<String, String>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}

			@Override
			public void onFailure(KafkaProducerException ex) {
				log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});

	}

}
