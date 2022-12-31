package io;

import java.io.File;
import java.util.Scanner;

/**
 * Класс, отвечающий за пользовательский ввод.
 */
public class InputController {

  /**
   * Сканер, через который читается ввод.
   */
  private final static Scanner scanner = new Scanner(System.in);

  /**
   * Метод для чтения ввода пользователя о том, нужно ли продолжать работу программы.
   *
   * @return true, если продолжается работа; false, если введен exit и нужно завершиться.
   */
  public static boolean getState() {
    System.out.println(
        "Введите exit, чтобы завершить программу, любую другую строку для продолжения работы:");

    String input;
    input = scanner.nextLine().strip().toLowerCase();

    return !input.equals("exit");
  }

  /**
   * Метод для чтения пути к корневой папке.
   *
   * @return файл - корневую папку.
   */
  public static File getRootDirectory() {
    System.out.println("Введите путь к корневой папке:");

    while (true) {
      String input = scanner.nextLine().strip();
      File directory = new File(input);

      try {
        if (directory.isDirectory()) {
          return directory;
        }

        System.out.println("По заданному пути не обнаружена папка, повторите попытку!");
      } catch (SecurityException e) {
        System.out.println(
            "Программа не может получить доступ к объекту по введенному пути, повторите попытку!");
      }
    }
  }
}
