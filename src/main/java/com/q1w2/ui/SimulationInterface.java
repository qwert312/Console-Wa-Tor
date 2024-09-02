package com.q1w2.ui;

import com.q1w2.domain.creatures.Fish;
import com.q1w2.domain.creatures.SeaCreature;
import com.q1w2.domain.creatures.Shark;
import com.q1w2.exceptions.IncorrectInterfaceInputException;
import com.q1w2.logic.Simulation;

import java.util.Scanner;

public class SimulationInterface {

    private Scanner scanner;

    public SimulationInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("\n-------------Welcome to Wa-Tor simulation!-------------\n");

        while (true) {
            Simulation simulation = enterSimulationConfiguration();
            int cycleLimit = enterNumberOfSimulationCycles();

            runSimulation(simulation, cycleLimit);

            System.out.println("\nStart a new simulation? Enter 'n' if not.");
            String input = scanner.nextLine();
            if (input.equals("n"))
                break;
        }
    }

    private Simulation enterSimulationConfiguration() {
        while (true) {
            try {
                System.out.println("Simulation configuration:");
                System.out.print("\tEnter the ocean height: ");
                int oceanHeight = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
                System.out.print("\tEnter the ocean Width: ");
                int oceanWidth = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
                System.out.print("\tEnter the fish reproduction period: ");
                int fishReproductionPeriod = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
                System.out.print("\tEnter the shark reproduction period: ");
                int sharkReproductionPeriod = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
                System.out.print("\tEnter the shark starvation limit: ");
                int sharkStarvationLimit = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());

                return new Simulation(oceanHeight, oceanWidth, fishReproductionPeriod,
                        sharkReproductionPeriod, sharkStarvationLimit);
            } catch (IncorrectInterfaceInputException e) {
                System.out.println("\n" + e.getMessage() + "\n");
            }
        }
    }

    private int enterNumberOfSimulationCycles() {
        System.out.print("Almost ready. Enter the number of cycles after which the simulation will stop: ");
        while (true) {
            try {
                return InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
            } catch (IncorrectInterfaceInputException e) {
                System.out.println("\n" + e.getMessage() + "\n");
                System.out.print("Enter the number of cycles after which the simulation will stop: ");
            }
        }
    }

    private void runSimulation(Simulation simulation, int cycleLimit) {
        System.out.println("Simulation start...\n");
        printSimulationState(simulation);
        System.out.println();

        while (simulation.getCurrentCycle() <= cycleLimit) {
            simulation.update();
            printSimulationState(simulation);
            System.out.println();

            if (simulation.getCurrentCycle() == cycleLimit) {
                System.out.println("Do you want to continue the simulation?" +
                        "\nIf yes, please enter the number of additional cycles. " +
                        "It must be greater than 0." +
                        "\nIf no, please enter anything except a number greater than 0.");
                try {
                    int numberOfAdditionalCycles = InterfaceInputConverter.convertStringToNumber(scanner.nextLine());
                    cycleLimit += numberOfAdditionalCycles;
                } catch(IncorrectInterfaceInputException e) {
                    break;
                } finally {
                    System.out.println();
                }
            } else
                System.out.println();
        }
    }

    private void printSimulationState(Simulation simulation) {
        System.out.println("Current simulation cycle: " + simulation.getCurrentCycle());
        System.out.println("Current ocean state: ");
        SeaCreature[][] oceanGrid = simulation.getCurrentOceanGrid();
        for (int i = 0; i < oceanGrid.length; i++) {
            for (int j = 0; j < oceanGrid[i].length; j++) {
                if (oceanGrid[i][j] == null)
                    System.out.print("_");
                else if (oceanGrid[i][j] instanceof Fish)
                    System.out.print("F");
                else if (oceanGrid[i][j] instanceof Shark)
                    System.out.print("S");
            }
            System.out.println();
        }
    }
}
