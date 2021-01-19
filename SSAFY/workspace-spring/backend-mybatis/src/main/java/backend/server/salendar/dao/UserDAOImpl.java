package backend.server.salendar.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import backend.server.salendar.dto.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int insert(User user) throws SQLException {
		
		return sqlSession.insert("user.insert", user);
	}

	@Override
	public int update(User user) throws SQLException {
		return sqlSession.update("user.update", user);
	}

	@Override
	public int delete(String usr_email) throws SQLException {
		return sqlSession.delete("user.delete", usr_email);
	}

	@Override
	public int login(User user) throws SQLException {
		return sqlSession.selectOne("user.login", user);
	}

	@Override
	public User select(String usr_email) throws SQLException {
		return sqlSession.selectOne("user.select", usr_email);
	}

	@Override
	public List<User> selectAll() throws SQLException {
		return sqlSession.selectList("user.selectAll");
	}

}
