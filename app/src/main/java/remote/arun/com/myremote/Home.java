package remote.arun.com.myremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */

// This fragment class is used to display mouse and Keyboard buttons and swithches tab's

public class Home extends Fragment implements View.OnClickListener{

   ImageButton buttonmouseScH,buttonkeyboardScH;
    View view;

    public Home()
    {
        // Required empty public constructor
    }

    // main method of this class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        buttonmouseScH = (ImageButton) view.findViewById(R.id.buttonMouseH);
        buttonkeyboardScH = (ImageButton) view.findViewById(R.id.buttonkeyboardH);

        buttonkeyboardScH.setOnClickListener(this);
        buttonmouseScH.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonkeyboardH: // Moveing RemotePanel Activity to Mouse Activity
                                       Intent i =new Intent(getContext(),Keyboard.class);
                                       startActivity(i);
                                       break;

             case R.id.buttonMouseH:  // Moveing RemotePanel Activity to Keyboard Activity
                                      Intent j = new Intent(getContext(),Mouse.class);
                                      startActivity(j);
                                      break;
        }
    }
}
