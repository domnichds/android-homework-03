package com.example.library

import androidx.recyclerview.widget.DiffUtil

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

    // Метод для получения строки с названием месяца на русском языке
    override fun toString(): String = russianName
}

// Интерфейсы для действий с объектами библиотеки

// Интерфейс для объектов, которые можно взять домой
interface Borrowable {
    // Метод для взятия объекта домой, возвращает обновлённый объект
    fun borrowItem(): LibraryItem
}

// Интерфейс для объектов, которые можно использовать в читальном зале
interface ReadableInLibrary {
    // Метод для взятия объекта в читальный зал, возвращает обновлённый объект
    fun readInLibrary(): LibraryItem
}

// Интерфейс для объектов, которые можно вернуть в библиотеку
interface Returnable {
    // Метод для возврата объекта, возвращает обновлённый объект
    fun returnItem(): LibraryItem
}

// Базовый sealed-класс для объектов библиотеки
sealed class LibraryItem {
    // Уникальный идентификатор объекта
    abstract val id: Int
    // Название объекта
    abstract val name: String
    // Флаг доступности объекта
    abstract val accessible: Boolean

    // Абстрактный метод для копирования с новым статусом
    abstract fun copyWithNewState(newState: Boolean): LibraryItem
}

// Data-класс для книги
data class Book(
    override val id: Int,
    override val name: String,
    override val accessible: Boolean,
    val numberOfPages: Int,
    val author: String
) : LibraryItem(), Borrowable, ReadableInLibrary, Returnable {


    // Метод для взятия книги домой
    override fun borrowItem(): Book =
        if (accessible) {
            println("Книга $name взята домой")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = false)
        } else {
            println("Книга $name уже на руках")
            this
        }

    // Метод для взятия книги в читальный зал
    override fun readInLibrary(): Book =
        if (accessible) {
            println("Книга $name взята в читальный зал")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = false)
        } else {
            println("Книга $name уже на руках")
            this
        }

    // Метод для возврата книги в библиотеку
    override fun returnItem(): Book =
        if (!accessible) {
            println("Книга $name возвращена в библиотеку")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = true)
        } else {
            println("Книга $name уже в библиотеке")
            this
        }

    override fun copyWithNewState(newState: Boolean): Book {
        return copy(accessible = newState)
    }
}

// Data-класс для газеты
data class Newspaper(
    override val id: Int,
    override val name: String,
    override val accessible: Boolean,
    // Номер выпуска газеты
    val issueNumber: Int,
    // Месяц выпуска газеты
    val monthOfPublication: Month
) : LibraryItem(), ReadableInLibrary, Returnable {

    // Метод для взятия газеты в читальный зал
    override fun readInLibrary(): Newspaper =
        if (accessible) {
            println("Газета $name №$issueNumber взята в читальный зал")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = false)
        } else {
            println("Газета $name №$issueNumber уже на руках")
            this
        }

    // Метод для возврата газеты в библиотеку
    override fun returnItem(): Newspaper =
        if (!accessible) {
            println("Газета $name №$issueNumber возвращена в библиотеку")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = true)
        } else {
            println("Газета $name №$issueNumber уже в библиотеке")
            this
        }

    override fun copyWithNewState(newState: Boolean): Newspaper {
        return copy(accessible = newState)
    }

}

// Data-класс для диска
data class Disk(
    override val id: Int,
    override val name: String,
    override val accessible: Boolean,
    // Тип диска: 0 для CD, 1 для DVD
    val type: Int
) : LibraryItem(), Borrowable, Returnable {

    // Метод для взятия диска домой
    override fun borrowItem(): Disk =
        if (accessible) {
            println("${if (type == 0) "CD" else "DVD"} диск $name взят домой")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = false)
        } else {
            println("${if (type == 0) "CD" else "DVD"} диск $name уже на руках")
            this
        }

    // Метод для возврата диска в библиотеку
    override fun returnItem(): Disk =
        if (!accessible) {
            println("${if (type == 0) "CD" else "DVD"} диск $name возвращен в библиотеку")
            // Возвращаем копию с изменённым состоянием доступности
            copy(accessible = true)
        } else {
            println("${if (type == 0) "CD" else "DVD"} диск $name уже в библиотеке")
            this
        }

    override fun copyWithNewState(newState: Boolean): Disk {
        return copy(accessible = newState)
    }
}