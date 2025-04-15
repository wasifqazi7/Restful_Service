package service.auldfellas;

import service.core.ClientInfo;
import service.core.Quotation;
import service.core.AbstractQuotationService;

/**
 * AuldFellas Quotation Service for JMS.
 */
public class AFQService extends AbstractQuotationService {
    public static final String PREFIX = "AF";
    public static final String COMPANY = "Auld Fellas Ltd.";

    public Quotation generateQuotation(ClientInfo info) {
        double price = generatePrice(600, 600);

        int discount = 0;
        if (info.getGender() == ClientInfo.MALE) {
            discount = 30;
            if (info.getAge() > 50) discount += 10;
            if (info.getAge() > 60) discount += 10;
        } else {
            discount = -20;
        }

        return new Quotation(COMPANY, generateReference(PREFIX), (price * (100 - discount)) / 100);
    }
}
