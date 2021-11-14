import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class UI {
    private final static String STMT_END = "Bye. Hope to see you again soon!";

    private final static String ERROR_PREFIX = "Oops I did not quite understand that.";

    public UI(TaskManager m) {
        this.manager = m;
    }

    Parser parser = new Parser();
    Scanner in = new Scanner(System.in);
    String input = "";
    Boolean isExit = false;
    TaskManager manager;

    public String getGreeting() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String msg = "";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            msg = "Good morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            msg = "Good afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            msg = "Good evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            msg = "Good night";
        }
        msg += "! I'm Dukie~\nWhat can I do for you?";
        return msg;
    }

    public void start() {
        System.out.println(getGreeting());
    }

    public String getInput() {
        input = in.nextLine();
        return input;
    }

    public void end() {
        System.out.println(STMT_END);
    }

    public void parseInput() {
        parser.parseInput(input);
    }

    public Boolean isExit() {
        return isExit;
    }

    public void printErrorMessage(Message m) {
        switch (m) {
            case ERROR_UNRECOGNISED:
                System.out.println(ERROR_PREFIX + " Unrecognised operation.");
                break;
            case ERROR_INVALID:
                System.out.println(ERROR_PREFIX + " Invalid input.");
                break;
            case EMPTY_LIST:
                System.out.println(ERROR_PREFIX + " Empty list.");
                break;
            case UNKNOWN_OBJECT:
                System.out.println(ERROR_PREFIX + " Could not find resource.");
                break;
        }
    }

    public void processInput() {
        String instruction = parser.getInstruction();
        String taskInfo = parser.getTaskInfo();
        String firstToken = parser.getFirstToken();

        if (parser.isExit()) {
            isExit = true;
            return;
        }

        if (parser.isList()) {
            manager.listTasks();
        } else if (parser.isDone()) {
            manager.markTaskDone(Integer.parseInt((firstToken)));
        } else if (parser.isDelete()) {
            manager.deleteTask(Integer.parseInt((firstToken)));
        } else if (parser.isFind()) {
            ArrayList<Task> li = manager.findTask(firstToken);
            manager.listTasks(li);
        } else if (parser.isView()) {
            manager.viewTaskOn(parser.stringToDate(firstToken));
        } else {
            Task t = manager.createTask(taskInfo, instruction);
            System.out.println("Got it! 👌 I've add this task: \n" + t.toString() + "\nNow you have " + manager.getNumOfTasks() + " tasks in the list.");
        }
    }
}
