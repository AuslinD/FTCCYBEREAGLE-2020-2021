package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class StateMachine extends AutoMethods{
    public String state;
    public ElapsedTime stateTime = new ElapsedTime();

    public  void ChangeState(String nextState)
    {
        if (state != nextState) {
            stateTime.reset();
            // nextatse is moveforward1
            // allstates index 0 moveforward2
            // alstate idex 1 mo
            // index 0 of allstate = moveForward
            state = nextState;
            ///// There will be more stuff here once we progress..

        }

    }


}

