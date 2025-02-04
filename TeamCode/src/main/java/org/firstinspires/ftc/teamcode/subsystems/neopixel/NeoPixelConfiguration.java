package org.firstinspires.ftc.teamcode.subsystems.neopixel;

public interface NeoPixelConfiguration {
    public int ledCount = 10;
    String NeoPixelName = "NeoPixel";

    public enum Effect
    {
        SOLID_COLOR,
        BREATHE,
        BLINK,
        CRAZYMODE;

    }

    public enum Colour {

        RED(255, 255,  255),
        GREEN(255, 255, 0),
        BLUE(0, 255, 255),
        YELLOW(255, 0, 255);
        public final int r, g, b;

        Colour(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

    }

}
