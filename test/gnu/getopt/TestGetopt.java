package gnu.getopt;

import org.junit.Test;

public class TestGetopt {
    @Test
    public void testGetopt() {
        
        String[] argv = new String[]{"-a","1","-b","2","-c","3","-d","4"};
        Getopt g = new Getopt("testprog", argv, "ab:c::d");
        //
        int c;
        String arg;
        while ((c = g.getopt()) != -1) {
            switch (c) {
            case 'a':
            case 'd':
                System.out.print("You picked " + (char) c + "\n");
                break;
            //
            case 'b':
            case 'c':
                arg = g.getOptarg();
                System.out.print("You picked " + (char) c + " with an argument of "
                        + ((arg != null) ? arg : "null") + "\n");
                break;
            //
            case '?':
                break; // getopt() already printed an error
            //
            default:
                System.out.print("getopt() returned " + c + "\n");
            }
        }

    }
}
