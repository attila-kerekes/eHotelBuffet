package com.codecool.ehotel.model;

public record Report(int wasteCosts, int disSatisfiedGuests) {
  public String dailyToString() {
    return "Daily report: " +
            "\u001b[33;1mWaste: " + wasteCosts +
            ", Dissatisfied guests :" + disSatisfiedGuests +
            "\u001b[0m";
  }
}
