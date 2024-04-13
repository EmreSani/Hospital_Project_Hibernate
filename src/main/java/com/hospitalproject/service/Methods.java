package Hospital_Project;

import java.io.IOException;

public interface Methods {
    void entryMenu() throws InterruptedException, IOException;
    void add () throws IOException, InterruptedException;
    void remove () throws IOException, InterruptedException;
    void list () throws IOException, InterruptedException;
}
