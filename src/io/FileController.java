package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за работу с файлами.
 */
public class FileController {

  /**
   * Метод для получения списка всех файлов в папке и ее подпапках.
   *
   * @param rootDirectory файл - корневая директория.
   * @param files         - список уже найденных файлов (новые обнаруженные дописываются сюда).
   */
  public static void getAllFiles(File rootDirectory, List<File> files) {
    File[] allFiles = rootDirectory.listFiles();

    if (allFiles == null) { // Ну null и null...
      return;
    }

    for (File file : allFiles) {
      if (file.isDirectory()) {
        getAllFiles(file, files);
      } else {
        files.add(file);
      }
    }
  }

  /**
   * Метод, отвечающий за нахождение всех необходимых файлов для выведения данного.
   *
   * @param file          рассматриваемый файл.
   * @param rootDirectory корневая директория (для получения абсолютного пути, по условию в файлах
   *                      даны относительные)
   * @return список родителей данного файла.
   * @throws IOException при ошибке чтения файла.
   */
  public static List<File> findRequires(File file, File rootDirectory) throws IOException {
    List<File> result = new ArrayList<>();

    BufferedReader bufferedReader = new BufferedReader(
        new java.io.FileReader(file.getAbsolutePath()));
    while (bufferedReader.ready()) {
      String line = bufferedReader.readLine().strip();

      if (line.startsWith("require '") && line.endsWith("'") && line.length() > 10) {
        result.add(new File(rootDirectory, line.substring(9, line.length() - 1)));
      }
    }

    bufferedReader.close();
    return result;
  }

  /**
   * Файл, объединяющий файлы из списка в одну строку (в порядке их появления в списке).
   *
   * @param files список файлов.
   * @return строку с содержимым всех файлов.
   * @throws IOException при ошибке чтения, пробрасывается из readFile.
   */
  public static String concatenateFiles(List<File> files) throws IOException {
    String result = "";
    for (File file : files) {
      result += readFile(file);
    }
    return result;
  }

  /**
   * Метод, отвечающий за чтение файла.
   *
   * @param file нужный файл.
   * @return строку с содержимым файла.
   * @throws IOException ошибка при чтении файла.
   */
  private static String readFile(File file) throws IOException {
    String result = "";
    BufferedReader bufferedReader = new BufferedReader(
        new java.io.FileReader(file.getAbsolutePath()));
    while (bufferedReader.ready()) {
      result += bufferedReader.readLine() + "\n";
    }

    bufferedReader.close();
    return result;
  }

  /**
   * Метод, записывающий результат конкатенации файлов в фиксированный файл ("result.txt" в папке
   * проекта).
   *
   * @param concateResult строка с результатом конкатенации.
   * @throws IOException ошибка при записи файла.
   */
  public static void writeFile(String concateResult) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result.txt"));
    bufferedWriter.write(concateResult);
    bufferedWriter.close();
  }
}
