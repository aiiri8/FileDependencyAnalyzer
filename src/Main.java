import graph.Graph;

import graph.SortingResult;
import io.FileController;
import io.InputController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс приложения. Здесь описывается логика работы, вызываются методы других классов.
 */
public class Main {

  /**
   * Точка входа. Описание основного процесса программы, который виден пользователю.
   *
   * @param args аргументы вызова (мы не используем).
   */
  public static void main(String[] args) {
    try {
      while (true) {
        if (InputController.getState()) {
          File rootDirectory = InputController.getRootDirectory();

          List<File> files = new ArrayList<>();
          FileController.getAllFiles(rootDirectory, files);

          Graph graph = new Graph(files);
          for (File file : files) {
            List<File> requires = FileController.findRequires(file, rootDirectory);

            for (File parent : requires) {
              graph.addRelation(parent, file);
            }
          }

          SortingResult sortingResult = graph.sortGraph();

          if (sortingResult.isSorted()) {
            System.out.println("Сортировка удалась, объединенный файл: ");

            String concatenationResult = FileController.concatenateFiles(sortingResult.result());
            System.out.println(concatenationResult);

            FileController.writeFile(concatenationResult);
          } else {
            System.out.println("Не получилось отсортировать, первый найденный цикл: ");

            for (int i = 0; i < sortingResult.result().size() - 1; ++i) {
              System.out.println(i + 1 + ". " + sortingResult.result().get(i));
            }
          }
        } else {
          break;
        }
      }

      System.out.println("До новых встреч!");
    } catch (Exception e) {
      System.out.println();
      System.out.println("Критическая ошибка! Что-то пошло не так:(");
      System.out.println(e.getMessage());
    }
  }
}