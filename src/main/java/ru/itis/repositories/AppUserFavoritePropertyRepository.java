package ru.itis.repositories;

import ru.itis.models.AppUserFavoriteProperty;
import java.util.List;

public interface AppUserFavoritePropertyRepository {
    boolean save(AppUserFavoriteProperty favorite); // добавить объект (property) в избранное конкретного пользователя
    boolean delete(int appUserId, int propertyId); // удалить объект (property) из избранного конкретного пользователя
    List<AppUserFavoriteProperty> findAllByUserId(int appUserId); // найти все избранные объявления конкретного пользователя
    List<AppUserFavoriteProperty> findAllByPropertyId(int propertyId); // найти всех пользователей, которые добавили конкретный объект property в избранное
    List<AppUserFavoriteProperty> findAll(); // получить список всех избранных объектов теми или иными пользователями
}
