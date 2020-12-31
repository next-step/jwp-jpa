package jpa.line.domain;

public enum LineType {
    FIRST_LINE(LineColor.BLUE, "1호선"),
    SECOND_LINE(LineColor.GREEN, "2호선");

    private final LineColor color;
    private final String name;

    LineType(LineColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public LineColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
