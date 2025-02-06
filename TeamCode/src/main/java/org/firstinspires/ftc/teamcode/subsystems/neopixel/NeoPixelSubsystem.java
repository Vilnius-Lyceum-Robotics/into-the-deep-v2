package org.firstinspires.ftc.teamcode.subsystems.neopixel;

import static com.arcrobotics.ftclib.util.MathUtils.clamp;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class NeoPixelSubsystem extends VLRSubsystem<NeoPixelSubsystem> implements NeoPixelConfiguration {

    private NeoPixelDriver neoPixel;
    double effectTime = 1;
    private Colour colour = Colour.RED;
    private Effect effect = Effect.SOLID_COLOR;
    public double brightness = 1.0;

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

    public void setEffectTime(double time) {
        if (this.effectTime == time) return;
        this.effectTime = time;
        timer.reset();
    }

    public void setBrightness(double brightness)
    {
        this.brightness=brightness;
    }

    ElapsedTime timer = new ElapsedTime();


    @Override
    public void periodic() {
        switch (effect) {
            case SOLID_COLOR:
                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, (int) (brightness * colour.r), (int) (brightness * colour.g), (int) (brightness * colour.b));
                    //System.out.println("linanguli");
                }
                break;
            case BREATHE:
                double multiplier = (Math.sin(2 * Math.PI * timer.seconds() / effectTime) + 1) / 2.0;

                double sinR = brightness * colour.r * multiplier;
                double sinG = brightness * colour.g * multiplier;
                double sinB = brightness * colour.b * multiplier;

                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, (int) sinR, (int) sinG, (int) sinB);
                }
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case BLINK:
                boolean active = (timer.seconds() % effectTime) > (effectTime / 2);


                double r = brightness * colour.r * (active ? 1 : 0);
                double g = brightness * colour.g * (active ? 1 : 0);
                double b = brightness * colour.b * (active ? 1 : 0);

                for (int i = 1; i < NeoPixelConfiguration.ledCount + 1; i++) {
                    neoPixel.setColor(i, (int) r, (int) g, (int) b);
                }

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case CHASE_FORWARD:
//                int ledCount = (int)Math.floor(timer.seconds()/effectTime);
//                ledCount = clamp(ledCount, 0, NeoPixelConfiguration.ledCount);
//                for(int i = 0; i<ledCount; i++)
//                {
//                    neoPixel.setColor(i, (int) (brightness * colour.r), (int) (brightness * colour.g), (int) (brightness * colour.b));
//                }
//                try {
//                    Thread.sleep(15);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                double phaseForward = (timer.seconds() % effectTime) / effectTime;
                int headForward = (int)(phaseForward * NeoPixelConfiguration.ledCount) + 1;

                for (int i = 1; i <= NeoPixelConfiguration.ledCount; i++) {
                    if (i == headForward) {
                        neoPixel.setColor(i, (int)(brightness * colour.r), (int)(brightness * colour.g), (int)(brightness * colour.b));
                    } else {
                        neoPixel.setColor(i, 0, 0, 0); // Turn off other LEDs
                    }
                }

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case CHASE_BACKWARD:
                double phaseBackward = (timer.seconds() % effectTime) / effectTime;
                int headBackward = NeoPixelConfiguration.ledCount - (int)(phaseBackward * NeoPixelConfiguration.ledCount);


                for (int i = 1; i <= NeoPixelConfiguration.ledCount; i++) {
                    if (i == headBackward) {
                        neoPixel.setColor(i, (int)(brightness * colour.r), (int)(brightness * colour.g), (int)(brightness * colour.b));
                    } else {
                        neoPixel.setColor(i, 0, 0, 0); // Turn off other LEDs
                    }
                }

                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }

        neoPixel.show();

    }
}
