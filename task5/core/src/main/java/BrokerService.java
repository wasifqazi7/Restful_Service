package service.core;

import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * Interface for defining the behaviours of the broker service.
 */
@WebService
public interface BrokerService {

    @WebMethod
    List<Quotation> getQuotations(ClientInfo info);
}
