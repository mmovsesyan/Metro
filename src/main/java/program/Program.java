package program;

import repository.Metro;

import java.io.IOException;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        try {
            Metro m1 = new Metro("metro.csv");
            System.out.println(m1);
            System.out.println(m1.sumTraffic());
            m1.save("metro1.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
