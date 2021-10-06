package versand.core;

public interface CsvSerializable<T> {

    String toCsv(char splitChar);

    default T fromCsv(char splitChar, String s) {
        String[] split = s.split(splitChar + "");
        return fromCsv(splitChar, split);
    }

    T fromCsv(char splitChar, String[] s);

}
