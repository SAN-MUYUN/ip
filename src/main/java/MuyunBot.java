import java.util.Scanner;

public class MuyunBot {

    private static Task[] taskList = new Task[100];
    private static int listLength = 0;

    private static void greet() {
//        display greeting when starting the bot;

        String text = PrintStyle.dashedLines()
                + PrintStyle.indent("Hello! I'm MuyunBot")
                + PrintStyle.indent("What can I do for you?")
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    private static void quit() {
//        display ending message when closing the bot;
        String text = PrintStyle.dashedLines()
                + PrintStyle.indent("Bye! Hope to see you soon!")
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    private static void addTask(String input) {
        Task newTask = new Task(input);
        taskList[listLength] = newTask;
        listLength++;
        display(PrintStyle.indent("added: " + input));
    }

    private static void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < listLength; i++) {
            listContent.append(PrintStyle.indent(
                    String.valueOf(i + 1) + ". " + taskList[i].toString()));
        }
        display(listContent.toString());
    }

    private static void display(String x) {
//        display x in proper style;
        String text = PrintStyle.dashedLines()
                + x
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        String input = scanner.nextLine();
//        continue echoing until user inputs "bye";
        while(!input.equals("bye")) {
            if (input.equals("list")) {
                showList();
            } else {
                addTask(input);
            }
            input = scanner.nextLine();
        }
        quit();

    }

}

