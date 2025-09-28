// Target
interface TypeCCharger {
    void chargePhone();
}

// Adaptee
class MicroUSBCharger {
    public void chargeWithMicroUSB() {
        System.out.println("Charging phone with Micro-USB.");
    }
}

// Adapter
class ChargerAdapter implements TypeCCharger {
    private MicroUSBCharger microUSBCharger;

    public ChargerAdapter(MicroUSBCharger charger) {
        this.microUSBCharger = charger;
    }

    public void chargePhone() {
        microUSBCharger.chargeWithMicroUSB();
    }
}

// Demo
public class AdapterPatternDemo {
    public static void main(String[] args) {
        MicroUSBCharger oldCharger = new MicroUSBCharger();
        TypeCCharger adapter = new ChargerAdapter(oldCharger);

        adapter.chargePhone(); // Works via adapter
    }
}
