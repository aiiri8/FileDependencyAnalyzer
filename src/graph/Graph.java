package graph;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Класс, отвечающий за граф файлов (для их сортировки).
 */
public class Graph {

  /**
   * Словарь файлов - их отметок для сортировки.
   */
  private final Map<File, Mark> files;

  /**
   * Словарь файлов - множеств их наследников (ребра графа).
   */
  private final Map<File, HashSet<File>> relations;

  /**
   * Конструктор класса, принимает все его вершины.
   *
   * @param files список файлов - вершин.
   */
  public Graph(List<File> files) {
    this.files = new HashMap<>();
    for (File file : files) {
      this.files.put(file, Mark.UNMARKED);
    }

    relations = new HashMap<>();
  }

  /**
   * Метод, отвечающий за добавление ребра в граф.
   *
   * @param parent файл, от которого идет ребро (родитель).
   * @param child  файл, в который идет ребро (наследник).
   */
  public void addRelation(File parent, File child) {
    if (checkRelation(parent, child)) {
      if (!relations.containsKey(parent)) {
        relations.put(parent, new HashSet<>());
      }
      relations.get(parent).add(child);
    }
  }

  /**
   * Метод, проверяющий ребро на корректность (обе вершины - вершины графа).
   *
   * @param parent файл - родитель.
   * @param child  файл - наследник.
   * @return true, если ребро корректное, иначе false.
   */
  public boolean checkRelation(File parent, File child) {
    return files.containsKey(parent) && files.containsKey(child);
  }

  /**
   * Метод, сортирующий граф (топологическая сортировка).
   *
   * @return запись с результатами сортировки, подробнее в описании записи.
   */
  public SortingResult sortGraph() {
    List<File> sortedFiles = new ArrayList<>();
    files.replaceAll((f, v) -> Mark.UNMARKED);

    for (File file : files.keySet()) {
      if (files.get(file) == Mark.UNMARKED) {
        VisitingResult visitingResult = visit(sortedFiles, file, new ArrayList<>());

        if (visitingResult.isCycled()) {
          return new SortingResult(false, visitingResult.parents());
        }
      }
    }

    return new SortingResult(true, sortedFiles);
  }

  /**
   * Метод, отвечающий за посещение файла - вершины в процессе сортировки.
   *
   * @param sortedFiles уже просмотренные файлы, при сортировке файл добавится сюда.
   * @param file        посещаемый файл.
   * @param parents     родители файла (уже посещенные файлы для данного прохода).
   * @return
   */
  private VisitingResult visit(List<File> sortedFiles, File file, List<File> parents) {
    List<File> newParents = new ArrayList<>(parents);
    newParents.add(file);

    if (files.get(file) == Mark.PERMANENT) {
      return new VisitingResult(false, newParents);
    }

    if (files.get(file) == Mark.TEMPORARY) {
      return new VisitingResult(true, newParents);
    }

    if (!relations.containsKey(file)) {
      files.put(file, Mark.PERMANENT);
      sortedFiles.add(0, file);
      return new VisitingResult(false, newParents);
    }

    files.put(file, Mark.TEMPORARY);

    for (File childFile : relations.get(file)) {
      VisitingResult childResult = visit(sortedFiles, childFile, newParents);
      if (childResult.isCycled()) {
        return new VisitingResult(true, childResult.parents());
      }
    }

    files.put(file, Mark.PERMANENT);
    sortedFiles.add(0, file);
    return new VisitingResult(false, newParents);
  }
}
