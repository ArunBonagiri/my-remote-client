package remote.arun.com.myremote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * A simple {@link Fragment} subclass.
 */

// This fragment class is used to implement Volume features in our application

public class Volume extends Fragment implements  View.OnClickListener {

    Button buttonIvolume,buttonDvolume,buttonMute;
    View view;
    boolean isMute=false;

    public Volume() {
        // Required empty public constructor
    }

    // main method of this class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_volume, container, false);

        buttonIvolume = (Button) view.findViewById(R.id.buttonVi);
        buttonDvolume = (Button) view.findViewById(R.id.buttonVd);
        buttonMute = (Button) view.findViewById(R.id.buttonVMute);

        buttonIvolume.setOnClickListener(this);
        buttonDvolume.setOnClickListener(this);
        buttonMute.setOnClickListener(this);


        return view;
    }

    // this method is used to send volume settings into RemotePanel class
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonVi:  // send volume increase msg to RemotePanel class
                                 ((RemotePanel)getActivity()).send("iVolume");

                                 // display msg in our app screen
                                 // Context context = this.getContext();
                                 // Toast.makeText(context, " Volume incresses ", LENGTH_LONG).show();
                                 break;

            case R.id.buttonVd:  // display msg in our app screen
                                 // Context context1 = this.getContext();
                                 // Toast.makeText(context1, " Volume decreses ", LENGTH_LONG).show();

                                 // send volume decrease msg to RemotePanel class
                                 ((RemotePanel)getActivity()).send("dVolume");
                                 break;

            case R.id.buttonVMute:

                                  if(!isMute)
                                  {
                                      isMute = true;

                                      // send volume mute msg to RemotePanel class
                                      ((RemotePanel) getActivity()).send("mVolume");

                                      // display msg in our app screen
                                      Context context3 = this.getContext();
                                      Toast.makeText(context3, " mute volume ", LENGTH_LONG).show();

                                      // renaming the button name into unmute
                                      buttonMute.setText("UN MUTE");
                                  }
                                  else
                                  {
                                      isMute=false;

                                      // send volume decrease msg to RemotePanel class
                                      ((RemotePanel)getActivity()).send("uVolume");

                                      // renaming the button name into mute
                                      buttonMute.setText("MUTE");

                                      // display msg in our app screen
                                      Context context4 = this.getContext();
                                      Toast.makeText(context4, " Unmute volume ", LENGTH_LONG).show();
                                      break;
                                  }
                                  break;


        }

    }
}
