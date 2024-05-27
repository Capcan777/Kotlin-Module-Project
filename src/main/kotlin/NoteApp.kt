import java.util.Scanner
import java.util.Stack

class NoteApp : ArchiveMenu() {
    private val scanner = Scanner(System.`in`)
    private val navigationStack = Stack<MenuState>()

    fun navigateTo(state: MenuState) {
        navigationStack.push(state)
    }

    fun backToPreviousState() {
        if (navigationStack.size > 1) {
            navigationStack.pop()
        }
    }

    fun render() {
        while (true) {
            val menuState = navigationStack.peek()
            when (menuState) {
                MenuState.ARCHIVE -> {
                    println(
                        """
                        Главное меню:
                        1. Создать архив
                        2. Посмотреть архивы
                        3. Выйти из программы
                    """.trimIndent()
                    )
                }
                MenuState.NOTE -> {
                    println(
                        """
                        Меню заметок:
                        1. Создать заметку
                        2. Посмотреть заметки
                        3. Редактировать заметку
                        4. Вернуться в главное меню
                    """.trimIndent()
                    )
                }
            }

            val command = scanner.nextLine().toIntOrNull() ?: -1
            when (menuState) {
                MenuState.ARCHIVE -> handleArchiveMenu(command)
                MenuState.NOTE -> handleNoteMenu(command)
            }
        }
    }

    private fun handleArchiveMenu(command: Int) {
        when (command) {
            1 -> {
                println("Введите название архива:")
                addArchive(scanner.nextLine().trim())
            }
            2 -> {
                viewArchives()
                println("Введите название архива для работы с заметками:")
                val archiveName = scanner.nextLine().trim()
                if (archives.containsKey(archiveName)) {
                    navigateTo(MenuState.NOTE)
                } else {
                    println("Архив не найден.")
                }
            }
            3 -> System.exit(0)
            else -> println("Неверный ввод, попробуйте еще раз.")
        }
    }

    private fun handleNoteMenu(command: Int) {
        when (command) {
            1 -> {
                println("Введите название архива для добавления заметки:")
                val archiveName = scanner.nextLine().trim()
                println("Введите название заметки:")
                val noteTitle = scanner.nextLine().trim()
                println("Введите содержание заметки:")
                val noteContent = scanner.nextLine().trim()
                addNoteToArchive(archiveName, noteTitle, noteContent)
            }
            2 -> {
                println("Введите название архива для просмотра заметок:")
                viewNotesInArchive(scanner.nextLine().trim())
            }
            3 -> {
                println("Введите название архива для редактирования заметки:")
                val archiveName = scanner.nextLine().trim()
                println("Введите номер заметки для редактирования:")
                val noteIndex = scanner.nextLine().toIntOrNull()
                println("Введите новое название заметки:")
                val newTitle = scanner.nextLine().trim()
                println("Введите новое содержание заметки:")
                val newContent = scanner.nextLine().trim()
                if (noteIndex != null) editNoteInArchive(archiveName, noteIndex, newTitle, newContent)
            }
            4 -> backToPreviousState()
            else -> println("Неверный ввод, попробуйте еще раз.")
        }
    }

    fun start() {
        navigateTo(MenuState.ARCHIVE)
        render()
    }
}