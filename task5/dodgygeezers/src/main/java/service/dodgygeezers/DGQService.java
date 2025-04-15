package service.dodgygeezers;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

public class DGQService extends AbstractQuotationService {
    public static final String PREFIX = "DG";
    public static final String COMPANY = "Dodgy Geezers Corp.";

    public Quotation generateQuotation(ClientInfo info) {
        double price = generatePrice(800, 200); // Base price range: 800–1000
        int discount = calculateBMIDiscount(info);

        if (info.isSmoker()) {
            discount += 10; // Penalty for smokers
        }

        double finalPrice = (price * (100 - discount)) / 100;
        return new Quotation(COMPANY, generateReference(PREFIX), finalPrice);
    }

    private int calculateBMIDiscount(ClientInfo info) {
        double bmi = info.getWeight() / (info.getHeight() * info.getHeight());

        if (bmi < 18.5) return 0;     // Underweight
        if (bmi < 24.5) return 5;     // Healthy
        if (bmi < 30) return 10;      // Overweight
        if (bmi < 40) return 15;      // Obese
        return 20;                   // Morbidly Obese
    }
}
