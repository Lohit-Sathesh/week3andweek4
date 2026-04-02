import java.util.Arrays;
import java.util.Comparator;

/**
 * Client Risk Score Ranking System
 * Demonstrates Bubble Sort (Ascending) and Insertion Sort (Descending)
 * applied to financial compliance scenarios.
 */
public class ClientRiskRanking {

    // --- Static Nested Client Class ---
    static class Client {
        String name;
        int riskScore;      // 0 to 100
        double accountBalance;

        public Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return String.format("%s(Risk:%d, Bal:%.0f)", name, riskScore, accountBalance);
        }
    }

    /**
     * Bubble Sort: Ascending Order
     * Good for visualizing the "bubbling up" of high risk scores.
     */
    public static void bubbleSortAscending(Client[] clients) {
        int n = clients.length;
        int swapCount = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    // Swap logic
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swapCount++;
                }
            }
        }
        System.out.println("Bubble Sort (Asc) Swaps: " + swapCount);
    }

    /**
     * Insertion Sort: Descending Order (Priority Review)
     * Criteria: Sort by riskScore DESC, then accountBalance DESC for ties.
     */
    public static void insertionSortDescending(Client[] clients) {
        int n = clients.length;
        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            /* Move elements that have a LOWER risk score than 'key' 
               one position ahead of their current position.
            */
            while (j >= 0 && shouldShift(clients[j], key)) {
                clients[j + 1] = clients[j];
                j = j - 1;
            }
            clients[j + 1] = key;
        }
    }

    /**
     * Helper to determine Descending order priority.
     * Higher Risk Score comes first; Higher Balance breaks ties.
     */
    private static boolean shouldShift(Client current, Client key) {
        if (current.riskScore < key.riskScore) return true;
        if (current.riskScore == key.riskScore) {
            return current.accountBalance < key.accountBalance;
        }
        return false;
    }

    // --- Main Execution ---
    public static void main(String[] args) {
        Client[] clients = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 15000),
                new Client("clientB", 50, 2000),
                new Client("clientD", 80, 9000), // Same risk as C, higher balance
                new Client("clientE", 95, 1000)
        };

        System.out.println("--- KYC Risk Prioritization Report ---");
        System.out.println("Original List: " + Arrays.toString(clients));

        // Task 1: Bubble Sort Ascending
        Client[] bubbleData = clients.clone();
        bubbleSortAscending(bubbleData);
        System.out.println("Bubble (Asc): " + Arrays.toString(bubbleData));

        // Task 2: Insertion Sort Descending (For Priority Review)
        Client[] insertionData = clients.clone();
        insertionSortDescending(insertionData);
        System.out.println("Insertion (Desc): " + Arrays.toString(insertionData));

        // Task 3: Identify Top 3 High Risk Clients
        System.out.println("\n--- TOP 3 PRIORITY REVIEWS ---");
        for (int i = 0; i < Math.min(3, insertionData.length); i++) {
            System.out.println((i + 1) + ". " + insertionData[i]);
        }
        System.out.println("---------------------------------------");
    }
}