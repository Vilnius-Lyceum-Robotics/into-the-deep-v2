package org.firstinspires.ftc.teamcode.subsystems.neopixel;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

@SuppressWarnings({"WeakerAccess", "unused"})
@Config
@I2cDeviceType
@DeviceProperties(name = "Adafruit I2C NeoPixel Driver", xmlTag = "NeoPixel")
public class NeoPixelDriver extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    private static final int pin = 15;
    private static final int BASE = 0x0E;
    private static final int PIN = 0x01;
    private static final int SPEED = 0x02;
    private static final int BUF_LENGTH = 0x03;
    private static final int BUF = 0x04;
    private static final int SHOW = 0x05;
    public static int COLOR_DIV_COEF = 1;

    int currentMax = 0;
    byte[] colorMap = new byte[170 * 3];


    public void setPin(int pin) {
        byte[] data = {(byte) PIN, (byte) pin};
        deviceClient.write(BASE, data);
    }


    byte[] buffLengthData = new byte[3];
    byte[] bufferData;

    public void setColor(int pixel, int r, int g, int b) {
        int realLength = (3 * pixel);
        buffLengthData[0] = (byte) BUF_LENGTH;
        buffLengthData[1] = 0;
        buffLengthData[2] = (byte) realLength;
        int realR = r / COLOR_DIV_COEF;
        int realG = g / COLOR_DIV_COEF;
        int realB = b / COLOR_DIV_COEF;
        //first 2 bytes for offset, bet palieku 0
        colorMap[realLength - 3] = (byte) realG;
        colorMap[realLength - 2] = (byte) realR;
        colorMap[realLength - 1] = (byte) realB;

        if (realLength > currentMax) {
            currentMax = realLength;
        }
        bufferData = new byte[3 + currentMax];
        bufferData[0] = (byte) BUF;
        bufferData[2] = (byte) 4;
        for (int i = 0; i < currentMax; i++) {
            bufferData[3 + i] = colorMap[i];
        }
    }


    public void show() {
        byte[] data = {(byte) SHOW};
        deviceClient.write(BASE, buffLengthData);
        deviceClient.write(BASE, bufferData);
        deviceClient.write(BASE, data);
    }


    @Override
    public Manufacturer getManufacturer() {

        return Manufacturer.Adafruit;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {

        return "Adafruit I2C NeoPixel Driver";
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x60);

    public NeoPixelDriver(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
        setPin(pin);
    }
}