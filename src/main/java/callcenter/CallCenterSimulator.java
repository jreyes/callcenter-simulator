package callcenter;

public class CallCenterSimulator
{
// --------------------------- main() method ---------------------------

    public static void main( String... args )
    {
        for ( int i = 0; i < 3; i++ )
        {
            new ServiceAgent( i + 1 ).start();
        }

        new CallGenerator().start();
    }
}
