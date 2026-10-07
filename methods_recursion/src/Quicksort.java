package src;
public class Quicksort {
    public static void main(String[] args) {
        int[] numbers = {4, 6, 2, 3, 6, 7, 8, 8, 3, 4, 2};
        quickSort(numbers, 0, numbers.length - 1);
        System.out.println("Sorted array:");
        for (int number : numbers) {
            System.out.print(number + " ");
        }
    }

    public static void quickSort(int[] numbers, int left, int right) {
        if (left < right) {
            int pivot = partition(numbers, left, right);
            quickSort(numbers, left, pivot - 1); // sort elements before the pivot
            quickSort(numbers, pivot + 1, right); // sort elements after the pivot
        }
    }

    public static int partition(int[] numbers, int left, int right) {
        int pivot = numbers[right]; // last element as the pivot
        int i = left - 1; // index of the smaller element

        for (int j = left; j < right; j++) {
            if (numbers[j] <= pivot) {
                i++;
                // swap numbers[i] and numbers[j]
                int temp = numbers[i];
                numbers[i] = numbers[j];
                numbers[j] = temp;
            }
        }
        // swap numbers[i + 1] and numbers[right] (pivot)
        int temp = numbers[i + 1];
        numbers[i + 1] = numbers[right];
        numbers[right] = temp;

        return i + 1; // return the pivot index
    }
}
