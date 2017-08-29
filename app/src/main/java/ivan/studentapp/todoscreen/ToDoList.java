package ivan.studentapp.todoscreen;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * activity to get data from text file
 */

public class ToDoList extends ArrayList<TodoTask> {
    private static String _fileName = "Tasks.txt";

    public void SaveToFile(String directory) throws IOException {
        File root = new File(Environment.getExternalStorageDirectory(), directory);
        if (!root.exists()) {
            root.mkdirs();
        }

        File file = new File(root, _fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream fw = null;
        try {
            fw = new PrintStream(file);
            for (TodoTask task : this) {
                fw.println(task.toString());
            }
        } catch (Exception ex) {
            //todo_subjectsscreen: handel ex
        } finally {
            fw.flush();
            fw.close();
        }
    }

    public void LoadFromFile(String directory) throws IOException {
        File root = new File(Environment.getExternalStorageDirectory(), directory);
        if (root.exists()) {
            File file = new File(root, _fileName);
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String tas[] = line.split(",", -1);
                    this.add(new TodoTask(tas[0], tas[1].startsWith("true"), tas[2], tas[3], tas[4], tas[5]));
                }
            }
        }
    }
}
