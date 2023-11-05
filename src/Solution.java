public class Solution {

    //1. Merge Sort
    public int[] sortArrayByMergeSort(int[] nums) {

        return mergeSort(nums, 0, nums.length - 1);
    }

    public int[] mergeSort(int[] nums, int left, int right) {
        //1. Chia ra
        if (left == right) {
            return new int[]{nums[left]};
        }
        int mid = left + (right - left) / 2;
        int[] arr1 = mergeSort(nums, left, mid);
        int[] arr2 = mergeSort(nums, mid + 1, right);

        //2. Trộn vào
        return merge(arr1, arr2);
    }

    public int[] merge(int[] arr1, int[] arr2) {
        int n = arr1.length + arr2.length;
        int[] result = new int[n];
        int i = 0, i1 = 0, i2 = 0;
        while (i < n) {
            if (i1 < arr1.length && i2 < arr2.length) {
                if (arr1[i1] < arr2[i2]) {
                    result[i++] = arr1[i1++];
                } else {
                    result[i++] = arr2[i2++];
                }
            } else {
                if (i1 < arr1.length) {
                    result[i++] = arr1[i1++];
                }

                if (i2 < arr2.length) {
                    result[i++] = arr2[i2++];
                }
            }
        }
        return result;
    }

    //2. Quick sort
    public int[] sortArrayByQuickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        //1. Chọn pivot (điểm chia)
        int pivot = partitionCenterPivot(nums, left, right);
        //Phân bổ mảng
        //2. Lặp lại bước 1
        quickSort(nums, left, pivot - 1);
        quickSort(nums, pivot, right);
    }

    public int partitionCenterPivot(int[] nums, int left, int right) {
        int mid = left + (right - left) / 2;
        int pivotValue = nums[mid];

        //Phân bổ lại
        int iLeft = left;
        int iRight = right;
        while (iLeft <= iRight) {
            while (nums[iLeft] < pivotValue) {
                iLeft++;
            }
            while (nums[iRight] > pivotValue) {
                iRight--;
            }
            if (iLeft <= iRight) {
                swap(nums, iLeft, iRight);
                iLeft++;
                iRight--;
            }
        }
        return iLeft;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    ////////////////
    public int numIdenticalPairs(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (nums[i] == nums[j]) count++;
            }
        }
        return count;
    }

    public int countAsterisks(String s) {
        String[] arrStr = s.split("\\|");
        int count = 0;
        for (int i = 0; i < arrStr.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < arrStr[i].length(); j++) {
                    if (arrStr[i].charAt(j) == '*') count++;
                }
            }
        }
        return count;
    }

    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) return false;
        boolean left = true;
        boolean right = true;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] <= arr[i - 1] && i == 1) return false;
            if (arr[i] == arr[i - 1]) return false;
            if (arr[i] < arr[i - 1]) {
                left = false;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] == arr[j - 1]) return false;
                    if (arr[j] > arr[j - 1]) {
                        right = false;
                        left = true;
                    }
                }
                break;
            }
        }
        return !left && right;
    }
}
