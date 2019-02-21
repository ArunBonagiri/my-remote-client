package remote.arun.com.myremote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */

// This fragment class is used to implement PowerOption's feature in our application

public class PowerOptions extends Fragment  implements  View.OnClickListener{

    ImageButton bShutdown,bRestart,bSleep,bLock;
    View view;

    // constructor on required
    public PowerOptions()
    {
        // Required empty public constructor
    }

    // main method of this class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view =  inflater.inflate(R.layout.fragment_power_options, container, false);

        bShutdown = (ImageButton) view.findViewById(R.id.buttonShutDown);
        bSleep = (ImageButton) view.findViewById(R.id.buttonSleep);
        bLock = (ImageButton) view.findViewById(R.id.buttonLock);
        bRestart = (ImageButton) view.findViewById(R.id.buttonRestart);

        bShutdown.setOnClickListener(this);
        bSleep.setOnClickListener(this);
        bRestart.setOnClickListener(this);
        bLock.setOnClickListener(this);

        return view;
    }

    // This is used to sending shutdown,sleep,restart and lock messages into RemotePanel Class
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonShutDown: System.out.println("Shutdown");

                                      // display message into the screen
                                      Context context = this.getContext();
                                      Toast.makeText(context, " Shudowning..!", LENGTH_LONG).show();

                                      // send shutdown msg to RemotePanel class
                                      ((RemotePanel)getActivity()).send("shutdown");
                                      break;

            case R.id.buttonSleep:   System.out.println("Sleep");

                                     // display message into the screen
                                     Context context1 = this.getContext();
                                     Toast.makeText(context1, " sleeping..! ", LENGTH_LONG).show();

                                     // send sleep msg to Remotepanel class
                                     ((RemotePanel)getActivity()).send("sleep");
                                     break;

            case R.id.buttonRestart: System.out.println("Restarting PC");

                                     // display message into the screen
                                     Context context2 = this.getContext();
                                     Toast.makeText(context2, " Restarting..! ", LENGTH_LONG).show();

                                     // send restart msg to Remotepanel class
                                     ((RemotePanel)getActivity()).send("restart");
                                     break;

            case R.id.buttonLock:   System.out.println("Lock");

                                    // display message into the screen
                                    Context context4 = this.getContext();
                                    Toast.makeText(context4, " Locked..! ", LENGTH_LONG).show();

                                    // send lock msg to Remotepanel class
                                    ((RemotePanel)getActivity()).send("lock");
                                    break;

        }
    }
}
