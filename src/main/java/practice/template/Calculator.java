package practice.template;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String path) throws IOException {
        return lineReadTemplate(path, (line, value) -> value + Integer.parseInt(line), 0);
    }

    public Integer calcTimes(String path) throws IOException {
        return lineReadTemplate(path, (line, value) -> value * Integer.parseInt(line), 1);
    }

    public String concatenate(String path) throws IOException {
        return lineReadTemplate(path, (line, value) -> value + line, "");
    }

    private <T> T lineReadTemplate(String path, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String line = null;
            T sum = initVal;
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
