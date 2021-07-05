package com.mtucholski.cinema.api.dto;

import lombok.Data;

import java.util.*;

@Data
public class TicketDetails {

     private int id;
     private final List<FilmDetails> films = new ArrayList<>();
}
