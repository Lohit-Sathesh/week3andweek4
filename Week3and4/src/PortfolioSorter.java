import java.util.Arrays;

/**
 * Portfolio Return Sorting System
 * Demonstrates Stable Merge Sort and Optimized Quick Sort
 * with Median-of-Three pivot selection for financial assets.
 */
public class PortfolioSorter {

    static class Asset {
        String ticker;
        double returnRate;
        double volatility;

        public Asset(String ticker, double returnRate, double volatility) {
            this.ticker = ticker;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return String.format("[%s: %.1f%% Return, %.1f%% Vol]", ticker, returnRate, volatility);
        }
    }

    // --- Merge Sort: Stable & Ascending ---
    public static void mergeSort(Asset[] assets, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(assets, left, mid);
            mergeSort(assets, mid + 1, right);
            merge(assets, left, mid, right);
        }
    }

    private static void merge(Asset[] assets, int left, int mid, int right) {
        Asset[] leftArr = Arrays.copyOfRange(assets, left, mid + 1);
        Asset[] rightArr = Arrays.copyOfRange(assets, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            // Stability: use <= to preserve order for identical returns
            if (leftArr[i].returnRate <= rightArr[j].returnRate) {
                assets[k++] = leftArr[i++];
            } else {
                assets[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) assets[k++] = leftArr[i++];
        while (j < rightArr.length) assets[k++] = rightArr[j++];
    }

    // --- Quick Sort: Descending Return + Ascending Volatility ---
    public static void quickSort(Asset[] assets, int low, int high) {
        if (low < high) {
            // Optimization: Median-of-Three pivot selection
            int pivotIndex = partition(assets, low, high);
            quickSort(assets, low, pivotIndex - 1);
            quickSort(assets, pivotIndex + 1, high);
        }
    }

    private static int partition(Asset[] assets, int low, int high) {
        int mid = low + (high - low) / 2;

        // Median-of-Three: ensures pivot is likely near the actual median
        sortThreeForMedian(assets, low, mid, high);
        swap(assets, mid, high); // Move pivot to end

        Asset pivot = assets[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Secondary Sort Logic: Higher Return first, then Lower Volatility
            if (assets[j].returnRate > pivot.returnRate ||
                    (assets[j].returnRate == pivot.returnRate && assets[j].volatility < pivot.volatility)) {
                i++;
                swap(assets, i, j);
            }
        }
        swap(assets, i + 1, high);
        return i + 1;
    }

    private static void sortThreeForMedian(Asset[] arr, int a, int b, int c) {
        if (arr[a].returnRate > arr[b].returnRate) swap(arr, a, b);
        if (arr[a].returnRate > arr[c].returnRate) swap(arr, a, c);
        if (arr[b].returnRate > arr[c].returnRate) swap(arr, b, c);
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Asset[] portfolio = {
                new Asset("AAPL", 12.0, 15.0),
                new Asset("TSLA", 8.0, 25.0),
                new Asset("GOOG", 15.0, 10.0),
                new Asset("MSFT", 12.0, 12.0) // Same return as AAPL, but lower volatility
        };

        System.out.println("--- Investment Portfolio Sorting ---");

        // Task 1: Merge Sort (Ascending)
        Asset[] mData = portfolio.clone();
        mergeSort(mData, 0, mData.length - 1);
        System.out.println("Merge Sort (Asc):  " + Arrays.toString(mData));

        // Task 2: Quick Sort (Ranked: Desc Return, Asc Vol)
        Asset[] qData = portfolio.clone();
        quickSort(qData, 0, qData.length - 1);
        System.out.println("Quick Sort (Rank): " + Arrays.toString(qData));

        System.out.println("------------------------------------");
    }
}