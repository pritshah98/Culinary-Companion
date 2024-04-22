package com.ps.culinarycompanion.recipes;

public enum CookingUnit {
    TEASPOON("Teaspoon", "tsp"),
    TABLESPOON("Tablespoon", "tbsp"),
    CUP("Cup", "cup"),
    FLUID_OUNCE("Fluid Ounce", "fl oz"),
    PINT("Pint", "pt"),
    QUART("Quart", "qt"),
    GALLON("Gallon", "gal"),
    MILLILITER("Milliliter", "ml"),
    LITER("Liter", "l"),
    GRAM("Gram", "g"),
    KILOGRAM("Kilogram", "kg"),
    OUNCE("Ounce", "oz"),
    POUND("Pound", "lb"),
    COUNT("Count", "ct");

    private final String fullName;
    private final String abbreviation;

    CookingUnit(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
