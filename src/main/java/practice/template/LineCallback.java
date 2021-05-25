package practice.template;

public interface LineCallback<T> {
    T doSthWithLine(String line, T value);
}
