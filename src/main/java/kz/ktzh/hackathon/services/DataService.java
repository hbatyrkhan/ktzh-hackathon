package kz.ktzh.hackathon.services;

import kz.ktzh.hackathon.dto.ParserResponseDto;

import java.util.concurrent.ExecutionException;

public interface DataService {
    ParserResponseDto getData(String from, String to, String date, String route) throws ExecutionException, InterruptedException;
}
