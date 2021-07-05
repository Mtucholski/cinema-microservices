package com.mtucholski.cinema.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
public class CustomerDetails {

     private int id;
     private String firstName;
     private String lastName;
     private String address;
     private String city;
     private String email;
     private String telephone;

     private final List<ReservationDetails> reservations = new ArrayList<>();

     @JsonIgnore
     public List<Integer> getReservationIds(){

          return reservations.stream().map(ReservationDetails::getId).collect(Collectors.toList());
     }
}
