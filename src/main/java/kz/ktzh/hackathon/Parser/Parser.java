package kz.ktzh.hackathon.Parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.ktzh.hackathon.dto.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Parser {

    static public ParserResponseDto getParserData(String from, String to, String date, String trainNumber) throws IOException {
        ParserResponseDto dto = new ParserResponseDto();
        dto.setDate(date);
        dto.setFrom(from);
        dto.setTo(to);
        dto.setTrainNumber(trainNumber);
        String fromStationNumber = getStationNumberByStationName(from);
        String toStationNumber = getStationNumberByStationName(to);
        dto.setStations(getStationsList(fromStationNumber, trainNumber, reverseFormat(date)));
        ArrayList<WagonDto> wagons = new ArrayList<>();
        String time = getExactTime(fromStationNumber, toStationNumber, reverseFormat(date), trainNumber);
        String url = "https://bilet.railways.kz/sale/default/car/search?car_search_form%5BdepartureStation%5D=" + fromStationNumber + "&car_search_form%5BarrivalStation%5D=" + toStationNumber + "&car_search_form%5BforwardDirection%5D%5BdepartureTime%5D=" + date + "T" + time + "&car_search_form%5BforwardDirection%5D%5BfluentDeparture%5D=&car_search_form%5BforwardDirection%5D%5Btrain%5D=" + trainNumber + "&car_search_form%5BforwardDirection%5D%5BisObligativeElReg%5D=0&car_search_form%5BbackwardDirection%5D%5BdepartureTime%5D=&car_search_form%5BbackwardDirection%5D%5BfluentDeparture%5D=&car_search_form%5BbackwardDirection%5D%5Btrain%5D=&car_search_form%5BbackwardDirection%5D%5BisObligativeElReg%5D=";
        Document doc = Jsoup.connect(url).get();
        Elements els = doc.select("tr.title");
        for (int i = 0; i<els.size(); i++){
            WagonDto wagonDto = new WagonDto();
            wagonDto.setWagonNumber(els.get(i).select("td").get(0).text().substring(0, 2));
            wagonDto.setCarClass(els.get(i).select("td").get(1).text());
            wagonDto.setCarClassName(els.get(i).select("td").get(2).text());
            wagonDto.setTicketsRemaining(els.get(i).select("td").get(5).text());
            wagons.add(wagonDto);
        }
        dto.setWagons(wagons);
        return dto;
    }

    static public String reverseFormat(String date){
        String[] els = date.split("-");
        return els[2] + "-" + els[1] + "-" + els[0];
    }

    static public String getExactTime(String from, String to, String date, String trainNumber) throws IOException {
        String url = "https://bilet.railways.kz/sale/default/route/search?route_search_form%5BdepartureStation%5D=" + from + "&route_search_form%5BarrivalStation%5D=" + to + "&route_search_form%5BforwardDepartureDate%5D="+ date +"&route_search_form%5BbackwardDepartureDate%5D=";
        Document doc = Jsoup.connect(url).get();
        String el = doc.select("#forward-direction-trains > table > tbody > tr > td:nth-child(2) > h2").text();
        return el.substring(0, 5) + ":00";
    }

    static public String getStationNumberByStationName(String name) throws IOException {
        URL url = new URL("https://bilet.railways.kz/api/v1/ktj/station/search?q="+name);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        Scanner sc = new Scanner(responseStream);
        String json = sc.nextLine();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map
                = mapper.readValue(json, new TypeReference<Map<String,Object>>(){});
        ArrayList<Object> arr = (ArrayList<Object>) map.get("results");
        String result = null;
        for (int i=0; i<arr.size(); i++){
            if (((LinkedHashMap<String,String>) arr.get(i)).get("name").equals(name)){
                result = ((LinkedHashMap<String,String>) arr.get(i)).get("value");
            }
        }
        return result;
    }

    static public ArrayList<String> getStationsList(String stationNumber, String trainNumber, String date) throws IOException {
        URL url = new URL("https://bilet.railways.kz/api/v1/ktj/train/route/html");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        http.setDoOutput(true);
        Map<String,String> arguments = new HashMap<>();
        arguments.put("trainNumber", trainNumber);
        arguments.put("date", date);
        arguments.put("station", stationNumber);
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.setRequestProperty("accept", "text/html");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        Scanner sc = new Scanner(http.getInputStream());
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        int i = 0;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (i!=0) {
                i++;
                if (i==4) {
                    times.add(line);
                    i=0;
                }
            }
            if (line.contains("<td><strong class=\"mobile only\">Станция</strong>")){
                Document doc = Jsoup.parse(line);
                result.add(doc.text().substring(7));
                i++;
            }
        }
        if (times.get(0).equals(times.get(1)))
            result.remove(0);
        return result;
    }

}

