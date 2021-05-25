package practice.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String path) throws IOException {
        return lineReadTemplate(path, (line, value) -> value + Integer.parseInt(line));
    }

    public Integer calcTimes(String path) throws IOException {
        return lineReadTemplate(path, (line, value) -> value * Integer.parseInt(line));
    }

    private Integer lineReadTemplate(String path, LineCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            Integer sum = Integer.parseInt(line);
            while ((line = br.readLine()) != null) {
                sum = callback.doSthWithLine(line, sum);
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
