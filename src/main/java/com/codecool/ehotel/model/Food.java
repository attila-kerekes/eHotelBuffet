package com.codecool.ehotel.model;

public class Food {
    private MealType mealType;
    private int currentCycle;
    private int expiryCycle;


    public Food(MealType mealType, int currentCycle) {
        this.mealType = mealType;
        this.currentCycle = currentCycle;
        this.expiryCycle = mealType.getDurability(currentCycle);
    }

    public int getExpiryCycle() {
        return expiryCycle;
    }

    public MealType getMealType() {
        return mealType;
    }

    @Override
    public String toString() {
        return "Cycle: " + currentCycle + " ExpiryCycle: " + expiryCycle;
    }

}
