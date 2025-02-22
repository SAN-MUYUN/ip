package muyunbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import muyunbot.exceptions.TimeTravelException;
import muyunbot.tasks.Deadline;
import muyunbot.tasks.Event;
import muyunbot.tasks.Task;
import muyunbot.tasks.Todo;


/**
 * Handles writing and reading data from files.
 */
public class Storage {
    final String FILEPATH = "src/data/record.txt";
    private File file;
    private Parser parser;
    private Ui ui;

    /**
     * Constructs a Storage object using filepath.
     */
    public Storage(Ui ui) {
        this.file = new File(FILEPATH);
        this.parser = new Parser();
        this.ui = ui;
    }

    /**
     * Checks if the required folder path and file path are valid.
     * If required file path and folder path are not present, create the required folder and file path.
     */
    public void initFile() {
        String directoryPath = "src/data";
        String filePath = this.FILEPATH;

        // Create folder if it does not exist.
        File f = new File(directoryPath);
        if (!f.exists()) {
            if (f.mkdirs()) {
                System.out.println("new directory created");
            } else {
                System.out.println("Cannot create new directory");
            }
        }
        //create File if it does not exist;
        f = new File(FILEPATH);
        try {
            if (file.createNewFile()) {
                this.ui.display("MuyunBot.Storage file was created.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * Writes content into the record.txt file.
     * @param content Content to be written into the file.
     */
    public void writeFile(String content) {
        try {
            FileWriter fw = new FileWriter(this.FILEPATH, true);
            fw.write(content + System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Writes all tasks in the list into the file.
     * @param list a list containing all the tasks.
     */
    public void updateFile(ArrayList<Task> list) throws IOException {
        StringBuilder updatedContent = new StringBuilder();
        File file = new File(this.FILEPATH);
        assert file.exists() : "file does not exist";
        for (Task task : list) {
            updatedContent.append(task.toObjStr() + System.lineSeparator());
        }

        FileWriter fw = new FileWriter(file);
        fw.write(updatedContent.toString());
        fw.close();

    }

    /**
     * Converts a string into a Task object.
     * @param content String representation of a Task.
     * @return Task
     * @throws DateTimeParseException If datetime in the content cannot be parsed into LocalDate object.
     */
    public Task strToTask(String content) throws DateTimeParseException, TimeTravelException {

        String[] parsed = content.split("\\|");
        String symbol = parsed[0];
        switch(symbol) {
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

    /**
     * Reads data from the record.txt, convert the text into new Tasks and adds the tasks into a new ArrayList of Task.
     * Returns the new Arraylist of Tasks in the end.
     * @return
     * @throws FileNotFoundException If the FILEPATH is invalid and cannot be reached
     * @throws DateTimeParseException If the DateTime passed in by user cannot be parsed properly
     */
    public ArrayList<Task> syncTaskList() throws FileNotFoundException, DateTimeParseException, TimeTravelException {
        File f = new File(FILEPATH); // create a File for the given file path
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

    /**
     * Deletes and creates a new file in the event that the current record.txt file is corrupted.
     */
    public void rebuildFile() {
        this.file.delete();
        initFile();
    }

    /**
     * Returns a new TaskList containing all tasks from the file.
     * @param storage Storage used to initialise a file and creates the new TaskList object.
     * @return new TaskList.
     */
    protected TaskList sync(Storage storage) {
        storage.initFile();
        try {
            return new TaskList(storage, syncTaskList(), this.ui);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            this.ui.display("File is corrupted, deleting current file and creating new file");
            this.rebuildFile();
        } catch (TimeTravelException e) {
            this.ui.display("File is corrupted, deleting current file and creating new file");
        }
        return new TaskList(storage, this.ui);
    }

}
