package backend.server.salendar.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.server.salendar.dao.UserDAO;
import backend.server.salendar.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO dao;
	
	@Override
	public int regist(User user) throws SQLException {
		return dao.insert(user);
	}

	@Override
	public int modify(User user) throws SQLException {
		return dao.update(user);
	}

	@Override
	public int remove(String usr_email) throws SQLException {
		return dao.delete(usr_email);
	}

	@Override
	public int login(User user) throws SQLException {
		return dao.login(user);
	}

	@Override
	public User search(String usr_email) throws SQLException {
		return dao.select(usr_email);
	}

	@Override
	public List<User> searchAll() throws SQLException {
		return dao.selectAll();
	}

}
