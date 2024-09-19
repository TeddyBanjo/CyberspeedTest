package org.scratchgame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardCalculator {

    private static final Map<String, Integer> symbolRewards = new HashMap<>();
    public static List<String> appliedWinningCombinations = new ArrayList<>();
    public static List<String> appliedBonusSymbols = new ArrayList<>();

    static {
        symbolRewards.put("A", 5);
        symbolRewards.put("B", 3);
    }

    public static List<String> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public static List<String> getAppliedBonusSymbols() {
        return appliedBonusSymbols;
    }

    // Method to calculate the reward based on the array and bet amount
    public static int calculateReward(String[][] matrix, int betAmount) {
        int totalReward = 0;
        int rows = matrix.length;
        int columns = matrix[0].length;



        // Check for symbol_A and symbol_B rewards
        boolean hasSymbolA = false;
        boolean hasSymbolB = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j].equals("A")) {
                    hasSymbolA = true;
                }
                if (matrix[i][j].equals("B")) {
                    hasSymbolB = true;
                }
            }
        }

        if (hasSymbolA) {
            totalReward += betAmount * symbolRewards.get("A");
            appliedWinningCombinations.add("symbol_A");
        }
        if (hasSymbolB) {
            totalReward += betAmount * symbolRewards.get("B");
            appliedWinningCombinations.add("symbol_B");

        }

        // Check for same_symbol_5_times and same_symbol_3_times rewards
        Map<String, Integer> symbolCount = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                symbolCount.put(matrix[i][j], symbolCount.getOrDefault(matrix[i][j], 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
            String symbol = entry.getKey();
            int count = entry.getValue();

            if (count >= 5 && symbolRewards.containsKey(symbol)) {
                totalReward += symbolRewards.get(symbol) * 5;
                appliedWinningCombinations.add("same_symbol_5_times (" + symbol + ")");
            } else if (count >= 3 && symbolRewards.containsKey(symbol)) {
                totalReward += symbolRewards.get(symbol);
                appliedWinningCombinations.add("same_symbol_3_times (" + symbol + ")");
            }
        }

        // Check for same_symbols_vertically reward
        for (int j = 0; j < columns; j++) {
            String firstSymbol = matrix[0][j];
            boolean isVertical = true;

            for (int i = 1; i < rows; i++) {
                if (!matrix[i][j].equals(firstSymbol)) {
                    isVertical = false;
                    break;
                }
            }

            if (isVertical && symbolRewards.containsKey(firstSymbol)) {
                totalReward += symbolRewards.get(firstSymbol) * 2;
                appliedWinningCombinations.add("same_symbols_vertically (" + firstSymbol + ")");
            }
        }

        // Check for +1000 reward
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j].equals("+1000")) {
                    totalReward += 1000;
                    appliedBonusSymbols.add("+1000");
                }
            }
        }



        return totalReward;
    }


}



