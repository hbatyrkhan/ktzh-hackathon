package kz.ktzh.hackathon.services;

import kz.ktzh.hackathon.dto.ParserResponseDto;

import java.util.concurrent.Future;

public interface FirebaseInitializer {
    Future<ParserResponseDto> sendData(String from, String to, String date, String route);
}
