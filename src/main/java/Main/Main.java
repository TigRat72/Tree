package Main;
import org.apache.commons.csv.CSVParser;
import java.io.*;
import java.nio.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        long begTime = System.currentTimeMillis();
        String fOut = "Output.csv";
        String fIn = null;
        System.out.println("Written by Petrosyants Lev");
        Set<Node> clearData = new LinkedHashSet<>();
        switch (args.length) {
            case 0:
                System.out.println("Usage ... Tree.jar input.file output.file");
                System.exit(1);
                break;
            case 2:
                fOut = args[1];
            case 1:
                fIn = args[0];
                System.out.println("Input file  : " + fIn + "\nOutput file : " + fOut);
                break;
        }
        Trees trees = new Trees();
//      Открываем файл и считываем все данные в LinkedHashSet (чтобы дубликаты исключить),
//      попутно разбирая на валидность
        try (Scanner scannerIn = new Scanner(Paths.get(fIn));) {
            int siz = 0;
            System.out.println("Open file: " + fIn);
            System.out.print("Load data...");
            while (scannerIn.hasNextLine()) {
                Node node;
                boolean corr = true;
                String str = scannerIn.nextLine();
                String[] mass = str.split(";", -1);
                if (mass.length == 3) {
                    for (int i = 0; i < 3; i++) {
                        if (mass[i].length() > 0) {
                            String[] k = mass[i].split("\"", -1);
                            if (k.length == 3 && k[0].isEmpty() && k[2].isEmpty()) {
                                mass[i] = k[1];
                            } else {
                                corr = false;
                            }
                        }
                    }
                } else {
                    corr = false;
//                    System.out.println("Incorrect data at: " + siz + "\n" + str);    //
                }
                if (corr && !(mass[0].isEmpty() && mass[1].isEmpty() && mass[2].isEmpty())) {
                    siz++;
//                    if (siz % 1000 == 0) System.out.print(".");  //
                    node = new Node(mass[0], mass[1], mass[2]);
                    clearData.add(node);
                }
            }
            System.out.println("done.");
            System.out.println("Correct lines: " + siz + "\nTotal lines: " + clearData.size());
        } catch (IOException e) {
            System.out.println("WARNING! Some problems with your input files:\n" + fIn);
            e.printStackTrace();
        }
        //Добавляем все узлы в дерево
        System.out.print("Processing data...");
        for (Node n : clearData) {
            trees.AddNode(n);
        }

        List<Map.Entry<Ghost, Integer>> resultTable = trees.prepareGetGroups();
        System.out.println("done.");
        try (FileWriter writer = new FileWriter(fOut, false);) {
//            System.out.println("Число групп : " + resultTable.size());
            System.out.println("Output data into file : " + fOut);
            writer.write("Число групп : " + resultTable.size() + "\n");
            for (int i = 0; i < resultTable.size(); i++) {
                writer.write("Группа " + (i + 1) + "\n");
                List<Node> outTree = resultTable.get(i).getKey().getTree();
                for (Node n : outTree) {
                    writer.write(n.toString() + "\n");
                }
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("WARNING! Some problems with your output files:\n" + fOut);
            e.printStackTrace();
        }
        System.out.println("All done in: " + ((System.currentTimeMillis() - begTime) / 1000.) + " sec");
    }
}
