# FileDependencyAnalyzer

Программа анализа зависимостей текстовых файлов в заданной директории.

По введенному пути к корневой папке поиска находит все файлы в ней и подпапках, анализирует их содержимое (ищутся строки вида `require '...'`), производит топологическую сортировку графа с вершинами - найденныыми файлами и ребрами - зависимостями.

Пользователь либо получает конкатенацию файлов (вывод на экран + сохранение в файле `result.txt` в папке проекта), либо сообщение об ошибке и список файлов в первом обнаруженном цикле.

Небольшие уточнения условия задачи:
- Используется формат `require '...'`, а не `require ‘...’` для упрощения создания примеров;
- В теле `require '...'` ожидается **относительный** путь с **указанным расширением файла** (например, не `require ‘Folder 1/File 1-1’`, а `require 'Folder 1/File 1-1.txt'`) - так как программа специально не проверяет файлы на соответствие формату .txt, может возникнуть проблема с автоматическим определением;
- Программа может отловить некоторые проблемы при работе с файлами (например, при чтении путя к директории поиска), но, так как работа с исключениями не указывалась в условии, многие исключения пробрасываются.
- Программа не распознает `require   '...'`, `reQUiRE '...'`, `require "..."`, `require ...`, `Require '...' lalala` и т.д.!

В папке `examples` расположены примеры для проверки работы программы.

Компоненты программы:
- `Main.java` - точка входа, основной процесс программы;
- `io` - отвечает за пользовательский ввод и работу с файлами и директориями (чтение/запись, поиск всех файлов, поиск зависимостей):
  - `InputController.java` - класс, отвечающий за ввод пользователя (и его проверку);
  - `FileController.java` - класс, отвечающий за работу с файлами - поиск всех файлов в подпапках, анализ зависимостей, чтение из файлов, запись конкатенированного файла;
- `graph` - отвечает за граф из файлов, в котором мы проводим топологическую сортировку:
  - `Graph.java` - сам граф с методами для выполнения сортировки;
  - `Mark.java` - перечисление состояний вершин графа при сортировке;
  - `SortingResult.java` - пара для хранения результата сортировки;
  - `VisitingResult.java` - пара дл хранения результата посещения вершины при сортировке.