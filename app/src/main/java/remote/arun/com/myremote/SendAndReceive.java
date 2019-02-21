package remote.arun.com.myremote;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


 // This class is used to connect,send msg,receive msg and disconnect with server

public class SendAndReceive extends  Thread  {

    static  Socket socket;
    static InetAddress serverAddr = null;

    public static  PrintWriter printWriter;
    public  BufferedReader bufferedReader;
    private static int SERVER_PORT;
    private static String SERVER_IP;
    boolean rRun = false;
    private  String receivedMessage;

    OnmessageListener onmessageListener;

    public void setSERVER_IP(String ip)
    {
        this.SERVER_IP = ip;
        Log.e("TCP Client", "C: ip setted"+SERVER_IP);
    }

    public void setSERVER_PORT(int port)
    {
        this.SERVER_PORT = port;
        Log.e("TCP Client", "C: port setted"+SERVER_PORT);
    }

    // parametarized constructor
    public SendAndReceive(OnmessageListener onmessageListener)
    {
        this.onmessageListener=onmessageListener;

    }

    // connecting to server
    public  boolean  connect()
    {

        // Log.e("TCP Client", "C: Connecting to : ip ="+SERVER_IP+" port= "+SERVER_PORT);
        try
        {
            serverAddr = InetAddress.getByName(SERVER_IP);
        }
        catch (UnknownHostException e) {  e.printStackTrace();  return  false; }

        try {

            // new thread for connect to Server
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {

                        socket = new Socket(serverAddr, SERVER_PORT);
                        // start for main thread
                        start();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            // start for connect thread
            thread.start();

            //System.out.println("SendAndReceive : socket succesed with ip :"+SERVER_IP+" port= "+SERVER_PORT);


        } catch (Exception e)
        {
            System.out.println("SendAndReceive : socket can't connecting to ip :"+SERVER_IP+" port= "+SERVER_PORT);

            e.printStackTrace();
            return  false;
        }

        Log.e("TCP Client", "C: Connection success with : ip ="+serverAddr+" port= "+SERVER_PORT);

        return  true;

    }

    //stoping main thread
    public void stopSR()
    {
        rRun=false;
    }


    // send messages to Server
    public static void send(String result)
    {
        try {
            if (printWriter != null && result != null) {
                printWriter.println(result);
                printWriter.flush();
                System.out.println("SendAndReceive : Message sent.!  "+result);
            }
        } catch (Exception e) { Log.e("remote_pc", "Error while Sending..!", e); }

    }

    // main thread run here.!
    public void run()
    {
        rRun = true;

        try {

            try {

                //send the message to the server
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                System.out.println("SendAndReceive : Connection success ,printWriter created.!");

                // It is used to receive the message form server
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("S: Received Message: buffered reader created");
                int i=1;
                
				while (rRun)
                {
                    if(i%100==0) { System.out.println("S: Received Message: buffered reader running "+i); }
                    receivedMessage = bufferedReader.readLine();
                    if (receivedMessage != null && onmessageListener != null)
                    {
                        System.out.println("S: Received Message: before send to RP " + receivedMessage);

                        // Send the received Message into RemotePanel class
                           onmessageListener.onMessageReceived(receivedMessage);
                        System.out.println("S: Received Message: after send to RP " + receivedMessage);


                    }
                    receivedMessage = null;
                    i++;

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + receivedMessage + "'");

            } catch (Exception e) { Log.e("TCP", "S: Error", e); }

            finally
            {
                // closeing Socket Connection
                socket.close();
            }
        } catch (Exception e) { Log.e("TCP", "C: Error", e); }
    }

    // interface for sending,received message into one class to another class
    public interface OnmessageListener
    {
      public void onMessageReceived(String string);
    }

}
