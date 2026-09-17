public class ValidationUtils {

    // Checks that a deposit/withdrawal amount is valid (must be positive)
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // Checks that account type is one of the allowed values
    public static boolean isValidAccountType(String type) {
        if (type == null) return false;
        String t = type.trim().toLowerCase();
        return t.equals("savings") || t.equals("current");
    }

    // Checks that a name isn't empty/blank
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // Checks that a withdrawal won't overdraw the account
    public static boolean hasSufficientBalance(double currentBalance, double withdrawAmount) {
        return currentBalance >= withdrawAmount;
    }
}