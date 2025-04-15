package service.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import service.dodgygeezers.DGQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.dodgygeezers.DGQService;

@RestController
public class DodgyGeezerController {

    private DGQService service = new DGQService();
    private Map<String, Quotation> quotations = new TreeMap<>(); // Declare quotations map

    @PostMapping(value = "/quotations", consumes = "application/json")
    public ResponseEntity<Quotation> createQuotation(@RequestBody ClientInfo info) {
        // Generate the new quotation using the service
        Quotation quotation = service.generateQuotation(info);

        // Store the quotation in the map
        quotations.put(quotation.getReference(), quotation);

        // Create the URL for the new quotation
        String url = "http://" + getHost() + "/quotations/" + quotation.getReference();

        // Return the response with status 201 (Created) and the Location header
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", url)
                .header("Content-Location", url)
                .body(quotation);
    }

    @GetMapping(value = "/quotations", produces = "application/json")
    public ResponseEntity<ArrayList<String>> getQuotations() {
        // Create a list to hold all the quotation URLs
        ArrayList<String> list = new ArrayList<>();
        
        // Iterate through all the quotations stored in the 'quotations' map
        for (Quotation quotation : quotations.values()) {
            // Add the full URL for each quotation using its reference
            list.add("http://" + getHost() + "/quotations/" + quotation.getReference());
        }

        // Return the list of quotation URLs with a 200 OK status
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // Utility method to get host address
    private String getHost() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            return host.getHostAddress(); // or host.getHostName() depending on your requirement
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "localhost"; // Fallback if host cannot be resolved
        }
    }
}
