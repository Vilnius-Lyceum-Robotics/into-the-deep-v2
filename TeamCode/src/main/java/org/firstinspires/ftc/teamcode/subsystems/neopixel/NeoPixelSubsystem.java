package org.firstinspires.ftc.teamcode.subsystems.neopixel;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class NeoPixelSubsystem extends VLRSubsystem<NeoPixelSubsystem> implements NeoPixelConfiguration {

    private NeoPixelDriver neoPixel;
    private Colour colour = Colour.RED;
    private Effect effect = Effect.SOLID_COLOR;

    @Override
    protected void initialize(HardwareMap hardwareMap) {
        neoPixel = hardwareMap.get(NeoPixelDriver.class, NeoPixelName);

    }

    public void setColor(Colour colour) {
        this.colour = colour;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }


    @Override
    public void periodic() {
        switch (effect) {
            case SOLID_COLOR:
                for (int i = 1; i < NeoPixelConfiguration.ledCount; i++) {
                    neoPixel.setColor(i, colour.r, colour.g, colour.b);
                    System.out.println("linanguli");
                }
                break;
            default:
                break;
        }

        neoPixel.show();
    }


}
