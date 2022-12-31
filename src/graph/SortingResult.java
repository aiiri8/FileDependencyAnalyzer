package graph;

import java.io.File;
import java.util.List;

/**
 * Рекорд, хранящий результат топологической сортировки графа.
 *
 * @param isSorted true, если сортировка удалась, иначе false.
 * @param result   если сортировка удалась, сортированный список файлов, иначе список файлов в
 *                 первом найденном цикле.
 */
public record SortingResult(boolean isSorted, List<File> result) {

}
