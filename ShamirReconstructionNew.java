import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ShamirReconstructionNew {

    // Convert value string in a given base to decimal
    private static double convertToDecimal(String value, int base) {
        return (double) Integer.parseInt(value, base);
    }

    private static double reconstructC(List<double[]> points) {
        int n = points.size();
        double c = 0;
        for (int i = 0; i < n; i++) {
            double xi = points.get(i)[0], yi = points.get(i)[1];
            double num = 1, den = 1;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                double xj = points.get(j)[0];
                num *= -xj;
                den *= (xi - xj);
            }
            c += yi * (num / den);
        }
        return c;
    }

    private static void combination(List<double[]> pts, int[] indices, int start, int[] combo, int depth, List<Double> results) {
        if (depth == combo.length) {
            List<double[]> selected = new ArrayList<>();
            for (int idx : combo) selected.add(pts.get(idx));
            results.add(reconstructC(selected));
            return;
        }
        for (int i = start; i <= indices.length - (combo.length - depth); i++) {
            combo[depth] = indices[i];
            combination(pts, indices, i + 1, combo, depth + 1, results);
        }
    }

    public static void main(String[] args) throws Exception {
        String jsonPath = "shares_new.json"; // your new input file
        String content = new String(Files.readAllBytes(Paths.get(jsonPath)));
        JsonObject obj = JsonParser.parseString(content).getAsJsonObject();

        int n = obj.getAsJsonObject("keys").get("n").getAsInt();
        int k = obj.getAsJsonObject("keys").get("k").getAsInt();

        List<double[]> pts = new ArrayList<>();
        for (String key : obj.keySet()) {
            if (key.equals("keys")) continue;
            JsonObject share = obj.getAsJsonObject(key);
            int base = Integer.parseInt(share.get("base").getAsString());
            String value = share.get("value").getAsString();
            double y = convertToDecimal(value, base);
            double x = Double.parseDouble(key); // use key as x
            pts.add(new double[]{x, y});
        }

        List<Double> cCandidates = new ArrayList<>();
        int[] indices = new int[pts.size()];
        for (int i = 0; i < pts.size(); i++) indices[i] = i;

        combination(pts, indices, 0, new int[k], 0, cCandidates);

        Map<Long, Integer> countMap = new HashMap<>();
        for (double val : cCandidates) {
            long r = Math.round(val);
            countMap.put(r, countMap.getOrDefault(r, 0) + 1);
        }
        long finalC = countMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        // Print only final secret
        System.out.println(finalC);
    }
}
