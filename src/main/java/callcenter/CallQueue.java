package callcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class CallQueue
{
// ------------------------------ FIELDS ------------------------------

    private static CallQueue instance;

    private int counter;

    private SimpleDateFormat formatter;

    private LinkedBlockingQueue<Call> queue;

// -------------------------- STATIC METHODS --------------------------

    public static void queueCall( int duration )
    {
        try
        {
            Call call = new Call( getInstance().counter++, duration );
            log( "Queueing call " + call.getNumber() + " with a duration of " + call.getDuration() + " minutes" );
            getInstance().queue.put( call );
        }
        catch ( InterruptedException e )
        {
            log( "There was an error queueing the call" );
        }
    }

    public static Call retrieveCall()
    {
        Call call = getInstance().queue.poll();
        if ( call != null )
        {
            log( "Retrieving call " + call.getNumber() );
        }
        return call;
    }

    private static CallQueue getInstance()
    {
        if ( instance == null )
        {
            instance = new CallQueue();
        }
        return instance;
    }

    private static void log( String s )
    {
        System.out.println( "[" + getInstance().formatter.format( new Date() ) + "][CallQueue] " + s );
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private CallQueue()
    {
        this.queue = new LinkedBlockingQueue<Call>();
        this.counter = 1;
        this.formatter = new SimpleDateFormat( "HH:mm:ss" );
    }
}
