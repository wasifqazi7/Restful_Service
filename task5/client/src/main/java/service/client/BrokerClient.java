package service.client;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.core.ClientInfo;

public class BrokerClient {

    public static void main(String[] args) throws IOException {
        // Create ObjectMapper for Jackson serialization
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("John Doe", 'M', 30, 1.75, 70, false, false);

        // Convert ClientInfo object to JSON
        String jsonClientInfo = objectMapper.writeValueAsString(clientInfo);

        // Broker service URL
        String brokerServiceUrl = "http://localhost:8080/offers";

        // Send POST request to broker service
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost postRequest = new HttpPost(brokerServiceUrl);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(jsonClientInfo));  // Set JSON in the body

            // Execute the request and handle the response
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_CREATED) {
                    System.out.println("Offer created successfully!");
                } else {
                    System.out.println("Failed to create offer. Status code: " + statusCode);
                }
            }
        }
    }
}
