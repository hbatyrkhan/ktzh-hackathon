package kz.ktzh.hackathon.controllers;

import kz.ktzh.hackathon.dto.ParserResponseDto;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class DefaultController {
    @Autowired
    private FirebaseInitializer firebaseInitializer;

    @GetMapping("/parse")
    public ResponseEntity<ParserResponseDto> parse(@RequestParam(name = "route") String route,
    @RequestParam(name = "date") String date) throws ExecutionException, InterruptedException {
        System.out.println("Thread: " + Thread.currentThread().getName());
        String from, to;
        switch (route) {
            case "021Ц":
                from = "СЕМЕЙ";
                to = "КЫЗЫЛОРДА";
                break;
            case "313Ц":
                from = "МАНГИСТАУ";
                to = "АТЫРАУ";
                break;
            case "327Т":
                from = "КАРАГАНДЫ ПАСС";
                to = "КОСТАНАЙ";
                break;
            case "031Х":
                from = "АЛМАТЫ 2";
                to = "ПАВЛОДАР";
                break;
            case "033Ц":
                from = "АЛМАТЫ 1";
                to = "АКТОБЕ-1";
                break;
            case "003Ц":
                from = "АЛМАТЫ";
                to = "НУР-СУЛТАН";
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        Future<ParserResponseDto> result = firebaseInitializer.sendData(from, to, date, route);
        return ResponseEntity.ok(result.get());
    }
}
