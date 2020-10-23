package org.firstinspires.ftc.teamcode;

import java.util.List;

public class StateMachine extends AutoMethods{
    public List<String> allStates;
    public String state;
    public  void ChangeState(String nextState, int sleeptime)
    {
        // nextatse is moveforward1
        // allstates index 0 moveforward2
        // alstate idex 1 mo
        allStates.add(nextState);
        // index 0 of allstate = moveForward
        for (int i = 0; i < allStates.size(); i++)
        {
            if (nextState == allStates.get(i))
            {
                //telemetry.addData ("staterepeats", nextState);
            }
            else if (i >= allStates.size() - 1)
            {
                state = nextState;
                sleep(sleeptime);

            }
        }
        ///// There will be more stuff here once we progress..

    }


}

