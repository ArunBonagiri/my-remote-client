package remote.arun.com.myremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// This class is used to implement the keyboard feature in our application

public class Keyboard extends AppCompatActivity implements View.OnClickListener {

    EditText keyboardEditText;
    TextView kboutText;
    Button  enter;
    RemotePanel remotePanel;
    String kboutstring="";

    // constructor on required
    public Keyboard()
    {
        remotePanel = new RemotePanel();
    }

    // main method of this class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        enter = (Button) findViewById(R.id.buttonEnter);
        keyboardEditText = (EditText) findViewById(R.id.editTextKip);
        kboutText = (TextView) findViewById(R.id.kbtextView);

        enter.setOnClickListener(this);

        keyboardEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
               if((keyCode== EditorInfo.IME_NULL)&&(event.getAction()==KeyEvent.ACTION_DOWN))
               {
                   remotePanel.send("enter");

                   // updateing the keyboard textview
                   kboutstring="";
                   kboutText.setText(kboutstring);
                   return true;
               }
               else if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_DEL))
               {
                   remotePanel.send("backspace");

                   // updateing the keyboard textview
                   try
                   {
                       int kbl = kboutstring.length();
                       if(kbl>-1)
                       {
                           kboutstring = kboutstring.substring(0, kbl - 1);
                           kboutText.setText(kboutstring);
                       }

                   }catch (StringIndexOutOfBoundsException e){ System.out.println(e); }
                   return true;
               }

              return false;
            }
        });

        keyboardEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {   }

            // This method will send user typed character into RemotePanel class
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try {


                    if (keyboardEditText.getText() != null)
                    {
                        // Getting the string form keyboardEditText
                        String str = String.valueOf(keyboardEditText.getText());


                        // Sending the last character of user typed into the RemotePanel class
                        remotePanel.send(String.valueOf(str.charAt(str.length()-1)));

                        // updateing the keyboard textview
                        kboutstring = kboutstring + str;
                        kboutText.setText(kboutstring);

                        // Setting keyboardEditText in to Normal position
                        keyboardEditText.setText("");


                    }

                }catch(StringIndexOutOfBoundsException e ){ e.printStackTrace();System.out.println("KeyBoard :  error is : "+e); }

            }

            @Override
            public void afterTextChanged(Editable s) { }

        });


    }

    @Override
    public void onClick(View v)
    {
      int id = v.getId();
      if(id==R.id.buttonEnter)
      {
          remotePanel.send("enter");

          // updateing the keyboard textview
          kboutstring="";
          kboutText.setText(kboutstring);
      }
    }
}
