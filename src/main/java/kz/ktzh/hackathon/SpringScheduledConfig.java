package kz.ktzh.hackathon;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import kz.ktzh.hackathon.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Configuration
@EnableScheduling
public class SpringScheduledConfig {
    @Autowired
    private FirebaseInitializer firebaseInitializer;
    @Autowired
    private RouteService routeService;

    @Scheduled(fixedRate = 50000)
    public void scheduleFixedRateTask() throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> data = firestore.collection("parsed_data").get();
        List<QueryDocumentSnapshot> documents = data.get().getDocuments();
        documents.forEach(document -> {
            String[] split = document.getId().split(", ");
            String route = split[0], date = split[1];
            firebaseInitializer.sendData(routeService.getFromByRouteNumber(route), routeService.getToByRouteNumber(route), date, route);
        });
    }
}