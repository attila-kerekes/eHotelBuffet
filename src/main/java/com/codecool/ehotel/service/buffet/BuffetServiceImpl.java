package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.Food;
import com.codecool.ehotel.model.MealType;

import java.util.Map;

public class BuffetServiceImpl implements BuffetService {

    @Override
    public void refill(int currentCycle, Buffet buffet, Map<MealType, Integer> request) {
        for (Map.Entry<MealType, Integer> entry : request.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Food food = new Food(entry.getKey(), currentCycle);
                buffet.addFood(food);
            }
        }
    }

    @Override
    public boolean consumeFreshest(Buffet buffet, MealType mealType) {

        return buffet.removeFood(mealType);
    }

    @Override
    public int collectWaste(Buffet buffet, int currentCycle) {
        return buffet.removeExpiredFoods(currentCycle);
    }

}

