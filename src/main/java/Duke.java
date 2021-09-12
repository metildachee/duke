import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;


public class Duke {
    private final static String DETECT_END = "bye";
    private final static String DETECT_LIST = "list";
    private final static String DETECT_DONE = "done";
    private final static String DETECT_ADD_TODO = "todo";
    private final static String DETECT_ADD_EVENT = "event";
    private final static String DETECT_ADD_DEADLINE = "deadline";
    private final static String STMT_END = "Bye. Hope to see you again soon!";
    private final static String STMT_START = "Hello! I'm Duke\nWhat can I do for you?";
    private final static String STMT_DONE = "Nice! I've marked this task as done: ";

    private final static String ERROR_PREFIX = "Oops I did not quite understand that.";

    public static void list(ArrayList<Task> list) {
        list.forEach(l -> System.out.println(l.toString()));
    }

    public static void add(ArrayList<Task> list, Task todo) {
        list.add(todo);
        System.out.println("added: " + todo.toString());
    }

    public static void markDone(ArrayList<Task> list, Integer taskId) {
        list.stream().filter(t -> t.getId().equals(taskId)).forEach(t -> {
            System.out.println(STMT_DONE);
            Todo task = (Todo) t;
            task.setDone(true);
            System.out.println(t.toString());
        });
    }

    public static void printErrorMessage(Message m) {
        switch (m) {
            case ERROR_UNRECOGNISED:
                System.out.println(ERROR_PREFIX + "Unrecognised operation.");
                break;
            case ERROR_INVALID:
                System.out.println(ERROR_PREFIX + "Invalid input.");
                break;
        }
    }


    public static void main(String[] args) {
        System.out.println(STMT_START);
        Scanner in = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();
        String input = in.nextLine();
        ArrayList<String> tokens = new ArrayList(Arrays.asList(input.split(" ")));
        String instruction = tokens.get(0);
        Integer id = 0;
        String taskInfo = "";

        while (!instruction.toLowerCase(Locale.ROOT).equals(DETECT_END)) {
            if (instruction.toLowerCase(Locale.ROOT).equals(DETECT_LIST)) {
                list(list);
            } else if (instruction.toLowerCase(Locale.ROOT).equals(DETECT_DONE) && tokens.size() >= 2) {
                markDone(list, Integer.parseInt((tokens.get(1))));
            } else {
                Task task = null;
                switch (instruction) {
                    case DETECT_ADD_TODO:
                        System.out.println(taskInfo);
                        task = new Todo(taskInfo, id);
                        break;
                    case DETECT_ADD_EVENT:
                        ArrayList<String> addInfo = new ArrayList(Arrays.asList(taskInfo.split("/at")));
                        task = new Event(addInfo.get(0), addInfo.get(1), id);
                        break;
                    case DETECT_ADD_DEADLINE:
                        ArrayList<String> deadlineInfo = new ArrayList(Arrays.asList(taskInfo.split("/by")));
                        task = new Deadline(deadlineInfo.get(0), deadlineInfo.get(1), id);
                        break;
                }

                if (task == null) {
                    printErrorMessage(Message.ERROR_UNRECOGNISED);
                }
                add(list, task);
            }

            input = in.nextLine();
            tokens = new ArrayList(Arrays.asList(input.split(" ")));
            instruction = tokens.get(0);
            taskInfo = tokens.subList(1, tokens.size()).toString();
            id = list.size();
        }
        System.out.println(STMT_END);
    }
}
