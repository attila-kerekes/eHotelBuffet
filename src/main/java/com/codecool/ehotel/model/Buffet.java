package com.codecool.ehotel.model;

import java.util.*;

public class Buffet {

    private Map<MealType, ArrayList<Food>> supplies;

    public Buffet() {
        supplies = new HashMap<>();
        MealType[] mealTypes = MealType.values();
        for (MealType mealType : mealTypes) {
            supplies.put(mealType, new ArrayList<>());
        }
    }

    public void addFood(Food food) {
        this.supplies.get(food.getMealType()).addFirst(food);
    }

    public boolean removeFood(MealType mealType) {
        int mealTypeCurrentStock = this.supplies.get(mealType).size();

        if (mealTypeCurrentStock == 0){
            return false;
        }
        this.supplies.get(mealType).remove(0);
        return true;
    }

    public int removeExpiredFoods(int currentCycle) {
        int lossInCurrentCycle = 0;

        for (Map.Entry<MealType, ArrayList<Food>> entry : supplies.entrySet()) {
            for (Food food : entry.getValue()) {
                if (food.getExpiryCycle() == currentCycle) {
                    lossInCurrentCycle += food.getMealType().getCost();
                }

            }
            entry.getValue().removeIf(food -> food.getExpiryCycle() == currentCycle);
        }
        return lossInCurrentCycle;
    }



    public void printState() {

        for (Map.Entry<MealType, ArrayList<Food>> entry : supplies.entrySet()) {
            System.out.println("From " + entry.getKey() + " I have the following foods: \n");
            for (Food food : entry.getValue()) {
                System.out.println(food.toString() + "\n");
            }
        }
    }
}
