public class repeat {
    public static Character firstNonRepeatingChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char flag = str.charAt(i);
            boolean isNonRepeating = true;
            for (int j = 0; j < str.length(); j++) {
                if (i != j && flag == str.charAt(j)) {
                    isNonRepeating = false;
                    break;
                }
            }
            if (isNonRepeating) {
                return flag;
            }
        }
        return null; 
    }

    public static void main(String[] args) {
        String str = "swiss";
        Character result = firstNonRepeatingChar(str);
        if (result != null) {
            System.out.println("First non-repeating character is: " + result);
        } else {
            System.out.println("No non-repeating character found.");
        }
    }
}