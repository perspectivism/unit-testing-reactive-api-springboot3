package org.springframework.samples.customer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.customer.models.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureWebTestClient
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    public void setup() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("John Doe");
    }

    @Test
    public void getAllCustomersTest() {
        given(customerRepository.findAll()).willReturn(Flux.just(testCustomer));

        webTestClient.get()
            .uri("/api/customers")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(APPLICATION_JSON)
            .expectBodyList(Customer.class);
    }

    @Test
    public void getCustomerTest() {
        given(customerRepository.findById(1L)).willReturn(Mono.just(testCustomer));

        webTestClient.get()
            .uri("/api/customers/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.name").isEqualTo("John Doe");
    }

    @Test
    public void getCustomerNotFoundTest() {
        given(customerRepository.findById(1L)).willReturn(Mono.empty());

        webTestClient.get()
            .uri("/api/customers/1")
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void putCustomerTest() {
        given(customerRepository.existsById(1L)).willReturn(Mono.just(true));
        
        given(customerRepository.save(ArgumentMatchers.any(Customer.class)))
            .willReturn(Mono.just(testCustomer));

        webTestClient.put()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void putCustomerNameIsNullTest() {
        testCustomer.setName(null);

        webTestClient.put()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void putCustomerNameTooLongTest() {
        testCustomer.setName(String.format("%256s", "J").replace(' ', 'J'));

        webTestClient.put()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void putCustomerNameTooShortTest() {
        testCustomer.setName("Jo");

        webTestClient.put()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }    

    @Test
    public void putCustomerNotFoundTest() {
        given(customerRepository.existsById(1L)).willReturn(Mono.just(false));

        webTestClient.put()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void postCustomerTest() {
        testCustomer.setId(null); // new customers don't initially have ids
        given(customerRepository.save(testCustomer)).willReturn(Mono.just(testCustomer));

        webTestClient.post()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void postCustomerBadIdTest() {
        testCustomer.setId(1L); // this id should be null

        webTestClient.post()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void postCustomerNameIsNullTest() {
        testCustomer.setId(null);
        testCustomer.setName(null);

        webTestClient.post()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }        

    @Test
    public void postCustomerNameTooLongTest() {
        testCustomer.setId(null);
        testCustomer.setName(String.format("%256s", "J").replace(' ', 'J'));

        webTestClient.post()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }    

    @Test
    public void postCustomerNameTooShortTest() {
        testCustomer.setId(null);
        testCustomer.setName("Jo");

        webTestClient.post()
            .uri("/api/customers")
            .contentType(APPLICATION_JSON)
            .body(Mono.just(testCustomer), Customer.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    public void deleteCustomerTest() {
        given(customerRepository.deleteById(1L)).willReturn(Mono.empty());

        webTestClient.delete()
            .uri("/api/customers/1")
            .exchange()
            .expectStatus().isOk();
    }    
}
