package com.main;

import java.util.Scanner;


public class Main {
    public static void main(final String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String command = sc.nextLine();
            Solver.solver(command);
        }
    }
}
