package refactored;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    List<Order> findAll();

    Order findById(String id);
}

class FileOrderRepository implements OrderRepository {
    private List<Order> orders = new java.util.ArrayList<>();

    @Override
    public void save(Order order) {
        orders.add(order);
        // También guardar en archivo
        try (var fw = new java.io.FileWriter("orders.txt", true)) {
            fw.write(order.getId() + "," + order.getCustomer() +
                    "," + order.getTotal() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public Order findById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElse(null);
    }
}
