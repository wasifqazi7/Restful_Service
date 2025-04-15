package service.girlsallowed;

import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

public class GAQService extends AbstractQuotationService {
    public static final String PREFIX = "GA";
    public static final String COMPANY = "Girls Allowed Inc.";

    public Quotation generateQuotation(ClientInfo info) {
        double price = generatePrice(600, 400); // base price: 600–1000

        int discount = (info.getGender() == ClientInfo.FEMALE) ? 50 : -30;
        discount += calculateBMIDiscount(info);
        discount += calculateMedicalWeighting(info);

        double finalPrice = (price * (100 - discount)) / 100;
        return new Quotation(COMPANY, generateReference(PREFIX), finalPrice);
    }

    private int calculateBMIDiscount(ClientInfo info) {
        double bmi = info.getWeight() / (info.getHeight() * info.getHeight());
        if (bmi < 18.5) return 20;
        if (bmi < 24.5) return 10;
        if (bmi < 30) return 0;
        if (bmi < 40) return -20;
        return -40;
    }

    private int calculateMedicalWeighting(ClientInfo info) {
        int penalty = 0;
        if (info.isMedicalIssues()) penalty -= 20;
        if (info.isSmoker()) penalty -= 40;
        return penalty;
    }
}
