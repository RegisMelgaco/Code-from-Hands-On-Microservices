package com.microservices.chapter3

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class CustomerServiceImpl: CustomerService {

    companion object {
        val initialCustomers = arrayOf(
                Customer(1, "Aline"),
                Customer(2, "Bernardo"),
                Customer(3, "Carlos", Customer.Telephone("+55", "85988776655"))
        )
    }

    val customers = ConcurrentHashMap(initialCustomers.associateBy(Customer::id))

    override fun getCustomer(id: Int) = customers[id]

    override fun createCustomer(customer: Customer): Customer {
        customers[customer.id] = customer

        return customer
    }

    override fun deleteCustomer(id: Int): Boolean {
        val hasCustomer = customers.containsKey(id)
        customers.remove(id)

        return hasCustomer
    }

    override fun updateCustomer(id: Int, customer: Customer): Customer? {
        val hadCustomer = deleteCustomer(id)
        return if (hadCustomer) {
            createCustomer(customer)
        } else {
            null
        }
    }

    override fun searchCustomer(nameFilter: String) =
        customers.filter { it.value.name.contains(nameFilter, ignoreCase = true) }
                .map { it.value }
}