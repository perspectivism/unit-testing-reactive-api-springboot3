package org.springframework.samples.customer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.samples.customer.models.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public Flux<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable long id) {
        return customerRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND)));
    }

    @PutMapping
    public Mono<Customer> putCustomer(@RequestBody @Validated Customer customer) {
        return customerRepository.existsById(customer.getId())
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
                }

                return customerRepository.save(customer);
        });
    }

    @PostMapping
    public Mono<Customer> postCustomer(@RequestBody @Validated Customer customer) {
        if (customer.getId() != null) {
            return Mono.error(new ResponseStatusException(
                HttpStatus.BAD_REQUEST));
        }

        return customerRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable long id) {
        return customerRepository.deleteById(id);
    }
}
