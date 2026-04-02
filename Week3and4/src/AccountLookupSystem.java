import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Transaction ID Lookup System
 * Demonstrates Linear Search (O(n)) vs Binary Search (O(log n))
 * for high-volume audit compliance.
 */
public class AccountLookupSystem {

    static class Transaction {
        String accountId;
        String transactionId;

        public Transaction(String accountId, String transactionId) {
            this.accountId = accountId;
            this.transactionId = transactionId;
        }

        @Override
        public String toString() {
            return String.format("[%s: %s]", accountId, transactionId);
        }
    }

    /**
     * Linear Search: Simple but slow.
     * Checks every element from start to finish.
     * Time Complexity: O(n)
     */
    public static int linearSearch(List<Transaction> logs, String target) {
        int comparisons = 0;
        for (int i = 0; i < logs.size(); i++) {
            comparisons++;
            if (logs.get(i).accountId.equals(target)) {
                System.out.println("Linear Search Comparisons: " + comparisons);
                return i;
            }
        }
        System.out.println("Linear Search Comparisons (Not Found): " + comparisons);
        return -1;
    }

    /**
     * Binary Search: Fast but requires Sorted data.
     * Repeatedly divides the search interval in half.
     * Time Complexity: O(log n)
     */
    public static int binarySearch(List<Transaction> logs, String target) {
        int low = 0;
        int high = logs.size() - 1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs.get(mid).accountId);

            if (res == 0) {
                System.out.println("Binary Search Comparisons: " + comparisons);
                return mid;
            }
            if (res > 0) low = mid + 1;
            else high = mid - 1;
        }
        System.out.println("Binary Search Comparisons (Not Found): " + comparisons);
        return -1;
    }

    /**
     * Helper to count occurrences in a sorted list after Binary Search finds one.
     */
    public static int countOccurrences(List<Transaction> logs, String target, int foundIndex) {
        if (foundIndex == -1) return 0;

        int count = 1;
        // Check to the left
        int left = foundIndex - 1;
        while (left >= 0 && logs.get(left).accountId.equals(target)) {
            count++;
            left--;
        }
        // Check to the right
        int right = foundIndex + 1;
        while (right < logs.size() && logs.get(right).accountId.equals(target)) {
            count++;
            right++;
        }
        return count;
    }

    public static void main(String[] args) {
        List<Transaction> logs = new ArrayList<>();
        logs.add(new Transaction("accB", "TXN101"));
        logs.add(new Transaction("accA", "TXN102"));
        logs.add(new Transaction("accB", "TXN103"));
        logs.add(new Transaction("accC", "TXN104"));

        System.out.println("--- Citi Transaction Forensics: Audit Lookup ---");

        // 1. Linear Search (Unsorted)
        String target = "accB";
        int linIndex = linearSearch(logs, target);
        System.out.println("Linear Search first '" + target + "': index " + linIndex);

        // 2. Binary Search (Requires Sorting)
        // Note: For compliance, we sort by accountId first
        logs.sort((a, b) -> a.accountId.compareTo(b.accountId));
        System.out.println("\nSorted Logs for Binary Search: " + logs);

        int binIndex = binarySearch(logs, target);
        int occurrences = countOccurrences(logs, target, binIndex);

        System.out.println("Binary Search found '" + target + "' at index: " + binIndex);
        System.out.println("Total occurrences of '" + target + "': " + occurrences);

        System.out.println("-------------------------------------------------");
    }
}