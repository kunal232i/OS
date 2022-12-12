package Algorithms;

import java.util.*;

public class SRTF {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter no of process: ");
        int n = sc.nextInt();
        int PID[] = new int[n];
        int AT[] = new int[n];
        int BT[] = new int[n];
        int CT[] = new int[n];
        int TAT[] = new int[n];
        int WT[] = new int[n];
        int f[] = new int[n]; // flag
        int k[] = new int[n]; // BT
        double avgWT = 0, avgTAT = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("enter process " + (i + 1) + " arrival time: ");
            AT[i] = sc.nextInt();
            System.out.println("enter process " + (i + 1) + " brust time: ");
            BT[i] = sc.nextInt();
            PID[i] = i + 1;
            k[i] = BT[i];
            f[i] = 0;
        }

        // sorting according to arrival times
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - (i + 1); j++) {
                if (AT[j] > AT[j + 1]) {
                    swap(AT, j, j + 1);
                    swap(BT, j, j + 1);
                    swap(PID, j, j + 1);
                }
            }
        }

        // Find Compeletion time
        int tot = 0; // total time
        int time = 0;

        while (true) {
            int c = n;
            int min = Integer.MAX_VALUE;

            if (tot == n) {
                break;
            }
            for (int i = 0; i < n; i++) {
                if ((AT[i] <= time) && (f[i] == 0) && (BT[i] < min)) {
                    min = BT[i];
                    c = i;
                }
            }

            if (c == n) {
                time++;
            } else {
                BT[c]--;
                time++;
                if (BT[c] == 0) {
                    CT[c] = time;
                    f[c] = 1;
                    tot++;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            TAT[i] = CT[i] - AT[i];
            WT[i] = TAT[i] - k[i];
            avgWT += WT[i];
            avgTAT += TAT[i];
        }

        System.out.println("\npid  arrival  burst  complete turn waiting");
        for (int i = 0; i < n; i++) {
            System.out.println(
                    PID[i] + "  \t\t" + AT[i] + "\t\t" + k[i] + "\t\t" + CT[i] + "\t\t" + TAT[i] + "\t\t" + WT[i]);
        }
        sc.close();
        System.out.println("\naverage waiting time: " + (avgWT / n));
        System.out.println("average turnaround time:" + (avgTAT / n));
    }

    static void swap(int nums[], int start, int end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }
}
