package com.codecool.ehotel.service.manager;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.time.LocalDate;
import java.util.*;

public class BreakfastManager {

    private GuestService guestService;
    private BuffetService buffetService;
    private Buffet buffet;
    //private int waste;
    //private int dissatisfiedGuests;

    public BreakfastManager(GuestService guestService, BuffetService buffetService, Buffet buffet) {
        this.guestService = guestService;
        this.buffetService = buffetService;
        this.buffet = buffet;
        //this.waste = 0;
        //this.dissatisfiedGuests = 0;
    }


    public Report serve(List<Guest> guestList, LocalDate date) {

    int waste = 0;
    int dissatisfiedGuests = 0;
        Set<Guest> guests = guestService.getGuestsForDay(guestList, date);
        List<Set<Guest>> breakfastCycles = guestService.getGuestsForCycle(guests);

        for (int i = 0; i < breakfastCycles.size(); i++) {

            buffetService.refill(i, buffet, mealGenerator(breakfastCycles.get(i).size()));
            dissatisfiedGuests += consumeFoods(breakfastCycles.get(i), dissatisfiedGuests);
            waste += sumCostOfWaste(i);
        }
        return new Report(waste, dissatisfiedGuests);
    }

    public Map<MealType, Integer> mealGenerator(int numberOfGuests) {
        Map<MealType, Integer> map = new HashMap<>();
        MealType[] mealTypeValues = MealType.values();

        for (MealType mealTypeValue : mealTypeValues) {
            map.put(mealTypeValue, numberOfGuests / mealTypeValues.length);
        }

        return map;
    }

    private int consumeFoods(Set<Guest> guests, int dissatisfiedGuests) {
        for (Guest guest : guests) {
            if (!buffetService.consumeFreshest(buffet, guest.mealType())) {
                dissatisfiedGuests++;
            }
        }
        return dissatisfiedGuests;
    }

    private int sumCostOfWaste(int i) {
        return buffetService.collectWaste(buffet, i);
    }
}
