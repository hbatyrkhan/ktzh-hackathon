package kz.ktzh.hackathon.controllers;

import kz.ktzh.hackathon.dto.ApiResponseDto;
import kz.ktzh.hackathon.dto.ParserResponseDto;
import kz.ktzh.hackathon.services.DataService;
import kz.ktzh.hackathon.services.FirebaseInitializer;
import kz.ktzh.hackathon.services.RouteService;
import kz.ktzh.hackathon.servicesImpl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class DefaultController {
    @Autowired
    private DataService dataService;
    @Autowired
    private RouteService routeService;

    RestTemplate rest = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    final String DjangoURL = "https://railways-ml.herokuapp.com/ml/";

    @GetMapping("/parse")
    public ResponseEntity<ApiResponseDto> parse(@RequestParam(name = "route") String route,
    @RequestParam(name = "date") String date) throws ExecutionException, InterruptedException {
        System.out.println("Thread: " + Thread.currentThread().getName());
        String from = routeService.getFromByRouteNumber(route), to = routeService.getToByRouteNumber(route);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ParserResponseDto> request =
                new HttpEntity<>(dataService.getData(from, to, date, route), headers);
        ApiResponseDto response = rest.postForObject(DjangoURL, request, ApiResponseDto.class);
        return ResponseEntity.ok(response);
    }
}
