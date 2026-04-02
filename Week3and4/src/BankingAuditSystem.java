import java.util.ArrayList;
import java.util.List;

/**
 * Banking Transaction Audit System
 * Covers: Bubble Sort, Insertion Sort, and Stability.
 */
public class BankingAuditSystem {

    // --- Nested Transaction Class ---
    static class Transaction {
        String id;
        double fee;
        String timestamp; // Format HH:mm

        public Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return String.format("[%s: $%.1f @ %s]", id, fee, timestamp);
        }
    }

    // --- Sorting Logic ---

    /**
     * Bubble Sort: Optimized with early termination.
     * Best for small batches (<= 100).
     */
    public static void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;
        int passes = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    // Swap
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break;
        }
        System.out.println("BubbleSort Result: " + list + " // " + passes + " passes, " + swaps + " swaps");
    }

    /**
     * Insertion Sort: Stable sort handling Fee + Timestamp.
     * Best for medium batches (100-1,000).
     */
    public static void insertionSort(List<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            // Stability check: Sort by fee, then by timestamp
            while (j >= 0 && (list.get(j).fee > key.fee ||
                    (list.get(j).fee == key.fee && list.get(j).timestamp.compareTo(key.timestamp) > 0))) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        System.out.println("InsertionSort (Fee+TS) Result: " + list);
    }

    /**
     * Outlier Detection
     */
    public static void checkOutliers(List<Transaction> list, double threshold) {
        System.out.print("High-fee outliers: ");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > threshold) {
                System.out.print(t.id + " ");
                found = true;
            }
        }
        if (!found) System.out.print("none");
        System.out.println();
    }

    // --- Main Execution ---
    public static void main(String[] args) {
        // Sample Data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println("--- Citi Transaction Audit Report ---");

        // Task 1: Bubble Sort by Fee
        List<Transaction> batch1 = new ArrayList<>(transactions);
        bubbleSort(batch1);

        // Task 2: Insertion Sort by Fee + Timestamp
        List<Transaction> batch2 = new ArrayList<>(transactions);
        insertionSort(batch2);

        // Task 3: Outlier Check
        checkOutliers(batch2, 50.0);

        System.out.println("-------------------------------------");
    }
}