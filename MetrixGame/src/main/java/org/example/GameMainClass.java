package org.example;

import java.util.Random;

public class GameMainClass {
    // Define the symbols and their probabilities
    private static final char[] SYMBOLS = {'A', 'B', 'C', 'D', 'E', 'F',};
    private static final double[] SYMBOL_PROBABILITIES = {0.1, 0.15, 0.2, 0.2, 0.2, 0.15};

    // Define the bonus symbols and their actions
    private static char[] BONUS_SYMBOLS={};
    // Define the reward multipliers for bonus symbols
    private static final double[] REWARD_MULTIPLIERS = {50, 25, 10, 5, 3, 1.5};

    // Define the matrix size
    private static final int MATRIX_SIZE = 3;

    // Define the winning combinations
    private static final char[][] WINNING_COMBINATIONS = {
            {'A', 'A', 'A'},
            {'B', 'B', 'B'},
            {'C', 'C', 'C'},
            {'D', 'D', 'D'},
            {'E', 'E', 'E'},
            {'F', 'F', 'F'},
    };
    public static void main(String[] args) {
        int bettingAmount = 100; // Betting amount

        // Generate the scratch matrix
        char[][] matrix = generateMatrix();

        // Check if the user has won
        double reward = checkWin(matrix) * bettingAmount;

        // Display the result
        System.out.println("Generated Matrix:");
        printMatrix(matrix);
        System.out.println("Reward Amound:" + reward);
    }

    // Method to generate the scratch matrix
    private static char[][] generateMatrix() {
        Random random = new Random();
        char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];

        // Fill the matrix with symbols based on probabilities
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                double rand = random.nextDouble();
                double cumulativeProbability = 0.0;
                for (int k = 0; k < SYMBOLS.length; k++) {
                    cumulativeProbability += SYMBOL_PROBABILITIES[k];
                    if (rand <= cumulativeProbability) {
                        matrix[i][j] = SYMBOLS[k];
                        break;
                    }
                }
            }
        }
        return matrix;
    }

    // Method to check if the user has won and calculate the reward
    private static double checkWin(char[][] matrix) {
        for (char[] combination : WINNING_COMBINATIONS) {
            if (checkCombination(matrix, combination)) {
                //System.out.println("combination "+combination[1]);
                //System.out.println("Amount "+calculateReward(combination));

                // Winning combination found, calculate the reward
                return calculateReward();

            }
        }
        return 0;
    }

    // Method to check if a specific combination exists in the matrix
    private static boolean checkCombination(char[][] matrix, char[] combination) {
        int count = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j] == combination[count]) {
                    count++;
                    if (count == combination.length) {
                        BONUS_SYMBOLS= new char[]{matrix[i][j]};
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Method to calculate the reward based on the winning combination
    private static double calculateReward() {
        double rewardMultiplier = 1.0;
        for (int i = 0; i < SYMBOLS.length; i++) {
            for (int j = 0; j < BONUS_SYMBOLS.length; j++) {
                if (SYMBOLS[i] == BONUS_SYMBOLS[j]) {
                    rewardMultiplier = REWARD_MULTIPLIERS[i];
                    break;
                }
            }
        }
        return rewardMultiplier;
    }

    // Method to print the matrix
    private static void printMatrix(char[][] matrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}