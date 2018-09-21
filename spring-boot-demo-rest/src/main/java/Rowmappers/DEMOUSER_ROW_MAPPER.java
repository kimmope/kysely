package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.eduix.spring.demo.domain.DemoUser;

public class DEMOUSER_ROW_MAPPER implements RowMapper<DemoUser>{
	
	
	
	public DemoUser mapRow(ResultSet resultSet, int row) throws SQLException {
		return new DemoUser(
			resultSet.getString("username"),
			resultSet.getString("lastname"),
			resultSet.getString("firstname"));
	}
};