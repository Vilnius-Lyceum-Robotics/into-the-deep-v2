package org.firstinspires.ftc.teamcode.subsystems.arm.commands;

import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.PrintCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.commands.CustomConditionalCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawTwist;

public class MoveArmToSpecimenDeposit extends CustomConditionalCommand {
    public MoveArmToSpecimenDeposit(){
        super(
                new SequentialCommandGroup(
                        new SetRotatorAngle(ArmRotatorConfiguration.TargetAngle.DEPOSIT_SPECIMEN),
                        new WaitUntilCommand(() -> VLRSubsystem.getInstance(ArmRotatorSubsystem.class).getAngleDegrees() >= 60),
                        new SetClawTwist(ClawConfiguration.TargetTwist.NORMAL),
                        new PrintCommand("DEPOSIT SPECIMEN 1"),
                        new ParallelRaceGroup(
                                new WaitUntilCommand(VLRSubsystem.getInstance(ArmSlideSubsystem.class)::reachedTargetPosition),
                                new WaitCommand(ArmSlideConfiguration.WAIT_TIMEOUT)
                        ),
                        new PrintCommand("DEPOSIT SPECIMEN 2"),
                        new SetSlideExtension(ArmSlideConfiguration.TargetPosition.DEPOSIT_SPECIMEN),
                        new ParallelRaceGroup(
                            new WaitUntilCommand(VLRSubsystem.getInstance(ArmSlideSubsystem.class)::reachedTargetPosition),
                            new WaitCommand(ArmSlideConfiguration.WAIT_TIMEOUT)
                        ),
                        new PrintCommand("DEPOSIT SPECIMEN 3"),
                        new SetArmState(ArmState.State.DEPOSIT_SPECIMEN)
                ),
                ()-> ArmState.get() != ArmState.State.DEPOSIT_SPECIMEN
        );
    }
}
