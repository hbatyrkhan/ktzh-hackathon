package kz.ktzh.hackathon.services;

public interface RouteService {
    String getFromByRouteNumber(String route);
    String getToByRouteNumber(String route);
}
