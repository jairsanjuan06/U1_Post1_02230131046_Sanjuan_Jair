package refactored;

public interface DiscountStrategy {
    double applyDiscount(double total);
}

class StandardDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        if (total > 5000)
            return total * 0.85;
        if (total > 1000)
            return total * 0.90;
        return total;
    }
}

class VIPDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total) {
        return total * 0.80; // 20% siempre
    }
}