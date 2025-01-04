package ru.itis.repositories;

import ru.itis.models.Role;
import java.util.List;

public interface RoleRepository {
    boolean save(Role role); // создать новую роль
    Role findById(int id); // найти роль по id
    Role findByName(String name); // найти роль по имени (напр. ADMIN)
    List<Role> findAll(); // получить список всех ролей
    boolean update(Role role); // обновить данные существующей роли
    boolean delete(int id); // удалить роль по id
}
