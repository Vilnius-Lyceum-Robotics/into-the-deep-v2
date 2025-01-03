package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.auto.pedroCommands.FollowPath;
import org.firstinspires.ftc.teamcode.auto.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.auto.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.auto.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.helpers.opmode.VLRLinearOpMode;
import org.firstinspires.ftc.teamcode.helpers.subsystems.VLRSubsystem;
import org.firstinspires.ftc.teamcode.helpers.utils.GlobalConfig;
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmState;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.MoveArmToIntake;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetRotatorAngle;
import org.firstinspires.ftc.teamcode.subsystems.arm.commands.SetSlideExtension;
import org.firstinspires.ftc.teamcode.subsystems.arm.rotator.ArmRotatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.arm.slide.ArmSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.chassis.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawConfiguration;
import org.firstinspires.ftc.teamcode.subsystems.claw.ClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawAngle;
import org.firstinspires.ftc.teamcode.subsystems.claw.commands.SetClawTwist;

@Config
@Photon
@Autonomous(name = "VLRAuto")
public class VLRAuto extends VLRLinearOpMode {
    private Follower follower;

    private final double xStart = 9.6;
    private final double yStart = 60;


    @Override
    public void run() {
        VLRSubsystem.requireSubsystems(ArmSlideSubsystem.class, ArmRotatorSubsystem.class, ClawSubsystem.class);
        VLRSubsystem.initializeAll(hardwareMap);

        GlobalConfig.setIsInvertedMotors(true);

        follower = new Follower(hardwareMap);
        follower.setStartingPose(new Pose(xStart, yStart, 0));
        follower.setMaxPower(0.6);

        FollowPath.setStartingPoint(new Point(xStart, yStart));
        FollowPath.setFollower(follower);

        waitForStart();
        schedulePath();

        CommandScheduler.getInstance().run();

        while (opModeIsActive()) {
            //follower.telemetryDebug(FtcDashboard.getInstance().getTelemetry());
        }
    }


    private void schedulePath() {
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new SetClawTwist(0.4),

                new ParallelCommandGroup(
                        new FollowPath(0, new Point(37, 60)),
                        new SequentialCommandGroup(
                                new WaitCommand(600),
                                new SetRotatorAngle(103.5),
                                new WaitUntilCommand(() -> VLRSubsystem.getInstance(ArmRotatorSubsystem.class).getAngleDegrees() >= 60),
                                new SetClawTwist(ClawConfiguration.TargetTwist.NORMAL),
                                new SetSlideExtension(0.36),
                                new WaitUntilCommand(()-> VLRSubsystem.getInstance(ArmSlideSubsystem.class).reachedTargetPosition()),
                                new WaitCommand(100)
                        )
                ),

                new WaitCommand(100),
                new SetSlideExtension(0.165),
                new WaitUntilCommand(()-> VLRSubsystem.getInstance(ArmSlideSubsystem.class).reachedTargetPosition()),
                new WaitCommand(20000)





//                new FollowPath(0, -90, new Point(28, 60)),
//                new WaitCommand(500),
//
//                new FollowPath(false,
//                        new Point(29, 42.5),
//                        new Point(38.5, 29),
//                        new Point(57.5, 29)),
//
//                new WaitCommand(50),
//
//                new FollowPath(0, new Point(57.5, 19)),
//                new FollowPath(0, new Point(21, 19)),
//                new FollowPath(0, new Point(57.5, 19)),
//                new FollowPath(0, new Point(57, 9.5)),
//                new FollowPath(0, new Point(21, 9.5)),
//
//                new FollowPath(0, new Point(57, 9.5)),
//                new FollowPath(0, new Point(57, 3.5)),
//                new FollowPath(0, new Point(21, 3.5)),
//
//                new FollowPath(0, new Point(17, 28)),
//                new WaitCommand(1500),
//                new FollowPath(0, new Point(39, 71.5))
        ));
    }

}
