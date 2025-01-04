package ru.itis.repositories;

import ru.itis.models.AppUserRole;

import java.util.List;

public interface AppUserRoleRepository {
    boolean save(AppUserRole userRole); // добавить связь (пользователь -> роль)
    boolean delete(int appUserId, int roleId); // удалить связь (пользователь -> роль) по ключам
    List<AppUserRole> findAllByUserId(int appUserId); // найти все связи пользователя (все роли, которые у него есть)
    List<AppUserRole> findAllByRoleId(int roleId); // найти все связи конкретной роли (все пользователи, у кого эта роль)
    List<AppUserRole> findAll(); // получить все записи из таблицы app_user_role
}
