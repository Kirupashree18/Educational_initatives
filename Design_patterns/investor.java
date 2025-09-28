import java.util.*;

// Observer
interface Investor {
    void update(String stock, double price);
}

// Concrete Observer
class MobileAppInvestor implements Investor {
    private String name;

    public MobileAppInvestor(String name) {
        this.name = name;
    }

    public void update(String stock, double price) {
        System.out.println(name + " notified: " + stock + " price updated to $" + price);
    }
}

// Subject
class Stock {
    private String name;
    private double price;
    private List<Investor> investors = new ArrayList<>();

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void addInvestor(Investor inv) {
        investors.add(inv);
    }

    public void setPrice(double price) {
        this.price = price;
        notifyAllInvestors();
    }

    private void notifyAllInvestors() {
        for (Investor inv : investors) {
            inv.update(name, price);
        }
    }
}
