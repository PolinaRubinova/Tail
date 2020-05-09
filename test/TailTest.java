import Tail.TailLauncher;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TailLauncherTest {

    private String[] args1 = {"-o", "test/output.txt", "test/input1.txt"};
    private String[] args2 = {"-n", "5", "-o", "test/output.txt",
            "test/input1.txt", "test/input2.txt"};
    private String[] args3 = {"-c", "5", "-o", "test/output.txt",
            "test/input1.txt", "test/input2.txt"};
    private String[] argsErr1 = {"-n", "3", "-c", "5", "test/input1.txt"};
    //private String[] argsErr2 = {"-n", "5", "test/input0.txt"};
    //private String[] args4 = {"-o", "test/output.txt"};

    @Test
    void test1 () throws IOException {
        TailLauncher.main(args1);
        assertEquals(Files.readAllLines(Paths.get("test/output.txt")),
                Files.readAllLines(Paths.get("test/expected1.txt")));
    }


    @Test
    void test2 () throws IOException {
        TailLauncher.main(args2);
        assertEquals(Files.readAllLines(Paths.get("test/output.txt")),
                Files.readAllLines(Paths.get("test/expected2.txt")));
    }

    @Test
    void test3 () throws IOException {
        TailLauncher.main(args3);
        assertEquals(Files.readAllLines(Paths.get("test/output.txt")),
                Files.readAllLines(Paths.get("test/expected3.txt")));
    }

    @Test
    void testErr () {
        assertThrows(Error.class, () -> TailLauncher.main(argsErr1));
        //assertThrows(Error.class, () -> TailLauncher.main(argsErr2));
    }

    /*@Test
    void testConsole ()throws IOException  {
       TailLauncher.main(args4);
       assertEquals(Files.readAllLines(Paths.get("test/output.txt")),
       Files.readAllLines(Paths.get("test/expected4.txt")));
    }*/
}