package com.mtucholski.cinema.api.dto;

import lombok.Value;
import java.util.*;
import java.util.ArrayList;

@Value
public class Reservations {

     List<ReservationDetails> items = new ArrayList<>();
}
