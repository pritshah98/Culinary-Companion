package com.ps.culinarycompanion;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class CulinaryCompanionApplication {

	// https://firebase.google.com/docs/admin/setup
	private static InputStream inputStream =
			CulinaryCompanionApplication.class.getClassLoader().getResourceAsStream("serviceAccountKey.json");

	public static void main(String[] args) {
		SpringApplication.run(CulinaryCompanionApplication.class, args);
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(inputStream))
					.build();
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
