open class ArchiveMenu {
    val archives = mutableMapOf<String, MutableList<Note>>()

    fun addArchive(archiveName: String) {
        if (archiveName.isNotBlank() && archives.putIfAbsent(archiveName, mutableListOf()) == null) {
            println("Архив '$archiveName' успешно создан.")
        } else {
            println("Архив с таким именем уже существует или введено пустое имя.")
        }
    }

    fun viewArchives() {
        println("Список архивов:")
        archives.keys.forEach(::println)
    }

    fun addNoteToArchive(archiveName: String, noteTitle: String, noteContent: String) {
        val notes = archives[archiveName]
        when {
            archiveName.isBlank() || noteTitle.isBlank() || noteContent.isBlank() ->
                println("Название архива, название заметки или содержание заметки не может быть пустым.")
            notes == null -> println("Архив '$archiveName' не найден.")
            notes.any { it.title == noteTitle } -> println("Заметка с таким названием уже существует в архиве.")
            else -> {
                notes.add(Note(noteTitle, noteContent))
                println("Заметка '$noteTitle' добавлена в архив '$archiveName'.")
            }
        }
    }

    fun viewNotesInArchive(archiveName: String) {
        archives[archiveName]?.let { notes ->
            println("Заметки в архиве '$archiveName':")
            notes.forEachIndexed { index, note -> println("${index + 1}. ${note.title}: ${note.content}") }
        } ?: println("Архив '$archiveName' не найден.")
    }

    fun editNoteInArchive(archiveName: String, noteIndex: Int, newTitle: String, newContent: String) {
        val notes = archives[archiveName]
        if (notes == null || noteIndex !in 1..notes.size || newTitle.isBlank() || newContent.isBlank()) {
            println("Некорректный ввод.")
        } else {
            notes[noteIndex - 1] = Note(newTitle, newContent)
            println("Заметка обновлена.")
        }
    }
}