package pipereader;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;


class PRW {

    final PipedReader reader;
    final PipedWriter writer;

    public PRW() throws IOException {
        this.reader = new PipedReader();
        this.writer = new PipedWriter(this.reader);
    }

    public void read() {
        try {
            int data = reader.read();
            while (data != -1) {
                System.out.println("read: " + data + "  " + (char) data);
                data = reader.read();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            System.out.println("writing ...");
            writer.write("nikita");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        PRW prw = new PRW();

        Thread t1 = new Thread(() -> prw.read());
        Thread t2 = new Thread(() -> prw.write());

        t1.start();
        t2.start();
    }
}
