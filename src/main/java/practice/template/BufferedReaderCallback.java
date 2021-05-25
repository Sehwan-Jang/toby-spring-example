package practice.template;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSthWithReader(BufferedReader bufferedReader) throws IOException;
}