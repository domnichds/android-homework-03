package com.example.library.repository

import com.example.library.LibraryItem
import com.example.library.Book
import com.example.library.Newspaper
import com.example.library.Disk
import com.example.library.Month

class ItemRepository {

    // Список элементов библиотеки (сгенерировано нееросетью)
    private val items = mutableListOf<LibraryItem>(
        Book(1, "Преступление и наказание", true, 300, "Фёдор Достоевский"),
        Book(2, "Война и мир", true, 1200, "Лев Толстой"),
        Book(3, "1984", true, 400, "Джордж Оруэлл"),
        Book(4, "Мастер и Маргарита", true, 500, "Михаил Булгаков"),
        Book(5, "Гарри Поттер и философский камень", true, 350, "Джоан Роулинг"),
        Book(6, "Алиса в стране чудес", true, 250, "Льюис Кэрролл"),
        Book(7, "Идиот", true, 600, "Фёдор Достоевский"),
        Book(8, "Отцы и дети", true, 320, "Иван Тургенев"),
        Book(9, "Ромео и Джульетта", true, 250, "Уильям Шекспир"),
        Book(10, "Анна Каренина", true, 864, "Лев Толстой"),

        Newspaper(11, "Комсомольская правда", true, 2345, Month.JANUARY),
        Newspaper(12, "Московский комсомолец", true, 1204, Month.FEBRUARY),
        Newspaper(13, "Известия", true, 5678, Month.MARCH),
        Newspaper(14, "Аргументы и факты", true, 9821, Month.APRIL),
        Newspaper(15, "Российская газета", true, 3050, Month.MAY),
        Newspaper(16, "Ведомости", true, 1678, Month.JUNE),
        Newspaper(17, "The New York Times", true, 6789, Month.JULY),
        Newspaper(18, "The Guardian", true, 2345, Month.AUGUST),
        Newspaper(19, "Frankfurter Allgemeine", true, 4567, Month.SEPTEMBER),
        Newspaper(20, "Le Monde", true, 7890, Month.OCTOBER),

        Disk(21, "Музыка Бетховена", true, 0),
        Disk(22, "Лунная соната", true, 0),
        Disk(23, "Рок-хиты 80-х", true, 0),
        Disk(24, "Лучшие треки 2000-х", true, 0),
        Disk(25, "Интерстеллар", true, 1),
        Disk(26, "Матрица", true, 1),
        Disk(27, "Начало", true, 1),
        Disk(28, "Властелин колец", true, 1),
        Disk(29, "Гарри Поттер: философский камень", true, 1),
        Disk(30, "Звёздные войны: Новая надежда", true, 1)
    )

    // Метод, возвращающий копию списка
    fun getItems(): List<LibraryItem> = items.toList()

    // Обновление элемента списка (изменение доступности)
    fun updateItemState(itemId: Int, newAccessible: Boolean) {
        val index = items.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val current = items[index]
            items[index] = current.copyWithNewState(newAccessible)
        }
    }

    // Метод для удаление элемента по id
    fun removeItem(itemId: Int) {
        items.removeAll { it.id == itemId }
    }
}
