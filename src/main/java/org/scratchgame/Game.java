package org.scratchgame;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Game {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar scratch-game.jar --config src/main/resources/config.json --betting-amount 100");
            System.exit(1);
        }

        String configPath = args[0];
        int betAmount = Integer.parseInt(args[1]);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode config = mapper.readTree(new File(configPath));

            MatrixGenerator generator = new MatrixGenerator(config);
            RewardCalculator calculator = new RewardCalculator();
            generator.getAllSymbolsInMatrix();

            String[][] matrix = generator.generateMatrix();
            int reward = calculator.calculateReward(matrix, betAmount);

            System.out.println("{");
            System.out.println("    \"matrix\": " + matrixToJson(matrix) + ",");
            System.out.println("    \"reward\": " + reward + ",");
            System.out.println("    \"applied_winning_combinations\": " + RewardCalculator.getAppliedWinningCombinations()+ ",");
            System.out.println("    \"applied_bonus_symbol\": " + RewardCalculator.getAppliedBonusSymbols());
            System.out.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String matrixToJson(String[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < matrix.length; i++) {
            sb.append("    [");
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append("\"").append(matrix[i][j]).append("\"");
                if (j < matrix[i].length - 1) sb.append(", ");
            }
            sb.append("]");
            if (i < matrix.length - 1) sb.append(", ");
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
