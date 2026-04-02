import java.util.Arrays;

/**
 * Risk Threshold System
 * Demonstrates Linear Search and Binary Search variants (Floor/Ceiling)
 * for assigning clients to risk bands.
 */
public class RiskThresholdLookup {

    /**
     * Linear Search: Checks every band for an exact match.
     */
    public static void linearSearch(int[] bands, int threshold) {
        int comparisons = 0;
        boolean found = false;
        for (int i = 0; i < bands.length; i++) {
            comparisons++;
            if (bands[i] == threshold) {
                System.out.println("Linear: threshold=" + threshold + " -> found at index " + i + " (" + comparisons + " comps)");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Linear: threshold=" + threshold + " -> not found (" + comparisons + " comps)");
        }
    }

    /**
     * Binary Search for Floor: Largest value <= target.
     */
    public static int findFloor(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int floor = -1;
        int comps = 0;

        while (low <= high) {
            comps++;
            int mid = low + (high - low) / 2;

            if (bands[mid] == target) return bands[mid];

            if (bands[mid] < target) {
                floor = bands[mid]; // Potential candidate
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.print("Binary floor(" + target + "): " + (floor == -1 ? "None" : floor));
        return floor;
    }

    /**
     * Binary Search for Ceiling: Smallest value >= target.
     */
    public static int findCeiling(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int ceiling = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (bands[mid] == target) return bands[mid];

            if (bands[mid] > target) {
                ceiling = bands[mid]; // Potential candidate
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        System.out.println(", ceiling: " + (ceiling == -1 ? "None" : ceiling));
        return ceiling;
    }

    public static void main(String[] args) {
        int[] sortedRisks = {10, 25, 50, 100};
        int threshold = 30;

        System.out.println("--- Compliance Risk Band Assignment ---");
        System.out.println("Current Risk Bands: " + Arrays.toString(sortedRisks));

        // 1. Linear Search Comparison
        linearSearch(sortedRisks, threshold);

        // 2. Binary Search for Range (Floor & Ceiling)
        findFloor(sortedRisks, threshold);
        findCeiling(sortedRisks, threshold);

        // Example: Finding insertion point for a high-risk client
        int newClientRisk = 120;
        System.out.println("\nChecking high-risk boundary for: " + newClientRisk);
        findFloor(sortedRisks, newClientRisk);
        findCeiling(sortedRisks, newClientRisk);

        System.out.println("---------------------------------------");
    }
}