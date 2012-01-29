package callcenter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceAgent
    implements Runnable
{
// ------------------------------ FIELDS ------------------------------

    private long callExpiration;

    private SimpleDateFormat formatter;

    private int id;

    private boolean running;

    private ServiceAgentStatus status;

// --------------------------- CONSTRUCTORS ---------------------------

    public ServiceAgent( int id )
    {
        this.id = id;
        this.status = ServiceAgentStatus.FREE;
        formatter = new SimpleDateFormat( "HH:mm:ss" );
    }

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface Runnable ---------------------

    @Override
    public void run()
    {
        while ( running )
        {
            if ( status == ServiceAgentStatus.FREE )
            {
                Call call = CallQueue.retrieveCall();
                if ( call != null )
                {
                    log( "Answering call " + call.getNumber() );
                    callExpiration = System.currentTimeMillis() + ( call.getDuration() * 60 * 1000 );
                    status = ServiceAgentStatus.IN_A_CALL;
                }
            }
            else
            {
                if ( System.currentTimeMillis() > callExpiration )
                {
                    log( "hanging up" );
                    status = ServiceAgentStatus.FREE;
                }
            }
            sleep();
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public void start()
    {
        running = true;
        new Thread( this ).start();
    }

    public void stop()
    {
        running = false;
    }

    private void log( String s )
    {
        System.out.println( "[" + formatter.format( new Date() ) + "][ServiceAgent][Agent " + id + "] " + s );
    }

    private void sleep()
    {
        try
        {
            Thread.sleep( 5000 );
        }
        catch ( InterruptedException e )
        {
            e.printStackTrace();
        }
    }
}
