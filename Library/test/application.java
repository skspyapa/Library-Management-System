import java.io.File;

public class application {
    public static void main(String[] args) {
        File file = new File("Library/src/resources");
        System.out.println(file.getAbsolutePath());
    }
}
