package ru.itis.repositories;

import ru.itis.models.Booking;
import java.util.List;

public interface BookingRepository {
    boolean save(Booking booking); // cоздать новую запись о бронировании
    Booking findById(int id); // найти бронирование по id
    List<Booking> findAll(); // получить список всех бронирований
    boolean update(Booking booking); // обновить данные бронирования
    boolean delete(int id); // удалить бронирование по id
    List<Booking> findAllByUserId(int appUserId); // найти все бронирования определенного пользователя
    List<Booking> findAllByPropertyId(int propertyId); // найти все бронирования конкретного объекта (property)
}
