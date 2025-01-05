package ru.itis.repositories;

import ru.itis.models.Review;
import java.util.List;

public interface ReviewRepository {
    boolean save(Review review); // создать новый отзыв
    Review findById(int id); // найти отзыв по id
    List<Review> findAll(); // получить список всех отзывов
    boolean update(Review review); // обновить данные отзыва (оценку, текст и тд)
    boolean delete(int id); // удалить отзыв по id
    List<Review> findAllByUserId(int appUserId); // найти все отзывы конкретного пользователя
    List<Review> findAllByPropertyId(int propertyId); // найти все отзывы конкретного объекта (property)
}