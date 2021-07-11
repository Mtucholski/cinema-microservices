package com.mtucholski.cinema.api.applications;

import com.mtucholski.cinema.api.dto.Reservations;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReservationServiceClient {

     private String hostname = "http://reservations-service/";
     private final WebClient.Builder webBuilder;

     public Mono<Reservations> getReservationsForCustomers(final List<Integer> customersIds) {

          return webBuilder.build()
               .get()
               .uri(hostname + "customers/reservations?customerId={customerId}", joinIds(customersIds))
               .retrieve()
               .bodyToMono(Reservations.class);
     }

     private String joinIds(List<Integer> customerIds) {

          return customerIds.stream().map(Objects::toString).collect(Collectors.joining(","));
     }

     void setHostname(String hostname) {
          this.hostname = hostname;
     }
}
