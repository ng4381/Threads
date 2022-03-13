import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println(nik());

        Nik nik = new Nik();

        Function<String, Boolean> f1 = s -> nik.isTrue("nik");
        Function<String, Boolean> f2 = nik::isTrue;

        Consumer<Nik> consumer = Nik::testNik;

        f1.apply("nik");


        String[] s = {"11", "22", "33"};

    }

    public static int nik() {
        try {
            System.out.println("111111");
            //return 0;
        } finally {
            System.out.println("sdgdsfgfds");
            return 0;
        }
    }
}

class Nik {
    public boolean isTrue(String str) {
        return str.equals("nik");
    }

    public void testNik() {
        System.out.println("NNIKKKKK");
    }
}
