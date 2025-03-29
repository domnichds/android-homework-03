package com.example.library

// Перечисление для месяцев
enum class Month(val russianName: String) {
    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    override fun toString(): String {
        return russianName
    }
}

// Интерфейс для объектов, которые можно взять домой
interface Borrowable {
    fun borrowItem()
}

// Интерфейс для объектов, которые можно использовать в библиотеке
interface ReadableInLibrary {
    fun readInLibrary()
}

// Интерфейс для объектов, которые можно вернуть
// В конкретной задаче не нужен, но полезен для масштабирования
interface Returnable {
    fun returnItem()
}

abstract class LibraryItem(var id: Int, var name: String, var accessible: Boolean) {
    // Метод для получения краткой информации об объекте
    fun getOneLineInfo(): String = "$name | Доступность: ${if (accessible) "да" else "нет"}"

    // Абстрактный метод получения подробной информации
    abstract fun getInfo(): String

    // Метод для изменения доступности с true на false при взятии объекта в зал или домой
    protected fun toggleAccessibility(successMessage: String, failureMessage: String) {
        if (accessible) {
            println(successMessage)
            accessible = false
        } else {
            println(failureMessage)
        }
    }

    // Метод для изменения доступности объекта с false на true при возврате в библиотеку
    protected fun marksAsAccessible(successMessage: String, failureMessage: String) {
        if (!accessible) {
            println(successMessage)
            accessible = true
        } else {
            println(failureMessage)
        }
    }
}

class Book(id: Int, name: String, accessible: Boolean, private var numberOfPages: Int, private var author: String) :
    LibraryItem(id, name, accessible), Borrowable, ReadableInLibrary, Returnable {
    override fun getInfo(): String =
        "Книга: $name ($numberOfPages стр.) автора: $author с id: $id | Доступность: ${if (accessible) "да" else "нет"}"

    override fun borrowItem() {
        toggleAccessibility(
            "Книга $name взята домой",
            "Книга $name уже на руках"
        )
    }

    override fun readInLibrary() {
        toggleAccessibility(
            "Книга $name взята в читальный зал",
            "Книга $name уже на руках"
        )
    }

    override fun returnItem() {
        marksAsAccessible(
            "Книга $name возвращена в библиотеку",
            "Книга $name уже в библиотеке"
        )
    }

}

class Newspaper(id: Int, name: String, accessible: Boolean, private var issueNumber: Int, private var monthOfPublication: Month) :
    LibraryItem(id, name, accessible), ReadableInLibrary, Returnable {
    override fun getInfo(): String =
        "Выпуск: $issueNumber от месяца ${monthOfPublication.russianName} газеты $name с id: $id | Доступность: ${if (accessible) "да" else "нет"}"

    override fun readInLibrary() {
        toggleAccessibility(
            "Газета $name №$issueNumber взята в читальный зал",
            "Газета $name №$issueNumber уже на руках"
        )
    }

    override fun returnItem() {
        marksAsAccessible(
            "Газета $name №$issueNumber возращена в библиотеку",
            "Газета $name №$issueNumber уже в библиотеке"
        )
    }
}

class Disk(id: Int, name: String, accessible: Boolean, private var type: Int) :
    LibraryItem(id, name, accessible), Borrowable, Returnable {
    override fun getInfo(): String =
        "${if (type == 0) "CD" else "DVD"} $name | Доступность: ${if (accessible) "да" else "нет"}"

    override fun borrowItem() {
        toggleAccessibility(
            "${if (type == 0) "CD" else "DVD"} диск $name взят домой",
            "${if (type == 0) "CD" else "DVD"} диск $name уже на руках"
        )
    }

    override fun returnItem() {
        marksAsAccessible(
            "${if (type == 0) "CD" else "DVD"} диск $name возращен в библиотеку",
            "${if (type == 0) "CD" else "DVD"} диск $name уже в библиотеке"
        )
    }
}

class LibraryManager(private val libraryItemList: MutableList<LibraryItem>) {

    // Метод для печати списка объектов по типу
    fun printList(type: Int) {
        val list = when (type) {
            1 -> libraryItemList.filterIsInstance<Book>()
            2 -> libraryItemList.filterIsInstance<Newspaper>()
            3 -> libraryItemList.filterIsInstance<Disk>()
            else -> emptyList()
        }
        list.forEachIndexed { index, item ->
            println("${index + 1}. ${item.getOneLineInfo()}")
        }
    }

    // Метод для получения объекта по типу и индексу в этом типе
    fun getItem(type: Int, index: Int): LibraryItem? {
        val list = when (type) {
            1 -> libraryItemList.filterIsInstance<Book>()
            2 -> libraryItemList.filterIsInstance<Newspaper>()
            3 -> libraryItemList.filterIsInstance<Disk>()
            else -> emptyList()
        }
        // Возвращаем null, если выбор типа или номера в типа неверен
        return if (index in list.indices) list[index] else null
    }
}