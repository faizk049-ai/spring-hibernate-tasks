package com.OnlineFoodOrderManagement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class MainApp {

    public static void main(String[] args) {

        // Step 1-3: Load configuration and create SessionFactory
        Configuration configuration = new Configuration();
        configuration.configure("hibernateconfiguration.cfg.xml");

        SessionFactory factory = configuration.buildSessionFactory();

        try {
            // Step 4-5: Create and populate the food order
            OnlineFoodOrder order = new OnlineFoodOrder();

            order.setOrderId(1001L);
            order.setCustomerName("Faiz Khan");
            order.setCustomerPhone("9876543210");
            order.setFoodName("Chicken Biryani");
            order.setQuantity(2);
            order.setOrderDate(LocalDate.of(2026, 10, 10));
            order.setRestaurantName("Hyderabad Spice House");
            order.setPaymentMethod("UPI");
            order.setDeliveryAddress("Madhapur, Hyderabad");
            order.setDeliveryPartner("Rahul");
            order.setOrderStatus("CONFIRMED");
            order.setTotalamount(598.00);

            // Step 6-10: Save the order
            Session session = factory.openSession();
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.persist(order);

                transaction.commit();

                System.out.println("Food order saved successfully!");

            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;

            } finally {
                session.close();
            }

            // Step 11-13: Retrieve the saved order
            Session readSession = factory.openSession();

            try {
                OnlineFoodOrder retrievedOrder =
                        readSession.get(OnlineFoodOrder.class, 1001L);

                System.out.println("\n----- Retrieved Food Order -----");

                if (retrievedOrder != null) {
                    System.out.println(retrievedOrder);
                } else {
                    System.out.println("Food order not found!");
                }

            } finally {
                readSession.close();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            factory.close();
        }
    }
}

