import com.sun.source.tree.ReturnTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public boolean isHappy(int n) {
        Map<Integer, Integer> arr = new HashMap<>(); //tạo 1 mảng HashMap
        if (n < 1 || n > Math.pow(2, 31) - 1) return false; //check đkiện n theo đề bài ban đầu
        return recursion(n, arr);
    }

    public boolean recursion(int n, Map<Integer, Integer> arr) { //dùng đệ quy để loop liên tục với số sau khi return
        String nStr = Integer.toString(n);  //convert số nguyên n sang kiểu String
        int sum = 0;
        for (int i = 0; i < nStr.length(); i++) {   //duyệt for cho từng ký tự chữ số
            sum += Math.pow(nStr.charAt(i) - '0', 2);   //mỗi lần duyệt sẽ + với số index thứ i mũ 2, trừ 0 là vì theo bảng mã ASCII
        }
        if (sum == 1) return true;
        else if (sum < 1 || sum > Math.pow(2, 31) - 1) return false;    //check đkiện đề bài
        else if (arr.containsKey(sum))
            return false;    //nếu sum đã tồn tại trong mảng arr trước đó tức loop vô hạn => return false
        arr.put(sum, sum);  //nếu chưa tồn tại && khác 1 thì put vào mảng arr
        return recursion(sum, arr);
    }

    public boolean isValidSudoku(char[][] board) {
        boolean row = true;
        boolean column = true;      //char[hàng][cột]
        int i = 0;
        int j = 0;
        Map<Character, Integer> arrRow = new HashMap<>();
        Map<Character, Integer> arrCol = new HashMap<>();
        Map<Character, Integer> arrSubBox = new HashMap<>();
        return isValidRow(board, arrRow) && isValidCol(board, arrCol) && checkSubBox(board, arrSubBox);
    }

    //    public boolean isValidRow(char[][] board, Map<Character, Integer> arrRow, int i, int j) {
//        if (j == board[0].length) {
//            i++;
//            j = 0;
//            arrRow.clear();
//        }
//        if (i == board[0].length) return true;
//        if (board[i][j] == '.') {
//            j++;
//            return isValidRow(board, arrRow, i, j);
//        }
//        if (arrRow.containsKey(board[i][j]) && board[i][j] != '.') return false;
//        else if (!arrRow.containsKey(board[i][j]) && board[i][j] != '.'){
//            arrRow.put(board[i][j], j);
//            j++;
//        }
//        return isValidRow(board, arrRow, i, j);
//    }
    public boolean isValidRow(char[][] board, Map<Character, Integer> arrRow) {
        for (int i = 0; i < 9; i++) {
            arrRow.clear();
            for (int j = 0; j < 9; j++) {
                if (arrRow.containsKey(board[i][j]) && board[i][j] != '.') return false;
                else if (!arrRow.containsKey(board[i][j]) && board[i][j] != '.') {
                    arrRow.put(board[i][j], j);
                }
            }
        }
        return true;
    }
    public boolean isValidCol(char[][] board, Map<Character, Integer> arrCol) {
        for (int j = 0; j < 9; j++) {
            arrCol.clear();
            for (int i = 0; i < 9; i++) {
                if (arrCol.containsKey(board[i][j]) && board[i][j] != '.') return false;
                else if (!arrCol.containsKey(board[i][j]) && board[i][j] != '.') {
                    arrCol.put(board[i][j], j);
                }
            }
        }
        return true;
    }

//    public boolean isValidColumn(char[][] board, Map<Character, Integer> arrCol, int i, int j) {
//        if (i == board[0].length) {
//            j++;
//            i = 0;
//            arrCol.clear();
//        }
//        if (j == board[0].length) return true;
//        if (board[i][j] == '.') {
//            i++;
//            return isValidRow(board, arrCol, i, j);
//        }
//        if (arrCol.containsKey(board[i][j]) && board[i][j] != '.') return false;
//        else if (!arrCol.containsKey(board[i][j]) && board[i][j] != '.') {
//            arrCol.put(board[i][j], j);
//            i++;
//        }
//        return isValidRow(board, arrCol, i, j);
//    }

    public boolean checkSubBox(char[][] board, Map<Character, Integer> arrSubBox) {
        for (int i = 0; i < 9; i += 3) {
            arrSubBox.clear();
            for (int j = 0; j < 9; j += 3) {
                arrSubBox.clear();
                for (int k = 0; k < 9; k++) {
                    int r = j + k % 3;
                    int c = i + k / 3;
                    if (arrSubBox.containsKey(board[r][c]) && board[r][c] != '.') return false;
                    else if (!arrSubBox.containsKey(board[r][c]) && board[r][c] != '.') {
                        arrSubBox.put(board[r][c], r);
                    }
                }
            }
        }
        return true;
    }
}
