// import org.json.JSONArray;
// import org.json.JSONObject;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.List;

// public class ShamirSecretSharing {

//     public static void main(String[] args) {
//         if (args.length < 1) {
//             System.err.println("Usage: java ShamirSecretSharing <input_file>");
//             return;
//         }

//         String jsonInput =  "src/testcases2[1].json";
//         try {
//             jsonInput = new String(Files.readAllBytes(Paths.get(args[0])));
//         } catch (IOException e) {
//             System.err.println("Error reading input file: " + e.getMessage());
//             return;
//         }   

//         // Parse the JSON input
//         JSONObject jsonObject;
//         try {
//             jsonObject = new JSONObject(jsonInput);
//         } catch (Exception e) {
//             System.err.println("Error parsing JSON input: " + e.getMessage());
//             return;
//         }

//         JSONArray roots = jsonObject.getJSONArray("roots");

//         // Decode y values and store (x, y) pairs
//         List<Point> points = new ArrayList<>();
//         for (int i = 0; i < roots.length(); i++) {
//             JSONObject root = roots.getJSONObject(i);
//             int x = root.getInt("x");
//             int y;
//             try {
//                 y = decodeY(root.getString("y"));
//             } catch (Exception e) {
//                 System.err.println("Error decoding y value for root " + i + ": " + e.getMessage());
//                 continue;
//             }
//             points.add(new Point(x, y));
//         }

//         // Calculate the constant term c using Lagrange interpolation
//         double c = lagrangeInterpolation(points, 0);

//         // Output the secret c
//         System.out.println("The secret c is: " + c);
//     }

//     private static int decodeY(String y) {
//         return Integer.parseInt(y, 2); // Decode from binary to integer
//     }

//     private static double lagrangeInterpolation(List<Point> points, double x) {
//         double result = 0.0;
//         for (int i = 0; i < points.size(); i++) {
//             double term = points.get(i).y;
//             for (int j = 0; j < points.size(); j++) {
//                 if (i != j) {
//                     term *= (x - points.get(j).x) / (points.get(i).x - points.get(j).x);
//                 }
//             }
//             result += term;
//         }
//         return result;
//     }

//     static class Point {
//         int x;
//         double y;

//         Point(int x, double y) {
//             this.x = x;
//             this.y = y;
//         }
//     }
// }


import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ShamirSecretSharing {

    public static void main(String[] args) {
        // Path to the JSON file (modify path if needed)
        String filePath = "C:\\Users\\ASHOK\\OneDrive\\Desktop\\ ShamirSecretSharing\\src\\testcase2.json";  // Make sure this path is correct

        // Read the JSON data from the file
        String jsonInput;
        try {
            jsonInput = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Parse the JSON data
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonInput);
        } catch (Exception e) {
            System.err.println("Error parsing JSON input: " + e.getMessage());
            return;
        }

        // Extract n and k
        int n = jsonObject.getJSONObject("keys").getInt("n");
        int k = jsonObject.getJSONObject("keys").getInt("k");
        System.out.println("n: " + n);
        System.out.println("k: " + k);

        // Store the extracted values
        Map<Integer, Long> values = new HashMap<>();

        // Iterate through the keys (1, 2, ..., n) and convert the values based on their base
        for (int i = 1; i <= n; i++) {
            JSONObject secret = jsonObject.getJSONObject(String.valueOf(i));
            String base = secret.getString("base");
            String value = secret.getString("value");

            // Convert the value based on its base
            long convertedValue = convertValue(value, base);
            values.put(i, convertedValue);

            System.out.println("Secret " + i + " (Base " + base + "): " + value + " -> Converted: " + convertedValue);
        }

        // You can now use the `values` map in your project logic, e.g., for secret sharing
    }

    // Method to convert a string value from its respective base to a decimal (long)
    private static long convertValue(String value, String base) {
        int radix;
        switch (base) {
            case "10":  // Decimal
                radix = 10;
                break;
            case "16":  // Hexadecimal
                radix = 16;
                break;
            case "12":  // Base-12
                radix = 12;
                break;
            case "11":  // Base-11
                radix = 11;
                break;
            case "14":  // Base-14
                radix = 14;
                break;
            case "9":  // Base-9
                radix = 9;
                break;
            case "8":  // Octal
                radix = 8;
                break;
            default:
                throw new IllegalArgumentException("Unsupported base: " + base);
        }
        return Long.parseLong(value, radix);
    }
}
