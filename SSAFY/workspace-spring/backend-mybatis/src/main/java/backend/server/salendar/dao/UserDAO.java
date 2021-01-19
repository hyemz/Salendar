package backend.server.salendar.dao;

import java.sql.SQLException;
import java.util.List;

import backend.server.salendar.dto.User;

public interface UserDAO {
	public int insert(User member)throws SQLException;
	public int update(User member)throws SQLException;
	public int delete(String email)throws SQLException;
	public int login(User member)throws SQLException;
	public User select(String email)throws SQLException;
	public List<User> selectAll() throws SQLException;
}
