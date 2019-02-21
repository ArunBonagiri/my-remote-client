package remote.arun.com.myremote;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import static android.widget.Toast.LENGTH_LONG;

 /*

 *  This class is a main class  of  our application and it is used to
 *  add fragment's
 *  add mouse and keyboard activity's
 *  start main thread
 *  send and receive messages form Server
 *  It contains one inner async task class to run some operations on background
 * */

public class RemotePanel extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Context context;


    static  SendAndReceive sendAndReceive;
    ReceiverTask rTask;
    String hostname="unknown";

    // main method of this class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_panel);

        // Toolbar creation
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Adding Home,Power and Volume fragments into RemotePanel Activity
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new Home(),"Home");
        viewPagerAdapter.addFragments(new PowerOptions(),"Power");
        viewPagerAdapter.addFragments(new Volume(),"Volume");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Starting Background Task
        rTask = new ReceiverTask();
        rTask.execute();

    }

    // this method is used to perform required actions when user closing the application by using back button
    @Override
    public void onDestroy()
    {
        super.onDestroy();

        // display exit msg in our app screen
        Context context;
        context=getBaseContext();
        Toast.makeText(context, " Your commited exit ", LENGTH_LONG).show();

        // sending exit msg to server
        sendAndReceive.send("Client_Exit");
        moveTaskToBack(true);

    }

    // sending msg to server using SendAndReceive Class of send method
    public static void send(String string)
    {
        sendAndReceive.send(string);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    public void setHostname(String hostname)
    {
        this.hostname=hostname;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int res_id = item.getItemId();
        if(res_id==R.id.action_computer)
        {
            if(hostname.equalsIgnoreCase("unknown")){
                send("hostname");
            }
            else
            {
                context=getBaseContext();
                Toast.makeText(context,hostname,LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    /*******************************             Background Task           **********************************/

    public class ReceiverTask extends AsyncTask<String,String,SendAndReceive>
    {

        @Override
        protected SendAndReceive doInBackground(String... params)
        {
            // implementation of On-listener Interface method
            sendAndReceive = new SendAndReceive(new SendAndReceive.OnmessageListener()
            {

                @Override
                public void onMessageReceived(String string)
                {

                    // send received message into OnProgress update method
                    onProgressUpdate(string);
                }
            } );

            try
            {
                // connecting to server using SendAndReceive Class of connect method
                boolean b = sendAndReceive.connect();


            }catch(RuntimeException re){  re.printStackTrace(); System.out.println(" RemotePanel : Can't Connect,Error is:"+re);  }

            return null;
        }


        @Override
        protected void onProgressUpdate(final String... values)
        {
            super.onProgressUpdate(values[0]);
            System.out.println("RemotePanel:received Message is                       "+values[0]+"                        ");

            // new thread for sending ,received msg from here to main thread
            runOnUiThread(new Runnable() {
                @Override
                public void run()
                {
                    String string = String.valueOf(values[0]);
                    if(string.contains(":"))
                    {
                      String str = String.valueOf(string.split(":")[1]);
                      setHostname(str);
                      context=getBaseContext();
                      Toast.makeText(context,hostname,LENGTH_LONG).show();
                      System.out.println(" RemotePanel : msg received, hostname is :" +hostname);
                    }
                    else
                    {
                        System.out.println(" RemotePanel : msg received, hostname is :" +string);
                    }
                }
            });
        }
    }

    /*******************************          End of Background Task       **********************************/
}
