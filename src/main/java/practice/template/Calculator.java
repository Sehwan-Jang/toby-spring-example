package practice.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String path) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }

            br.close();
            return sum;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
