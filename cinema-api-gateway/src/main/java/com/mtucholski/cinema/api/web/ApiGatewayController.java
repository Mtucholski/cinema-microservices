package com.mtucholski.cinema.api.web;

import com.mtucholski.cinema.api.applications.CustomerServiceClient;
import com.mtucholski.cinema.api.applications.ReservationServiceClient;
import com.mtucholski.cinema.api.dto.CustomerDetails;
import com.mtucholski.cinema.api.dto.Reservations;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/cinema-gateway")
public class ApiGatewayController {

     private final CustomerServiceClient customerServiceClient;
     private final ReservationServiceClient reservationServiceClient;
     private final ReactiveCircuitBreakerFactory cbFactory;

     @GetMapping(value = "customers/{customerId}")
     public Mono<CustomerDetails> getCustomerDetails(final @PathVariable int customerId){

          return customerServiceClient.getCustomer(customerId).flatMap(customer ->
               reservationServiceClient.getReservationsForCustomers(customer.getReservationIds())
          .transform( it -> {
               ReactiveCircuitBreaker cb = cbFactory.create("getCustomerDetails");
               return cb.run(it, throwable -> emptyReservationsForCustomer());
          }).map(addReservationsToCustomer(customer))
          );
     }

     private Mono<Reservations> emptyReservationsForCustomer(){

          return Mono.just(new Reservations());
     }

     private Function<Reservations, CustomerDetails> addReservationsToCustomer(CustomerDetails customer) {
          return reservations -> {
               customer.getReservations()
                    .forEach(reservation -> reservation.getReservations()
                         .addAll(reservations.getItems().stream()
                              .filter(v -> v.getId() == reservation.getId())
                              .collect(Collectors.toList()))
                    );
               return customer;
          };
     }
}
