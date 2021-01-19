package backend.server.salendar.service;

import java.sql.SQLException;
import java.util.List;

import backend.server.salendar.dto.User;

public interface UserService {
	public int regist(User member)throws SQLException;
	public int modify(User member)throws SQLException;
	public int remove(String email)throws SQLException;
	public int login(User member)throws SQLException;
	public User search(String email)throws SQLException;
	public List<User> searchAll()throws SQLException;
	
}
