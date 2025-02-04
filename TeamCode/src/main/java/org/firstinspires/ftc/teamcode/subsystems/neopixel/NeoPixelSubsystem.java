package org.firstinspires.ftc.teamcode.subsystems.neopixel;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class NeoPixelSubsystem extends VLRSubsystem<NeoPixelSubsystem> implements NeoPixelConfiguration {

    private NeoPixelDriver neoPixel;
    double effectTime = 1;
    private Colour colour = Colour.RED;
    private Effect effect = Effect.SOLID_COLOR;

    @Override
    protected void initialize(HardwareMap hardwareMap) {
        neoPixel = hardwareMap.get(NeoPixelDriver.class, NeoPixelName);

    }

    public void setColor(Colour colour) {
        if (this.colour == colour) return;
        this.colour = colour;
        timer.reset();
    }

    public void setEffect(Effect effect) {
        if (this.effect == effect) return;
        this.effect = effect;
        timer.reset();
    }

    public void setEffectTime(double time)
    {
        if(this.effectTime == time)return;
        this.effectTime = time;
        timer.reset();
    }
    ElapsedTime timer = new ElapsedTime();


    @Override
    public void periodic() {
        switch (effect) {
            case SOLID_COLOR:
                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, colour.r, colour.g, colour.b);
                    System.out.println("linanguli");
                }
                break;
            case BREATHE:
                int realr, realg, realb;
                double sinreiksmekartvienasduastuonibe128 = (Math.sin(2 * Math.PI * timer.seconds()/ effectTime) + 1)/2.0;

                double sinR = colour.r * sinreiksmekartvienasduastuonibe128;
                double sinG = colour.g * sinreiksmekartvienasduastuonibe128;
                double sinB = colour.b * sinreiksmekartvienasduastuonibe128;

                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, (int) sinR, (int) sinG, (int) sinB);
                }
                break;
            case BLINK:
                boolean active = (timer.seconds() % effectTime) > (effectTime / 2);


                double r = colour.r * (active ? 1 : 0);
                double g = colour.g * (active ? 1 : 0);
                double b = colour.b * (active ? 1 : 0);

                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, (int) r, (int) g, (int) b);
                }
                break;
            default:
                break;
        }

        neoPixel.show();
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
