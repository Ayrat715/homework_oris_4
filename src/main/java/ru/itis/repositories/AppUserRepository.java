package ru.itis.repositories;

import ru.itis.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    boolean save(AppUser user); // сохранить нового пользователя в БД
    AppUser findById(int id); // найти пользователя по id
    AppUser findByEmail(String email); // найти пользователя по email
    List<AppUser> findAll(); // получить список всех пользователей
    boolean update(AppUser user); // обновить данные пользователя
    boolean delete(int id); // удалить пользователя по id
}
