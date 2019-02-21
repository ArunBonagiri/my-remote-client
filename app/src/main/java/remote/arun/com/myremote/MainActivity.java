package remote.arun.com.myremote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.net.Socket;

import static android.widget.Toast.*;

// This class is used to perform login actions

public class MainActivity extends AppCompatActivity
{

    EditText ipText,portText;
    Button  connectButton;

    public String ip;
    public int port;

    SendAndReceive sendAndReceive;
    Intent i;
    Context context;
    ProgressDialog pd;
    Test test;

    // main method of this class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipText = (EditText) findViewById(R.id.ipeditText);
        portText = (EditText) findViewById(R.id.porteditText);
        connectButton = (Button)findViewById(R.id.buttonConnect);
        context=getBaseContext();
        pd = new ProgressDialog(MainActivity.this);

        sendAndReceive = new SendAndReceive(null);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ip = String.valueOf(ipText.getText());
                port = Integer.parseInt(String.valueOf(portText.getText()));
                System.out.println(" MainActivity: From MainActivity ip :"+ip+" port:"+port);
                boolean check = validIP(ip);

                if(check==false || port!=(int)port||port!=4444)
                {


                    System.out.println(" MainActivity: Invalid ip or port");
                    System.out.println(" MainActivity: from send Extras ip :" + ip + " port:" + port);
                    makeText(context, " Invalid IP or Port", LENGTH_LONG).show();

                }
                else
                {

                    sendAndReceive.setSERVER_IP(ip);
                    sendAndReceive.setSERVER_PORT(port);

                    try
                    {


                        pd.setMessage("Searching ...");
                        pd.show();

						// testing connection before we connect to server
                        test = new Test();
                        test.execute();

                    }
                    catch (Exception e)
                    {
                        pd.dismiss();
                        context = getBaseContext();
                        Toast.makeText(context, " Invalid IP or PORT", LENGTH_LONG).show();
                        System.out.println(" MainActivity: Invalid  ip :" + ip + " port:" + port);

                    }
                }

            }
        });




    }

    // this method for moving to next Activity
    public void move()
    {

            context = getBaseContext();
            Toast.makeText(context, " Connected.!", LENGTH_LONG).show();

            i = new Intent(getApplicationContext(), RemotePanel.class);
            startActivity(i);
    }

    // this method for checking ip address
    public static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }
            if(ip.contains("IP"))
            {
                return  false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }



    /***********************                 Test Connection class                    ********************/

    private class Test extends  AsyncTask<Void,Void,Void>
     {
        Socket socket=null;

        @Override
        protected Void doInBackground(Void... params)
        {

            try
            {
                socket = new Socket(ip,port);
                System.out.println(" MainActivity : Test : Server found with :" + ip + " port:" + port);

            }
            catch (IOException e)
            {
                System.out.println(" MainActivity : Test : Server not found with :" + ip + " port:" + port);
            }

            try {

                if(socket!=null)
                {

                    String str = "proceed";
                    onProgressUpdate(str);
                }
                else
                {
                    String str=null;
                    onProgressUpdate(str);

                }

            } catch (Exception e) {  e.printStackTrace();  }

            try
            {
                if(socket!=null)
                {
                    socket.close();
                    System.out.println(" Main Activity: Test : closing socket in background method ...");
                }

            } catch (IOException e)
             {
                e.printStackTrace();
             }


            return null;
        }


        protected void onProgressUpdate(final String str)
        {
            System.out.println("                       "+str+"                        ");

            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {

                    if(socket!=null)
                    {
                        try
                        {
                            socket.close();
                            System.out.println("MainActivty: Test : closing socket in on update ...");

                        } catch (IOException e) { e.printStackTrace(); }
                    }


                    if(str!=null&&str.equalsIgnoreCase("proceed"))
                    {
                        move();
                        System.out.println("MainActivity: Test : moveing ...");
                        pd.dismiss();
                        pd.setMessage("Please Wait ..");
                        pd.show();

                    }
                    else
                    {
                        pd.dismiss();
                        context = getBaseContext();
                        Toast.makeText(context, "PC not found.!", LENGTH_LONG).show();
                    }

                }
            });
        }
    }
    /***********************                 end of the test class                    ********************/
}
