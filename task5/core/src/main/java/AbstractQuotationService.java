package service.core;

import java.util.Random;

public abstract class AbstractQuotationService {
    private int counter = 1000;
    private Random random = new Random();

    protected String generateReference(String prefix) {
        return prefix + String.format("%06d", counter++);
    }

    protected double generatePrice(double min, int range) {
        return min + random.nextInt(range);
    }

    public double bmi(double weight, double height) {
        return weight / (height * height);
    }
}
