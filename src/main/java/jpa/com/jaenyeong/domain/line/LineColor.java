package jpa.com.jaenyeong.domain.line;

import java.util.Arrays;

public enum LineColor {
    BLUE("blue"),
    GREEN("green"),
    ORANGE("orange"),
    SKY_BLUE("skyBlue"),
    PURPLE("purple"),
    BROWN("brown"),
    DARK_GREEN("darkGreen"),
    PINK("pink"),
    YELLOW("yellow");

    private final String colorName;

    LineColor(final String colorName) {
        this.colorName = colorName;
    }

    public static LineColor getColor(final String colorName) {
        return Arrays.stream(values())
            .filter(color -> color.colorName.equalsIgnoreCase(colorName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("this line color name is not valid"));
    }

    public String getColorName() {
        return colorName;
    }
}
