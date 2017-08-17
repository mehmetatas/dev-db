package net.mehmetatas.devdb.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Component
public class DevDbClient {
    private int port;

    private final String accountId = UUID.randomUUID().toString();

    public void setPort(int port) {
        this.port = port;
    }

    private <TResponse> ResponseEntity<TResponse> send(HttpMethod method, String path, Map data, Class<TResponse> responseClass) {
        RestTemplate rest = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-DevDb-AccountId", accountId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map> entity = new HttpEntity<>(data, headers);

        rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        return rest.exchange("http://localhost:" + port + "/" + path, method, entity, responseClass);
        //return rest.exchange("https://api.dev-db.com/" + path, method, entity, responseClass);
    }

    public String insert(String table, Map item) {
        return send(HttpMethod.POST, table, item, String.class)
                .getHeaders()
                .get("X-DevDb-Id")
                .get(0);
    }

    public void update(String table, Map item) {
        send(HttpMethod.PUT, table + "/" + item.get("id"), item, null);
    }

    public void patch(String table, Map item) {
        send(HttpMethod.PATCH, table + "/" + item.get("id"), item, null);
    }

    public void deleteTable(String table) {
        send(HttpMethod.DELETE, table, null, null);
    }

    public void delete(String table, String id) {
        send(HttpMethod.DELETE, table + "/" + id, null, null);
    }

    public Map get(String table, String id) {
        return send(HttpMethod.GET, table + "/" + id, null, Map.class)
                .getBody();
    }

    public Map search(String table, String filter, int pageIndex, int pageSize, String orderby) {
        try {
            return send(HttpMethod.GET,
                    String.format("%s?filter=%s&pageIndex=%s&pageSize=%s&orderby=%s",
                            table,
                            URLEncoder.encode(filter, "utf-8"),
                            pageIndex,
                            pageSize,
                            URLEncoder.encode(orderby, "utf-8")), null, Map.class)
                    .getBody();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
