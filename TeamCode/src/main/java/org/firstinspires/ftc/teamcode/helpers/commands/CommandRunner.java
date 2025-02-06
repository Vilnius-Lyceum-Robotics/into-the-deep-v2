package org.firstinspires.ftc.teamcode.helpers.commands;

import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

/**
 * Runnable class to run FTCLib command scheduler on an
 * independent thread from the main opmode loop.
 */
public class CommandRunner implements Runnable {
    final OpModeRunningInterface runningInterface;
    private HardwareMap hardwareMap;

    public CommandRunner(OpModeRunningInterface runningInterface, HardwareMap hardwareMap) {
        this.runningInterface = runningInterface;
        this.hardwareMap = hardwareMap;
    }

    public void run() {
        while (!runningInterface.isOpModeRunning()) {
            try {
                sleep(10); // Wait for the opmode to start to start running commands
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // some stupid shit so it compiles
            }
        }

        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        while (runningInterface.isOpModeRunning()) {
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
            CommandScheduler.getInstance().run();
        }
    }
}
