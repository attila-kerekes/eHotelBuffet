package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.model.NameOptions;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class GuestServiceImpl implements GuestService {

    private Random random = new Random();

    @Override
    public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {

        NameOptions[] nameValues = NameOptions.values();
        int randomNameIndex = random.nextInt(nameValues.length);
        NameOptions randomNameOptionsValue = nameValues[randomNameIndex];

        GuestType[] guestTypeValues = GuestType.values();
        int randomGuestTypeIndex = random.nextInt(guestTypeValues.length);
        GuestType randomGuestTypeValue = guestTypeValues[randomGuestTypeIndex];
        List<MealType> preferredFoods = randomGuestTypeValue.getMealPreferences();
        MealType favouriteFood = preferredFoods.get(random.nextInt(preferredFoods.size()));

        Period period = Period.between(seasonStart, seasonEnd);
        int periodDays = period.getDays();

        LocalDate checkIn = seasonStart.plusDays(random.nextInt(periodDays));
        LocalDate checkOut;

        do {
            checkOut = checkIn.plusDays(1 + random.nextInt(6));
        }
        while (checkOut.isAfter(seasonEnd));

        Guest guest = new Guest(randomNameOptionsValue.name(), favouriteFood, checkIn, checkOut);

        return guest;
    }

    @Override
    public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {

        Set<Guest> guestForTheDay = new HashSet<>();

        for (Guest guest : guests) {
            if (guest.checkIn().isBefore(date) && guest.checkOut().isAfter(date)) {
                guestForTheDay.add(guest);
            }
        }

        return guestForTheDay;
    }

    public List<Set<Guest>> getGuestsForCycle(Set<Guest> guestsForTheDay) {
        List<Set<Guest>> breakfastCycles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            breakfastCycles.add(new HashSet<>());
        }

        for (Guest guest : guestsForTheDay) {
            breakfastCycles.get(random.nextInt(8)).add(guest);
        }
        return breakfastCycles;
    }


}
