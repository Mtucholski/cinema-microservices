package com.mtucholski.cinema.api.applications;

import com.mtucholski.cinema.api.dto.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerServiceClient {

     private final WebClient.Builder webClientBuilder;

     public Mono<CustomerDetails> getCustomer(final int customerId) {

          return webClientBuilder.build().get()
               .uri("http://customers-service/customers/{customerId}", customerId)
               .retrieve()
               .bodyToMono(CustomerDetails.class);
     }
}
