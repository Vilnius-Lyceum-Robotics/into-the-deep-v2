package org.firstinspires.ftc.teamcode.subsystems.claw.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.CustomConditionalCommand;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;

public class ToggleClawAngle extends SequentialCommandGroup {
    public ToggleClawAngle(){
        addCommands(
                new CustomConditionalCommand(
                    new ConditionalCommand(
                        new SetClawAngle(ClawConfiguration.TargetAngle.DOWN),
                        new SetClawAngle(ClawConfiguration.TargetAngle.UP),
                        ()-> (VLRSubsystem.getInstance(ClawSubsystem.class).getTargetAngle() == ClawConfiguration.TargetAngle.UP ||
                        VLRSubsystem.getInstance(ClawSubsystem.class).getTargetAngle() == ClawConfiguration.TargetAngle.DEPOSIT)
                    ),
                    () -> (ArmState.get() != ArmState.State.DEPOSIT)
                )
        );
    }
}
