import classes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
    String filePath = "ip/src/data/record.txt";
    File file;
    Parser parser;

    public FileManager() {
        this.file = new File(filePath);
        this.parser = new Parser();
    }
    public void initFile() {
        String directoryPath = "ip/src/data";
        String filePath = this.filePath;

        // Create folder if it does not exist.
        File f = new File(directoryPath);
        if (!f.exists()) {
            if(f.mkdirs()) {
                System.out.println("new directory created");
            } else {
                System.out.println("Canno create new directory");
            }
        }
        //create File if it does not exist;
        f = new File(filePath);
        try {
            if (file.createNewFile()) {
                PrintStyle.display("Storage file was created.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    public void writeFile(String content) {
        try {
            FileWriter fw = new FileWriter(this.filePath, true);
            fw.write(content + System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateFile(ArrayList<Task> list) {
        StringBuilder updatedContent = new StringBuilder();
        for (Task task : list) {
            updatedContent.append(task.toObjStr() + System.lineSeparator());
        }
        try {
            FileWriter fw = new FileWriter(this.filePath);
            fw.write(updatedContent.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Task strToTask(String content) throws DateTimeParseException {

        String[] parsed = content.split("\\|");
        String symbol = parsed[0];
        switch(symbol){
            case("T"):
                return new Todo(parsed[2], Integer.parseInt(parsed[1]) == 1);
            case("D"):
                return new Deadline(parsed[2], parsed[3], Integer.parseInt(parsed[1]) == 1);
            case ("E"):
                return new Event(parsed[2], parsed[3], parsed[4], Integer.parseInt(parsed[1]) == 1);
            default:
                return null;
        }
    }

    public ArrayList<Task> syncTaskList() throws FileNotFoundException, DateTimeParseException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f);
        ArrayList<Task> result = new ArrayList<>();
        while (s.hasNext()) {
            Task curr = this.strToTask(s.nextLine());
            if (curr != null) {
                result.add(curr);
            }
        }
        return result;
    }

    public void rebuildFile() {
        this.file.delete();
        this.initFile();
    }

}
