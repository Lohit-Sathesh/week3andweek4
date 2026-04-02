import java.util.Arrays;

/**
 * Trade Volume Analysis System
 * Demonstrates Merge Sort (Stable) and Quick Sort (Fast/In-place)
 * for high-volume financial data.
 */
public class TradeVolumeAnalysis {

    static class Trade {
        String id;
        int volume;

        public Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    // --- Merge Sort Implementation (Ascending & Stable) ---
    public static void mergeSort(Trade[] trades, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(trades, left, mid);
            mergeSort(trades, mid + 1, right);
            merge(trades, left, mid, right);
        }
    }

    private static void merge(Trade[] trades, int left, int mid, int right) {
        Trade[] leftArr = Arrays.copyOfRange(trades, left, mid + 1);
        Trade[] rightArr = Arrays.copyOfRange(trades, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].volume <= rightArr[j].volume) { // <= maintains stability
                trades[k++] = leftArr[i++];
            } else {
                trades[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) trades[k++] = leftArr[i++];
        while (j < rightArr.length) trades[k++] = rightArr[j++];
    }

    // --- Quick Sort Implementation (Descending & In-place) ---
    public static void quickSort(Trade[] trades, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(trades, low, high);
            quickSort(trades, low, pivotIndex - 1);
            quickSort(trades, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] trades, int low, int high) {
        // Using Lomuto partition scheme with the last element as pivot
        int pivot = trades[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Sort Descending: check if current volume is GREATER than pivot
            if (trades[j].volume >= pivot) {
                i++;
                Trade temp = trades[i];
                trades[i] = trades[j];
                trades[j] = temp;
            }
        }
        Trade temp = trades[i + 1];
        trades[i + 1] = trades[high];
        trades[high] = temp;
        return i + 1;
    }

    // --- Utility: Merge Two Already Sorted Lists ---
    public static Trade[] mergeTwoSessions(Trade[] morning, Trade[] afternoon) {
        Trade[] combined = new Trade[morning.length + afternoon.length];
        int i = 0, j = 0, k = 0;
        while (i < morning.length && j < afternoon.length) {
            if (morning[i].volume <= afternoon[j].volume) combined[k++] = morning[i++];
            else combined[k++] = afternoon[j++];
        }
        while (i < morning.length) combined[k++] = morning[i++];
        while (j < afternoon.length) combined[k++] = afternoon[j++];
        return combined;
    }

    public static void main(String[] args) {
        Trade[] session1 = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.println("--- Citi Trading Desk: Volume Analysis ---");

        // 1. Merge Sort (Ascending)
        Trade[] mSortData = session1.clone();
        mergeSort(mSortData, 0, mSortData.length - 1);
        System.out.println("MergeSort (Asc): " + Arrays.toString(mSortData));

        // 2. Quick Sort (Descending)
        Trade[] qSortData = session1.clone();
        quickSort(qSortData, 0, qSortData.length - 1);
        System.out.println("QuickSort (Desc): " + Arrays.toString(qSortData));

        // 3. Merging Morning + Afternoon Sessions
        Trade[] morning = {new Trade("m1", 100), new Trade("m2", 400)};
        Trade[] afternoon = {new Trade("a1", 200), new Trade("a2", 500)};