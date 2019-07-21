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
import java.util.Map;

@Component
public class IpVililanteService {

    private static final Logger LOG = LoggerFactory.getLogger(IpVililanteService.class);


    public Location getIpVigilante(String ipFromRequestUser) throws IOException {


        HttpClient client = HttpClientBuilder.create().build();
        HttpGet requestIpVililante = new HttpGet("https://ipvigilante.com/" + ipFromRequestUser);
        requestIpVililante.setHeader("Contenty-Type", String.valueOf(MediaType.APPLICATION_JSON_UTF8));

        HttpResponse responseExternal = client.execute(requestIpVililante);
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
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> mapIp = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> mapData = (Map<String, Object>) mapIp.get("data");
            location.setLatitude((String) mapData.get("latitude"));
            location.setLongitude((String) mapData.get("longitude"));

            return location;

        } else {
            LOG.error("500 Internal Server Error");
            return null;
        }
    }
}