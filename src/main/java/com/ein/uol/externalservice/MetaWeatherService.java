package com.ein.uol.externalservice;

import com.ein.uol.model.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Component
public class MetaWeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(MetaWeatherService.class);

    public String getWOEId(String latitudeByIp, String longitudeByIp) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(
                "https://www.metaweather.com/api/location/search/?lattlong=" + latitudeByIp + "," + longitudeByIp);
        request.setHeader("Contenty-Type", String.valueOf(MediaType.APPLICATION_JSON_UTF8));

        HttpResponse responseExternal = client.execute(request);
        String response = EntityUtils.toString(responseExternal.getEntity());

        int codeHttp = responseExternal.getStatusLine().getStatusCode();

        if (codeHttp == 400) {
            LOG.error("400 Bad Request");
            return null;
        }

        if (codeHttp == 401) {
            LOG.error("401 Unauthorized");
            return null;
        }

        if (codeHttp == 403) {
            LOG.error("403 Forbidden");
            return null;
        }

        if (codeHttp == 404) {
            LOG.error("404 Not Found");
            return null;
        }

        if (codeHttp >= 500) {
            LOG.error("500 Internal Server Error");
            return null;
        }

        if (codeHttp == 200) {

            Location location = new Location();
            List<Location> locationList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> listMapMetaWeather = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>() {
            });

            for (Map<String, Object> mapMetaWeather : listMapMetaWeather) {
                location.setDistance((String) mapMetaWeather.get("distance"));
                location.setWoeid((String) mapMetaWeather.get("woeid"));
                location.setTitle((String) mapMetaWeather.get("title"));
                locationList.add(location);
            }
            return locationList.get(0).getWoeid();
        } else {
            LOG.error("500 Internal Server Error");
            return null;
        }

    }


    public Location getWeather(String woeid) throws IOException {

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(
                "https://www.metaweather.com/api/location/" + woeid + "/" + year + "/" + month + "/" + day);
        request.setHeader("Contenty-Type", String.valueOf(MediaType.APPLICATION_JSON_UTF8));

        HttpResponse responseExternal = client.execute(request);
        String response = EntityUtils.toString(responseExternal.getEntity());

        int codeHttp = responseExternal.getStatusLine().getStatusCode();

        if (codeHttp == 400) {
            LOG.error("400 Bad Request");
            return null;
        }

        if (codeHttp == 401) {
            LOG.error("401 Unauthorized");
            return null;
        }

        if (codeHttp == 403) {
            LOG.error("403 Forbidden");
            return null;
        }

        if (codeHttp == 404) {
            LOG.error("404 Not Found");
            return null;
        }

        if (codeHttp >= 500) {
            LOG.error("500 Internal Server Error");
            return null;
        }

        if (codeHttp == 200) {

            Location location = new Location();
            List<Location> locationList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> listMapMetaWeather = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>() {
            });

//            location.setMin_temp((String) listMapMetaWeather.get(0).get("min_temp"));
//            location.setMin_temp((String) listMapMetaWeather.get(0).get("max_temp"));
            for (Map<String, Object> mapMetaWeather : listMapMetaWeather) {
                location.setMin_temp((String) mapMetaWeather.get("min_temp"));
                location.setMax_temp((String) mapMetaWeather.get("max_temp"));
                locationList.add(location);
            }
            return locationList.get(0);
        } else {
            LOG.error("500 Internal Server Error");
            return null;
        }

    }
}

