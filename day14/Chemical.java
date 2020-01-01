package day14;

public class Chemical {
    private long quantity;
    private String name;

    Chemical(long quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    static Chemical parseChemical(String str) {
        String[] split = str.split(" ");
        return new Chemical(Integer.parseInt(split[0]), split[1]);
    }

    @Override
    public String toString() {
        return quantity + " " + name;
    }

    long getQuantity() {
        return quantity;
    }

    String getName() {
        return name;
    }

    void changeQuantity(long difference) {
        quantity += difference;
    }
}
