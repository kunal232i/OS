package Phase2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class phase2 {

    static char M[][];
    static char R[];
    static char IR[];
    static int IC;
    static int C, RA;
    static char buffer[] = new char[41];
    static BufferedReader br;
    static String S;

    class PCB {
        static int JID, SI, TI, PI = 0, TTC, LLC, TTL, TLL, PTR;
    }

    static void init() {
        M = new char[300][4];
        IR = new char[4];
        R = new char[4];
        PCB.SI = 0;
        PCB.TI = 0;
        PCB.PI = 0;
        PCB.TTC = 0;
        PCB.LLC = 0;
    }

    static void printMemory() {
        int k = 0;
        for (int i = 0; i < M.length; i++) {
            if (i % 10 == 0) {
                System.out.println("Block[" + k + "]");
                k++;
            }
            System.out.print(i + "-->");
            for (int j = 0; j < 4; j++) {
                System.out.print(M[i][j]);
            }
            System.out.println();
        }
    }

    static int addressMAP(int VA) {
        if (VA >= 99) {
            PCB.PI = 2;
        }
        int PTE = PCB.PTR + (VA / 10);
        if (M[PTE][0] == '*' && (IR[0] == 'P' && IR[1] == 'D' || IR[0] == 'L' && IR[1] == 'R'
                || IR[0] == 'C' && IR[1] == 'R' || IR[0] == 'B' && IR[1] == 'T')) {
            PCB.PI = 3;
            return -1;
        }
        // for valid page fault
        if (M[PTE][0] == '*') {
            allocateFrame((PTE));
        }
        RA = (Integer.parseInt(String.valueOf(M[PTE][0]))) * 10 + (VA % 10);
        return RA;
    }

    static void MOS() throws IOException {
        // opcode error
        if (!((IR[0] == 'G' && IR[1] == 'D') || (IR[0] == 'P' && IR[1] == 'D') || (IR[0] == 'S' && IR[1] == 'R')
                || (IR[0] == 'L' && IR[1] == 'R') || (IR[0] == 'C' && IR[1] == 'R') || (IR[0] == 'B' && IR[1] == 'T')
                || (IR[0] == 'H'))) {
            PCB.PI = 1;
        }

        // operand error
        if (IR[0] != 'H') {
            String ts = String.valueOf(IR[2]);
            ts += String.valueOf(IR[3]);
            try {
                int t1 = Integer.parseInt(ts);
            } catch (Exception e) {
                PCB.PI = 2;
                if (IR[0] == 'P' && IR[1] == 'D') {
                    PCB.LLC++;
                    // line limit check
                    if (PCB.LLC > PCB.TLL)
                        terminate(2);
                }
            }
        }

        if (PCB.SI == 1 && PCB.TI == 0) {
            read(RA);
            PCB.SI = 0;
        }
        if (PCB.SI == 2 && PCB.TI == 0) {
            write(RA);
            PCB.SI = 0;
        }
        if (PCB.SI == 3 && PCB.TI == 0) {
            terminate(0);
        }
        if (PCB.SI == 1 && PCB.TI == 2) {
            terminate(3);
        }
        if (PCB.SI == 2 && PCB.TI == 2) {
            write(RA);
            terminate(3);
        }
        if (PCB.SI == 3 && PCB.TI == 2) {
            terminate(0);
        }

        if (PCB.TI == 0 && PCB.PI == 1) {
            terminate(4);
            System.exit(1);
        }
        if (PCB.TI == 0 && PCB.PI == 2) {
            terminate(5);

        }
        if (PCB.TI == 0 && PCB.PI == 3) {
            terminate(6);
        }

        if (PCB.TI == 2 && PCB.PI == 1) {
            terminate(3);
            terminate(4);
        }
        if (PCB.TI == 2 && PCB.PI == 2) {
            terminate(3);
            terminate(5);
        }
        if (PCB.TI == 2 && PCB.PI == 3) {
            terminate(3);
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
            RA = addressMAP(IC);
            if (PCB.PI != 0) {
                MOS();
                System.exit(1);
            }
            for (int i = 0; i < 4; i++) {
                IR[i] = M[RA][i];
            }
            IC++;
            String str1 = Character.toString(IR[0]);
            if (IR[0] != 'H') {
                str1 += Character.toString(IR[1]);
                str = String.valueOf(IR[2]);
                str += String.valueOf(IR[3]);
                try {
                    tmp = Integer.parseInt(str);
                } catch (Exception e) {
                    // operand error
                    PCB.PI = 2;
                    MOS();
                    System.exit(1);
                }
            }
            RA = addressMAP(tmp);
            if (PCB.PI != 0) {
                MOS();
                System.exit(1);
            }

            switch (str1) {
                case "GD":
                    PCB.TTC += 2;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(1);
                    }
                    PCB.SI = 1;
                    break;
                case "PD":
                    PCB.TTC++;
                    PCB.LLC++;
                    PCB.SI = 2;
                    if (PCB.LLC > PCB.TLL) {
                        terminate(2);
                        if (PCB.TTC > PCB.TTL) {
                            PCB.TI = 2;
                            MOS();
                        }
                        System.exit(0);
                    }
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    break;
                case "H":
                    PCB.TTC++;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    PCB.SI = 3;
                    break;
                case "LR":
                    PCB.TTC++;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    for (int i = 0; i < 4; i++) {
                        R[i] = M[RA][i];
                    }
                    break;
                case "SR":
                    PCB.TTC += 2;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    for (int i = 0; i < 4; i++) {
                        M[RA][i] = R[i];
                    }
                    break;
                case "CR":
                    PCB.TTC++;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    for (int i = 0; i < 4; i++) {
                        if (R[i] == M[RA][i]) {
                            C = 1;
                        } else {
                            C = 0;
                            break;
                        }
                    }
                    break;
                case "BT":
                    PCB.TTC++;
                    if (PCB.TTC > PCB.TTL) {
                        PCB.TI = 2;
                        MOS();
                        System.exit(0);
                    }
                    if (C == 1) {
                        IC = RA;
                    }
                    break;
            }
            MOS();
        } while (IR[0] != 'H');
    }

    static void read(int RA) throws IOException {
        S = br.readLine();
        if (S.startsWith("$END")) {
            terminate(1);
        }
        int r = RA;
        int c = 0;

        for (int i = 0; i < S.length(); i++) {
            M[r][c] = S.charAt(i);
            c++;
            if (c == 4) {
                c = 0;
                r++;
            }
            if (r == (RA + 10)) {
                break;
            }
        }
    }

    static void write(int RA) throws IOException {
        int w = RA;
        int c = 0;
        String str = "";

        while (M[w][c] != 0) {
            str += M[w][c++];
            if (c == 4) {
                c = 0;
                w++;
            }
            if (w == (RA + 10)) {
                break;
            }
        }

        try {
            FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
            output.write(str);
            output.write("\n");
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void terminate(int n) throws IOException {
        System.out.println("***********************************************************");
        switch (n) {
            case 0:
                System.out.println("No Error");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("No Error\n");
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 1:
                System.out.println("Out of Data");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Out of Data\n");
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 2:
                System.out.println("Line Limit Exceeded");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Line Limit Exceeded\n");
                    output.write("TLL = " + PCB.TLL + " LLC = " + PCB.LLC);
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 3:
                System.out.println("Time Limit Exceeded");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Time Limit Exceeded\n");
                    output.write("TTL = " + PCB.TTL + " TTC = " + PCB.TTC + " SI = " + PCB.SI + " TI = " + PCB.TI);
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 4:
                System.out.println("Operation Code Error");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Operation Code Error\n");
                    output.write("TI = " + PCB.TI + " PI = " + PCB.PI);
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 5:
                System.out.println("Operand Error");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Operand Error\n");
                    output.write("TI = " + PCB.TI + " PI = " + PCB.PI);
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 6:
                System.out.println("Invalid Page Fault");
                try {
                    FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
                    output.write("Invalid Page Fault\n");
                    output.write("TI = " + PCB.TI + " PI = " + PCB.PI);
                    output.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
        }
        try {
            FileWriter output = new FileWriter("D:\\OS\\Output.txt", true);
            output.write("\n\n");
            output.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("***********************************************************");

        System.out.println(" JID " + " TTL " + " TTC " + " TLL " + " LLC " + " SI " + " PI " + " TI " + " IC ");
        System.out.println(
                "  " + PCB.JID + "    " + PCB.TTL + "    " + PCB.TTC + "    " + PCB.TLL + "    " + PCB.LLC + "   "
                        + PCB.SI + "    " + PCB.PI + "   " + PCB.TI + "   " + IC);
        System.out.println("***********************************************************");

    }

    static void allocatePTR() {
        PCB.PTR = (int) (Math.random() * 30) * 10;
        int totalPTR = PCB.PTR + 10;

        for (int i = PCB.PTR; i < totalPTR; i++) {
            for (int j = 0; j < M[i].length; j++) {
                M[i][j] = '*';
            }
        }
    }

    static HashSet<Character> set = new HashSet<>();

    static int allocateFrame(int p) {
        char f = ' ';
        f = (char) ((Math.random() * 10) + '0');
        if (!set.contains(f)) {
            set.add(f);
        } else {
            while (set.contains(f)) {
                f = (char) ((Math.random() * 10) + '0');
            }
        }
        while (M[p][0] != '*') {
            p++;
        }
        M[p][0] = f;
        return Integer.valueOf(String.valueOf(M[p][0]));
    }

    static void load() throws IOException {
        while (S != null) {
            if (buffer[0] == '$' && buffer[1] == 'A' && buffer[2] == 'M' && buffer[3] == 'J') {

                init();
                allocatePTR();

                String J = String.valueOf(buffer[4]);
                J += String.valueOf(buffer[5]);
                J += String.valueOf(buffer[6]);
                J += String.valueOf(buffer[7]);
                PCB.JID = Integer.parseInt(J);
                String tl = String.valueOf(buffer[8]);
                tl += String.valueOf(buffer[9]);
                tl += String.valueOf(buffer[10]);
                tl += String.valueOf(buffer[11]);
                PCB.TTL = Integer.parseInt(tl);
                String tll = String.valueOf(buffer[12]);
                tll += String.valueOf(buffer[13]);
                tll += String.valueOf(buffer[14]);
                tll += String.valueOf(buffer[15]);
                PCB.TLL = Integer.parseInt(tll);

                int k = 0;
                S = br.readLine();
                int count = 0;

                while (!(buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A')) {
                    k = allocateFrame(PCB.PTR) * 10;
                    while (count != S.length()) {
                        for (int i = 0; i < 4; i++) {
                            if (count == S.length()) {
                                break;
                            }
                            buffer[i] = S.charAt(count);
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
                    S = br.readLine();
                    buffer = S.toCharArray();
                }

            }

            if (buffer[0] == '$' && buffer[1] == 'D' && buffer[2] == 'T' && buffer[3] == 'A') {
                startExecution();
            }
            if (buffer[0] == '$' && buffer[1] == 'E' && buffer[2] == 'N' && buffer[3] == 'D') {
                printMemory();
            }
            S = br.readLine();
            if (S != null) {
                buffer = S.toCharArray();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FileReader file = new FileReader("D:\\OS\\Input.txt");
        br = new BufferedReader(file);
        S = br.readLine();
        buffer = S.toCharArray();
        load();
    }
}
