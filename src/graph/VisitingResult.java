package graph;

import java.io.File;
import java.util.List;

/**
 * Рекорд, хранящий результат посещения файла в графе.
 *
 * @param isCycled true, если обнаружен цикл, иначе false.
 * @param parents  список пройденных файлов до данного (его родитель и родители родителей).
 */
public record VisitingResult(boolean isCycled, List<File> parents) {

}
