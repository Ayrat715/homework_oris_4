package ru.itis.repositories;

import ru.itis.models.AdminLog;
import java.util.List;

public interface AdminLogRepository {
    boolean save(AdminLog adminLog); // сохранить новую запись лога (действия)
    AdminLog findById(int id); // найти запись лога по id
    List<AdminLog> findAll(); // получить список всех записей логов
    boolean update(AdminLog adminLog); // обновить данные лога (напр., изменить текст action)
    boolean delete(int id); // удалить запись лога по id
    List<AdminLog> findAllByAdminId(int adminId); // найти все записи логов конкретного админа
}
