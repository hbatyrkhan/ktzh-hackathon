package kz.ktzh.hackathon.servicesImpl;

import kz.ktzh.hackathon.services.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {
    @Override
    public String getFromByRouteNumber(String route) {
        switch (route) {
            case "021Ц":
                return "СЕМЕЙ";
            case "313Ц":
                return "МАНГИСТАУ";
            case "327Т":
                return "КАРАГАНДЫ ПАСС";
            case "031Х":
                return "АЛМАТЫ 2";
            case "033Ц":
                return "АЛМАТЫ 1";
            case "003Ц":
                return "АЛМАТЫ";
            default:
                return "";
        }
    }

    @Override
    public String getToByRouteNumber(String route) {
        switch (route) {
            case "021Ц":
                return "КЫЗЫЛОРДА";
            case "313Ц":
                return "АТЫРАУ";
            case "327Т":
                return "КОСТАНАЙ";
            case "031Х":
                return "ПАВЛОДАР";
            case "033Ц":
                return "АКТОБЕ-1";
            case "003Ц":
                return "НУР-СУЛТАН";
            default:
                return "";
        }
    }
}
