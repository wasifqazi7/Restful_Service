package service.core;

import java.util.ArrayList;

public class Offer {

    private static int COUNTER = 1000;
    private int id;
    private ClientInfo info;
    private ArrayList<String> quotations;

    public Offer(ClientInfo info) {
        this.id = COUNTER++;
        this.info = info;
        this.quotations = new ArrayList<>();
    }

    public Offer() {
        this.id = COUNTER++;
        this.quotations = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ClientInfo getInfo() {
        return info;
    }

    public void setInfo(ClientInfo info) {
        this.info = info;
    }

    public ArrayList<String> getQuotations() {
        return quotations;
    }

    public void setQuotations(ArrayList<String> quotations) {
        this.quotations = quotations;
    }

    public void addQuotation(String quotationUrl) {
        this.quotations.add(quotationUrl);
    }
}
