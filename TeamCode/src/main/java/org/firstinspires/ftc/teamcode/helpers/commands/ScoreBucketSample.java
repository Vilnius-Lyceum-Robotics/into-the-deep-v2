package org.firstinspires.ftc.teamcode.helpers.commands;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.MoveArmInToRobot;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.MoveArmToBucketDeposit;

public class ScoreBucketSample extends SequentialCommandGroup {
    public ScoreBucketSample(){
        addCommands(
                new MoveArmToBucketDeposit(),
                new WaitCommand(100),
                new MoveArmInToRobot(),
                new WaitCommand(200)
        );
    }
}
