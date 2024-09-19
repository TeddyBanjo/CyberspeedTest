package org.scratchgame;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

public class MatrixGenerator {
    private final JsonNode config ;
    private String[] standardSymbols = {"A","B","C","D","E","F"};
    private String[] bonusSymbols = {"10x","5x","+1000","500","Miss"};
    private String[] allSymbolsInMatrix = new String[9];


    public MatrixGenerator(JsonNode config) {
        this.config = config;
    }

    public String[] getAllSymbolsInMatrix() {
        return allSymbolsInMatrix;
    }


    public String[][] generateMatrix() {
        int rows = config.path("rows").asInt();
        int columns = config.path("columns").asInt();
        String[][] matrix = new String[rows][columns];


        // Filling the matrix with random symbols
        for (int i = 0; i < rows; i++) {
             for (int j = 0; j < columns; j++) {
                matrix[i][j] = pickRandomSymbol();

            }


        }

        // Transfer values from the matrix to an array

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                allSymbolsInMatrix[index++] = matrix[i][j];

            }
        }

        return matrix;

    }



    public String pickRandomSymbol() {
        // Combine the two arrays
        String[] allSymbols = new String[standardSymbols.length + bonusSymbols.length];
        System.arraycopy(standardSymbols, 0, allSymbols, 0, standardSymbols.length);
        System.arraycopy(bonusSymbols, 0, allSymbols, standardSymbols.length, bonusSymbols.length);

        // Pick a random index
        Random random = new Random();
        int randomIndex = random.nextInt(allSymbols.length);

        // Return the randomly selected symbol
        return allSymbols[randomIndex];
    }

}

