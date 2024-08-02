package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    Map<String, Customer> customers = new HashMap<>();


    public boolean registerCustomer(String customerName, String password) {
        if (customers.containsKey(customerName)) {
            return false;
        }

        customers.put(customerName, new Customer(customerName, password));
        return true;
    }

    public boolean loginCustomer(String customerName, String password) {
        Customer customer = customers.get(customerName);

        if (customer == null) {
            return false;
        }

        return customer.getPassword().equals(password);
    }
}
