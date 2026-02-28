package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class SpamDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpamDetectionApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void openBrowserAfterStartup() {
		try {
			String url = "http://localhost:8080/index.html";
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			} else {
				Runtime.getRuntime().exec("cmd /c start " + url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
