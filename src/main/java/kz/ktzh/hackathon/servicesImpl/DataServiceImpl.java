package kz.ktzh.hackathon.servicesImpl;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import kz.ktzh.hackathon.dto.ParserResponseDto;
import kz.ktzh.hackathon.dto.WagonDto;
import kz.ktzh.hackathon.services.DataService;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private FirebaseInitializer firebaseInitializer;
    @Override
    public ParserResponseDto getData(String from, String to, String date, String route) throws ExecutionException, InterruptedException {
        String docName = route + ", " + date;
        System.out.println("Thread: " + Thread.currentThread().getName());
        Firestore firestore = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> data = firestore.collection("parsed_data").get().get().getDocuments();
        QueryDocumentSnapshot documentSnapshot = null;
        for(QueryDocumentSnapshot doc: data) {
            if(doc.getId().equals(docName)) documentSnapshot = doc;
        }
        if(documentSnapshot == null) return firebaseInitializer.sendData(from, to, date, route).get();
        Map<String, Object> snapshotData = documentSnapshot.getData();
        ParserResponseDto responseDto = new ParserResponseDto();
        responseDto.setDate((String) snapshotData.get("date"));
        responseDto.setFrom((String) snapshotData.get("from"));
        responseDto.setTo((String) snapshotData.get("to"));
        responseDto.setTrainNumber((String) snapshotData.get("trainNumber"));
        responseDto.setStations((ArrayList<String>) snapshotData.get("stations"));
        responseDto.setWagons((ArrayList<WagonDto>) snapshotData.get("wagons"));
        return responseDto;
    }
}
