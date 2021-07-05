package com.mtucholski.cinema.api.dto;

import lombok.Data;
import java.util.*;
import java.time.Instant;

@Data
public class ReservationDetails {

     private int id;
     private Instant reservationDate;
     private final List<ReservationDetails> reservations = new ArrayList<>();

}
