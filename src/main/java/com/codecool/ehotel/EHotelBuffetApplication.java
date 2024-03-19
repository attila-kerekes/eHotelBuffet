package com.codecool.ehotel;

import com.codecool.ehotel.model.*;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;
import com.codecool.ehotel.service.manager.BreakfastManager;
import com.codecool.ehotel.ui.ConsoleLogger;
import com.codecool.ehotel.ui.Display;
import com.codecool.ehotel.ui.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class EHotelBuffetApplication {
    public static Logger logger = new ConsoleLogger();

    public static void main(String[] args) {
        Buffet buffet = new Buffet();
        BuffetService buffetService = new BuffetServiceImpl();
        GuestService guestService = new GuestServiceImpl();
        Display display = new Display();
        Report endSeasonReport = new Report(0, 0);
        BreakfastManager breakfastManager = new BreakfastManager(
                guestService, buffetService, buffet);

        display.printMessage("Welcome to the EHotelBuffet Application!");
        display.printMessage("Choose when you want your season to start!");
        LocalDate seasonStart = display.inputDate();
        display.printMessage("Choose when you want your season to end!");
        LocalDate seasonEnd = display.inputDate();

        //LocalDate chosenDate = LocalDate.of(2023, 11, 1);
        //LocalDate chosen2Date = LocalDate.of(2023, 11, 15);
        //List<Guest> guestList = generateGuests(guestService, chosenDate, chosen2Date);
        //simulation(chosenDate, chosen2Date, breakfastManager, guestList);

        List<Guest> guestList = generateGuests(guestService, seasonStart, seasonEnd);
        simulation(seasonStart, seasonEnd, breakfastManager, guestList);
    }

    private static List<Guest> generateGuests(GuestService guestService, LocalDate seasonStart, LocalDate seasonEnd) {
        List<Guest> guests = new ArrayList<>();
        int min = 500;
        int max = 1000;

        int nrOfGuests = (int) Math.floor(Math.random() * (max - min + 1) + min);

        for (int i = 0; i < nrOfGuests; i++) {
            Guest guest = guestService.generateRandomGuest(
                    seasonStart,
                    seasonEnd
            );
            guests.add(guest);
        }

        return guests;
    }

    private static void simulation(LocalDate start, LocalDate end, BreakfastManager breakfastManager, List<Guest> guestList) {
        int waste = 0;
        int disSatisfiedGuests = 0;
        Period period = Period.between(start, end);
        int periodDays = period.getDays();
        for (int i = 0; i < periodDays; i++) {
            LocalDate currentDay = start.plusDays(i);
            Report dailyReport = breakfastManager.serve(guestList, currentDay);
            logger.logInfo(dailyReport.dailyToString());
            waste += dailyReport.wasteCosts();
            disSatisfiedGuests += dailyReport.disSatisfiedGuests();
        }
        logger.logInfo("End season report : \n\u001b[33;1mWaste:" + String.valueOf(waste)
                + "\nDissatisfied guests: " + String.valueOf(disSatisfiedGuests));
    }
}
