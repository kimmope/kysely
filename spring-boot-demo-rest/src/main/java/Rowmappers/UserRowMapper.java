package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import queta.User;

public class UserRowMapper implements RowMapper<User>  {
	public User mapRow(ResultSet rs, int row) throws SQLException {
		User user = new User();
		user.setUid(rs.getInt("uid"));
		user.setUsername(rs.getString("username"));
		user.setAmntUserAnsw(rs.getInt("amntUserAnsw"));
		user.setScore(rs.getInt("score"));
		return user;
	}
}