package kz.ktzh.hackathon.servicesImpl;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import kz.ktzh.hackathon.Parser.Parser;
import kz.ktzh.hackathon.dto.ParserResponseDto;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class FirebaseInitializerImpl implements FirebaseInitializer {

    @Override
    public Future<ParserResponseDto> sendData(String from, String to, String date, String route) {
        ParserResponseDto data;
        try {
            data = Parser.getParserData(from, to, date, route);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String docName = route + ", " + date;
        System.out.println("Thread: " + Thread.currentThread().getName());
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("parsed_data").document(docName);
        // Add document data  with id "alovelace" using a hashmap
        // from + " - " + to + ", " + date
        Map<String, Object> fData = new HashMap<>();
        fData.put("trainNumber", data.getTrainNumber());
        fData.put("from", data.getFrom());
        fData.put("to", data.getTo());
        fData.put("date", data.getDate());
        fData.put("stations", data.getStations());
        fData.put("wagons", data.getWagons());
        // asynchronously write data
        docRef.set(data);
        return new AsyncResult<>(data);
    }

    @PostConstruct
    private void postConstruct() throws FileNotFoundException {

        FileInputStream serviceAccount =
                new FileInputStream("/home/cmaster/IdeaProjects/samruk-hackathon/src/main/resources/static/ktzh-analytics-ac123-firebase-adminsdk-37xf4-79d6751e1b.json");

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
