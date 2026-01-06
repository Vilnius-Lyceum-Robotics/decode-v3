package org.firstinspires.ftc.teamcode.helpers.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

public class FollowCommand extends CommandBase {
    private final Follower follower;
    private final PathChain pathChain;
    public FollowCommand(Follower follower, PathChain pathChain) {
        this.follower = follower;
        this.pathChain = pathChain;
    }

    @Override
    public void initialize() {
        follower.followPath(pathChain, true);
    }

    @Override
    public void execute(){
//        follower.update();
    }

    @Override
    public boolean isFinished() {
        return !follower.isBusy();
    }

    @Override
    public void end(boolean interrupted){
        if(interrupted) follower.breakFollowing();
    }
}
