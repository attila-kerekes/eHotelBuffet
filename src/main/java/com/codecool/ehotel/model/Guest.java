package com.codecool.ehotel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Guest(String name, MealType mealType, LocalDate checkIn, LocalDate checkOut) {

}
