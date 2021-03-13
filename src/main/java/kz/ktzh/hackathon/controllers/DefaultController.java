package kz.ktzh.hackathon.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import kz.ktzh.hackathon.dto.NamedDto;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class DefaultController {
    @Autowired
    private FirebaseInitializer firebaseInitializer;

    @GetMapping("/hello")
    public ResponseEntity<NamedDto> hello(@RequestParam(name = "name", required = false) Optional<String> name) throws ExecutionException, InterruptedException {
        NamedDto body = new NamedDto();
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference docRef = firestore.collection("users").document("alovelace");
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("first", "Ada");
        data.put("last", "Lovelace");
        data.put("born", 1815);
        // asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();// blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
        body.setName(result.get().toString());

        return ResponseEntity.ok(body);
    }
}
