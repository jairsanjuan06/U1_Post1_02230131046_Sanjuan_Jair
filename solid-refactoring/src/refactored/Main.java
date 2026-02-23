package refactored;

public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new FileOrderRepository();
        NotificationService notifier = new EmailNotificationService();

        // OCP
        DiscountStrategy discountStrategy = new StandardDiscount();

        OrderService orderService = new OrderService(repository, notifier, discountStrategy);

        System.out.println("------------------------");

        Order order1 = orderService.createOrder("Jair Sanjuan", "Laptop", 1200.0, 1);
        System.out.println("Detalles: " + order1.getId() + " | Total final: $" + order1.getTotal() + "\n");

        Order order2 = orderService.createOrder("Maria Lopez", "Servidor", 3000.0, 2);
        System.out.println("Detalles: " + order2.getId() + " | Total final: $" + order2.getTotal() + "\n");

        System.out.println("--- VERIFICANDO PERSISTENCIA EN MEMORIA ---");
        System.out.println("Total de órdenes guardadas: " + repository.findAll().size());
    }
}
