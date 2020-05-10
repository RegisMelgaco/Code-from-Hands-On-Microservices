package com.microservices.chapter3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
class CustomerController {

    @Autowired
    private lateinit var customerService: CustomerService

    @RequestMapping("/customer/{id}", method = [RequestMethod.GET])
    fun getCustomer(@PathVariable id: Int): ResponseEntity<Customer> {
        val customer = customerService.getCustomer(id)
        val status = if (customer != null) HttpStatus.OK else HttpStatus.NOT_FOUND

        return ResponseEntity(customer, status)
    }

    @RequestMapping("/customers", method = [RequestMethod.GET])
    fun getCustomers(
            @RequestParam(required = false, defaultValue = "")
            nameFilter: String
    ) = customerService.searchCustomer(nameFilter)

    @RequestMapping("/customer/", method = [RequestMethod.POST])
    fun createCustomer(@RequestBody customer: Customer) = ResponseEntity(
            customerService.createCustomer(customer),
            HttpStatus.CREATED
    )

    @RequestMapping("/customer/{id}", method = [RequestMethod.DELETE])
    fun deleteCustomer(@PathVariable id: Int): ResponseEntity<Unit> {
        val hadCustomer = customerService.deleteCustomer(id)
        val status = if (hadCustomer) HttpStatus.OK else HttpStatus.NOT_FOUND

        return ResponseEntity(Unit, status)
    }

    @RequestMapping("/customer/{id}", method = [RequestMethod.PUT])
    fun updateCustomer(@PathVariable id: Int, @RequestBody customer: Customer): ResponseEntity<Customer> {
        val newCustomer = customerService.updateCustomer(id, customer)
        val status = if (newCustomer != null) HttpStatus.ACCEPTED else HttpStatus.NOT_FOUND

        return ResponseEntity(newCustomer, status)
    }
}