public class ValidationUtils {
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }
    public static boolean isValidAccountType(String type) {
        if (type == null) return false;
        String t = type.trim().toLowerCase();
        return t.equals("savings") || t.equals("current");
    }
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    public static boolean hasSufficientBalance(double currentBalance, double withdrawAmount) {
        return currentBalance >= withdrawAmount;
    }
}