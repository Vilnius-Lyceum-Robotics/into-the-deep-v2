package org.firstinspires.ftc.teamcode.helpers.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;

import java.util.function.BooleanSupplier;

public class CustomWaitTimeoutCommand extends CommandBase {
    private final Command command;

    public CustomWaitTimeoutCommand(BooleanSupplier condition){
        this.command = new WaitUntilCommand(condition);
    }

    public CustomWaitTimeoutCommand(BooleanSupplier condition, int waitTimeMillis){
        this.command = new ParallelRaceGroup(
                new WaitUntilCommand(condition),
                new WaitCommand(waitTimeMillis)
        );
    }

    @Override
    public void initialize() {
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        return command.isFinished();
    }

    @Override
    public boolean runsWhenDisabled() {
        return command.runsWhenDisabled();
    }
}
