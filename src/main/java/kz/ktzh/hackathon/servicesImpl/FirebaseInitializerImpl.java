package kz.ktzh.hackathon.servicesImpl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FirebaseInitializerImpl implements FirebaseInitializer {
    private String status;

    @Override
    public String getStatus() {
        return status;
    }

    @PostConstruct
    private void postConstruct() throws FileNotFoundException {

        FileInputStream serviceAccount =
                new FileInputStream("/home/cmaster/IdeaProjects/samruk-hackathon/src/main/resources/static/ktzh-analytics-firebase-adminsdk-9pshx-3f1534a769.json");

        FirebaseOptions options = null;
        try {
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (options != null) {
            FirebaseApp.initializeApp(options);
        }
    }
}
