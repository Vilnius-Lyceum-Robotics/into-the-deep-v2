package org.firstinspires.ftc.teamcode.subsystems.neopixel;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;

@SuppressWarnings({"WeakerAccess", "unused"})
@I2cDeviceType
@DeviceProperties(name = "AAAA Adafruit I2C NeoPixel Driver",  xmlTag = "NeoPixel")
public class NeoPixelDriver extends I2cDeviceSynchDevice<I2cDeviceSynch>
{
    private static final int pin = 15;
    private static final int BASE = 0x0E;
    private static final int PIN = 0x01;
    private static final int SPEED = 0x02;
    private static final int BUF_LENGTH = 0x03;
    private static final int BUF = 0x04;
    private static final int SHOW = 0x05;

    int currentMax = 0;
    byte[] colorMap = new byte[170*3];


    public void setPin(int pin) {
        byte[] data = {(byte) PIN, (byte) pin};
        deviceClient.write(BASE, data);
    }


    public void setColor(int pixel, int r, int g, int b)
    {
        int realLength = 3*pixel;
        byte[] data = {(byte) BUF_LENGTH, 0, (byte) realLength};
        deviceClient.write(BASE, data);
        int realR = r/11;
        int realG = g/11;
        int realB = b/11;
        //first 2 bytes for offset, bet palieku 0
        colorMap[realLength - 3] = (byte) realG;
        colorMap[realLength - 2] = (byte) realR;
        colorMap[realLength - 1] = (byte) realB;

        if(realLength>currentMax)
        {
            currentMax = realLength;
        }
        byte[] data2 = new byte[3 + currentMax];
        data2[0] = (byte) BUF;
        for(int i = 0; i < currentMax; i++)
        {
            data2[3+i] = colorMap[i];
        }
        deviceClient.write(BASE, data2);
    }

    public void show()
    {
        byte[] data = {(byte) SHOW};
        deviceClient.write(BASE, data);
    }


    @Override
    public Manufacturer getManufacturer()
    {

        return Manufacturer.Adafruit;
    }

    @Override
    protected synchronized boolean doInitialize()
    {
        return true;
    }

    @Override
    public String getDeviceName()
    {

        return "AAAAAA Adafruit I2C NeoPixel Driver";
    }
    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x60);
    public NeoPixelDriver(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned)
    {
        super(deviceClient, deviceClientIsOwned);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
        setPin(pin);
    }
}