package org.firstinspires.ftc.teamcode.subsystems.arm.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;

public class ScoreSpecimen extends CustomConditionalCommand {
    public ScoreSpecimen() {
        super(
                new SequentialCommandGroup(
                        new SetSlideExtension(ArmSlideConfiguration.TargetPosition.SCORE_SPECIMEN),
                        new WaitUntilCommand(VLRSubsystem.getInstance(ArmSlideSubsystem.class)::reachedTargetPosition),
                        new SetArmState(ArmState.State.SCORE_SPECIMEN)
                ),
                () -> ArmState.get() != ArmState.State.SCORE_SPECIMEN
        );
    }
}
