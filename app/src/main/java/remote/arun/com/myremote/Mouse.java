package remote.arun.com.myremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 // This class is used to implement the Mouse feature in our application

public class Mouse extends AppCompatActivity implements View.OnClickListener {

    Button buttonLeft,buttonRight;
    TextView mousePad;

    private float initX =0;
    private float initY =0;
    private float disX =0;
    private float disY =0;

    private boolean isConnected=false;
    private boolean mouseMoved=false;
    RemotePanel remotePanel;

    // Constructor on Required
    public Mouse()
    {
        remotePanel = new RemotePanel();
    }

    // main method of this class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse);

        buttonLeft = (Button)findViewById(R.id.buttonMLeft);
        buttonRight = (Button) findViewById(R.id.buttonMRight);
        mousePad = (TextView) findViewById(R.id.mousePlace);

        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);

        // this method is used to get the user screen touch's data and send it  into RemotePanel class
        mousePad.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                isConnected=true;
                if(isConnected )
                {
                    switch (event.getAction())
                    {

                        case MotionEvent.ACTION_DOWN:

                            //save X and Y positions when user touches the TextView
                            initX = event.getX();
                            initY = event.getY();
                            mouseMoved=false;
                            break;

                        case MotionEvent.ACTION_MOVE:

                            disX = event.getX() - initX; // get Mouse movement in x direction
                            disY = event.getY() - initY; // get Mouse movement in y direction

                            // save x,y values
                            initX = event.getX();
                            initY = event.getY();

                            if (disX != 0 || disY != 0)
                            {
                                // send x,y values into RemotePanel class
                                remotePanel.send(disX + "," + disY);

                            }

                            mouseMoved=true;
                            break;

                        case MotionEvent.ACTION_UP:

                            //consider a tap only if usr did not move mouse after ACTION_DOWN
                            if (!mouseMoved)
                            {
                                // send leftclick message into RemotePanel class
                                remotePanel.send("left_click");

                            }
                    }
                }
                return true;
            }
        });


    }


    // this method for left and right click buttons
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonMLeft: // send leftclick message into RemotePanel class
                                   remotePanel.send("left_click");
                                   break;

            case R.id.buttonMRight: // send rightclick message into RemotePanel class
                                    remotePanel.send("right_click");
                                    break;

        }

    }
}
