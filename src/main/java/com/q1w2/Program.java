package com.q1w2;

import com.q1w2.ui.SimulationInterface;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SimulationInterface ui = new SimulationInterface(scanner);
        ui.start();
    }
}
