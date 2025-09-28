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
