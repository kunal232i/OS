import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class phase1 {

    static char M[][];
    static char R[];
    static char IR[];
    static int IC, C;
    static int SI;
    static char buffer[] = new char[10000];
    static BufferedReader br;
    static String s;

    static void init() {
        M = new char[100][4];
        IR = new char[4];
        R = new char[4];
    }

    static void printMemory() {
        int k = 0;
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println("Block[" + k + "]");
                k++;
            }
            for (int j = 0; j < 4; j++) {
                System.out.print(M[i][j]);
            }
            System.out.println();
        }
    }

    static void startExecution() throws IOException {
        IC = 0;
        executeUserProgram();
    }

    static void executeUserProgram() throws IOException {
        int tmp = 0;
        String str = "";
        do {
            for (int i = 0; i < 4; i++) {
                IR[i] = M[IC][i];
            }
            String str1 = Character.toString(IR[0]);
            if (IR[0] != 'H') {
                str1 += Character.toString(IR[1]);
                str = String.valueOf(IR[2]);
                str += String.valueOf(IR[3]);
                tmp = Integer.parseInt(str);
            }
            IC++;

            switch (str1) {
                case "GD":
                    SI = 1;
                    break;
                case "PD":
                    SI = 2;
                    break;
                case "H":
                    SI = 3;
                    break;
                case "LR":
                    for (int i = 0; i < 4; i++) {
                        R[i] = M[tmp][i];
                    }
                    break;
                case "SR":
                    for (int i = 0; i < 4; i++) {
                        M[tmp][i] = R[i];
                    }
                    break;
                case "CR":
                    for (int i = 0; i < 4; i++) {
                        if (R[i] == M[tmp][i]) {
                            C = 1;
                        } else {
                            C = 0;
                            break;
                        }
                    }
                    break;
                case "BT":
                    if (C == 1) {
                        IC = tmp;
                    }
                    break;
            }

            if (SI == 1) {
                read();
                SI = 0;
            }
            if (SI == 2) {
                write();
                SI = 0;
            }
            if (SI == 3) {
                terminate();
            }
        } while (IR[0] != 'H');

    }

    static void read() throws IOException {
        s = br.readLine();
        String str = String.valueOf(IR[2]);
        str += String.valueOf(IR[3]);

        int tmp = Integer.parseInt(str);
        int checkBlock = tmp;

        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            M[tmp][c] = s.charAt(i);
            c++;
            if (c == 4) {
                c = 0;
                tmp++;
            }
            if (tmp == (checkBlock + 10)) {
                break;
            }
        }
    }

    static void write() throws IOException {
        String str = String.valueOf(IR[2]);
        str += String.valueOf(IR[3]);

        int tmp = Integer.parseInt(str);
        int checkBlock = tmp;
        String s = "";
        int c = 0;
        while (M[tmp][c] != 0) {
            s += M[tmp][c++];
            if (c == 4) {
                c = 0;
                tmp++;
            }
            if (tmp == (checkBlock + 10)) {
                break;
            }
        }

        try {
            FileWriter output = new FileWriter("output.txt", true);
            output.write(s);
            output.write("\n");
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void terminate() throws IOException {
        try {
            FileWriter output = new FileWriter("output.txt", true);
            output.write("\n\n");
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void load() throws IOException {
        while (s != null) {
            if (buffer[0] == '$' && buffer[1] == 'A' && buffer[2] == 'M' && buffer[3] == 'J') {
                init();
                int k = 0;
                s = br.readLine();
                int count = 0;
                while (!(buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A')) {
                    while (count != s.length()) {
                        for (int i = 0; i < 4; i++) {
                            if (count == s.length()) {
                                break;
                            }
                            buffer[i] = s.charAt(count);
                            count++;
                        }

                        for (int i = 0; i < 4; i++) {
                            if (buffer[i] == 'H') {
                                M[k][0] = 'H';
                                break;
                            }
                            M[k][i] = buffer[i];
                        }
                        k++;
                    }
                    count = 0;
                    s = br.readLine();
                    buffer = s.toCharArray();
                }

            }

            if (buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A') {
                startExecution();
            }
            if (buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                printMemory();
            }
            s = br.readLine();
            if (s != null) {
                buffer = s.toCharArray();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FileReader file = new FileReader("input.txt");
        br = new BufferedReader(file);
        s = br.readLine();
        buffer = s.toCharArray();
        load();
    }
}
