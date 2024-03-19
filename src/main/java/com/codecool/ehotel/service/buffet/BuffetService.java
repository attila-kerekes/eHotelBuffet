package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

import java.nio.Buffer;
import java.util.Map;

public interface BuffetService {
    void refill(int currentCycle, Buffet buffet, Map<MealType, Integer> request);


    boolean consumeFreshest(Buffet buffet, MealType mealType);


    int collectWaste(Buffet buffet, int currentCycle);

}
