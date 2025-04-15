package service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import service.core.Offer;
import service.core.ClientInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/offers")
public class BrokerController {

    // Store offers in a map by unique ID
    private final Map<String, Offer> offers = new ConcurrentHashMap<>();

    // List of quotation service URLs (can be modified later)
    private static final List<String> QUOTATION_SERVICES_URLS = List.of(
        "http://localhost:8082/quotations",  // Quotation service URL for dodgygeezer
        "http://localhost:8083/quotations"   // Quotation service URL for girlsallowed
    );

    // POST  - Create an offer by contacting multiple quotation services
    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody ClientInfo clientInfo) {
        Offer offer = new Offer(clientInfo);  // Create the offer with the provided client info

        // Create a RestTemplate to send requests to the quotation services
        RestTemplate restTemplate = new RestTemplate();

        // List to store URLs of the generated quotations
        ArrayList<String> quotationUrls = new ArrayList<>();  


        // Contact each quotation service and collect their URLs
        for (String url : QUOTATION_SERVICES_URLS) {
            try {
                // Send a POST request to each quotation service to create a quotation
                ResponseEntity<String> response = restTemplate.postForEntity(url, clientInfo, String.class);
                if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                    // Extract the URL of the newly created quotation
                    String location = response.getHeaders().getLocation().toString();
                    quotationUrls.add(location);  // Add the URL to the list
                }
            } catch (Exception e) {
                e.printStackTrace();  // Handle any exceptions that occur
            }
        }

        offer.setQuotations(quotationUrls);  // Set the collected quotation URLs in the offer

        String id = Integer.toString(offer.getId());  // Convert ID to String (for response)

        // Store the offer in the map
        offers.put(id, offer);

        // Return the offer with status CREATED (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(offer);
    }

    // GET - Get all offer IDs
    @GetMapping
    public ResponseEntity<List<String>> getOffers() {
        List<String> offerIds = new ArrayList<>(offers.keySet());
        return ResponseEntity.ok(offerIds);  // Return all offer IDs
    }

    // GET /offers/{id} - Get a specific offer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable String id) {
        Offer offer = offers.get(id);  // Retrieve the offer by its ID
        if (offer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Return 404 if not found
        }
        return ResponseEntity.ok(offer);  // Return the offer with status OK
    }
}
