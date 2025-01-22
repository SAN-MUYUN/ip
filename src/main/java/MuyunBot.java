import java.util.Scanner;

public class MuyunBot {

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

    private static void echo(String input) {
        display(input);
    }

    private static void display(String x) {
//        display x in proper style;
        String text = PrintStyle.dashedLines()
                + PrintStyle.indent(x)
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greet();
        String input = scanner.nextLine();
//        continue echoing until user inputs "bye";
        while(!input.equals("bye")) {
            echo(input);
            input = scanner.nextLine();
        }
        quit();

    }

}

