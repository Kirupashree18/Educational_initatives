// Component
interface Coffee {
    String getDescription();
    double cost();
}

// Concrete Component
class SimpleCoffee implements Coffee {
    public String getDescription() { return "Simple Coffee"; }
    public double cost() { return 5.0; }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    public CoffeeDecorator(Coffee c) { this.coffee = c; }
    public String getDescription() { return coffee.getDescription(); }
    public double cost() { return coffee.cost(); }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee c) { super(c); }
    public String getDescription() { return coffee.getDescription() + ", Milk"; }
    public double cost() { return coffee.cost() + 1.5; }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee c) { super(c); }
    public String getDescription() { return coffee.getDescription() + ", Sugar"; }
    public double cost() { return coffee.cost() + 0.5; }
}

// Demo
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.cost());

        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.cost());
    }
}
