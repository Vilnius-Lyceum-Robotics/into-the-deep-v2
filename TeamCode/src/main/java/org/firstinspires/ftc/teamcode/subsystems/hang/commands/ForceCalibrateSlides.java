package org.firstinspires.ftc.teamcode.subsystems.hang.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;

public class ForceCalibrateSlides extends SequentialCommandGroup {
    public ForceCalibrateSlides(){
        addRequirements(VLRSubsystem.getSlides());
        addCommands(
                new InstantCommand(()-> VLRSubsystem.getSlides().setPowerOverride(true)),
                new InstantCommand(()-> VLRSubsystem.getSlides().setMotorPower(-0.5)),
                new WaitCommand(500),
                new InstantCommand(()-> VLRSubsystem.getSlides().setMotorPower(0)),
                new InstantCommand(()-> VLRSubsystem.getSlides().setPowerOverride(false))
        );
    }
}
