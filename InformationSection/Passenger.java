package InformationSection;

import PathDatabase.Path;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Passenger {

    private static final Hashtable<String, Hashtable<String, String>> database = new Hashtable<>();
    private static final String CURRENT_USER_KEY = "current_user";
    private static List<Path> CURRENT_PATH = null;
    private static Hashtable<String, String> currentUserInfo;

    public static void addPassenger(String firstName, String lastName, String phoneNumber,
                                           String emailAddress, String passportNumber, String validityDate) {
        if (!database.containsKey(CURRENT_USER_KEY)) {
            Hashtable<String, String> passengerInfo = new Hashtable<>();
            passengerInfo.put("firstName", firstName);
            passengerInfo.put("lastName", lastName);
            passengerInfo.put("phoneNumber", phoneNumber);
            passengerInfo.put("emailAddress", emailAddress);
            passengerInfo.put("passportNumber", passportNumber);
            passengerInfo.put("validityDate", validityDate);
            passengerInfo.put("referenceNumber", generateReferenceNumber());
            database.put(CURRENT_USER_KEY, passengerInfo);
            currentUserInfo = passengerInfo;
        }
    }

    public static void setCurrentPath(List<Path> path){
        CURRENT_PATH = path;
    }
    
    public static Hashtable<String, String> getCurrentUserInfo() {
        if (database.containsKey(CURRENT_USER_KEY)) {
            return database.get(CURRENT_USER_KEY);
        } else {
            System.out.println("User not found: " + CURRENT_USER_KEY);
            return null;
        }
    }

    public static Hashtable<String, Hashtable<String, String>> getDatabase() {
        return new Hashtable<>(database);
    }
    
    public static Hashtable<String, String> getPassenger() {
        String currentUserKey = CURRENT_USER_KEY;
        return database.get(currentUserKey);
    }
    public static List<Path> getCurrentPath(){
        return CURRENT_PATH;
    }
    private static String generateKey(String firstName, String lastName) {
        return firstName.toLowerCase() + "_" + lastName.toLowerCase();
    }

    private static String generateReferenceNumber() {
        Random random = new Random();
        long randomNumber = Math.abs(random.nextLong(0, 10000000));
        return "REF#" + String.format("%010d", randomNumber);
    }
    
    public static String generateUserId(String firstName, String lastName) {
        return generateKey(firstName, lastName);
    }

    public static void printDatabase() {
        for (Map.Entry<String, Hashtable<String, String>> entry : database.entrySet()) {
            String key = entry.getKey();
            Hashtable<String, String> passengerInfo = entry.getValue();
            System.out.println("Key: " + key);
            System.out.println("Value: (Details below)");
            System.out.println("------------");
            System.out.println("First Name: " + passengerInfo.get("firstName"));
            System.out.println("Last Name: " + passengerInfo.get("lastName"));
            System.out.println("Phone Number: " + passengerInfo.get("phoneNumber"));
            System.out.println("Email Address: " + passengerInfo.get("emailAddress"));
            System.out.println("Passport Number: " + passengerInfo.get("passportNumber"));
            System.out.println("Validity Date: " + passengerInfo.get("validityDate"));
            System.out.println("Reference Number: " + passengerInfo.get("referenceNumber"));
        }
    }
}
