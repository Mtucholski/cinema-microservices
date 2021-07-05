package com.mtucholski.cinema.api.dto;

import lombok.Data;

@Data
public class FilmDetails {

     private int id;
     private String title;
     private String mainActor;
     private String movieType;
     private String director;
}
