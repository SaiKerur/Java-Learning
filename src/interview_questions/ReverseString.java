package interview_questions;

public class ReverseString {
    public static String reverse(String str) {
        if(str == null){
            return null;
        }
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length-1;
        while (left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }
    public static void main(String[] args){
        String input = "hello";
        String output = reverse(input);
        System.out.println("Original String: " +input);
        System.out.println("Reversed String: " +output);
    }
}
