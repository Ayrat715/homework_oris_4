package ru.itis.repositories;

import ru.itis.models.Property;
import java.util.List;

public interface PropertyRepository {
    boolean save(Property property); // создать новое объявление
    Property findById(int id); // найти объявление по id
    List<Property> findAll(); // получить список всех объявлений
    boolean update(Property property); // обновить данные объявления
    boolean delete(int id); // удалить объявление по id
    List<Property> findByOwnerId(int ownerId); // найти все объявления по ownerId
}
