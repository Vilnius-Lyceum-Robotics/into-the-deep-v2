package org.firstinspires.ftc.teamcode.subsystems.arm.commands;

import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.PrintCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.commands.CustomConditionalCommand;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;

public class ScoreSpecimen extends CustomConditionalCommand {
    public ScoreSpecimen() {
        super(
                new SequentialCommandGroup(
                        new SetSlideExtension(ArmSlideConfiguration.TargetPosition.SCORE_SPECIMEN),
                        new PrintCommand("SCORE SPECIMEN 1"),
                        new ParallelRaceGroup(
                                new WaitUntilCommand(VLRSubsystem.getInstance(ArmSlideSubsystem.class)::reachedTargetPosition),
                                new WaitCommand(ArmSlideConfiguration.WAIT_TIMEOUT)
                        ),
                        new PrintCommand("SCORE SPECIMEN 2"),
                        new SetArmState(ArmState.State.SCORE_SPECIMEN)
                ),
                () -> ArmState.get() != ArmState.State.SCORE_SPECIMEN
        );
    }
}
