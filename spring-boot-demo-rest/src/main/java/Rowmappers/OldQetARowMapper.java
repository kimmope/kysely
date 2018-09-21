package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import queta.OldQetA;

public class OldQetARowMapper implements RowMapper<OldQetA>  {
	public OldQetA mapRow(ResultSet rs, int row) throws SQLException {
		OldQetA oldQetA = new OldQetA();
		oldQetA.setUid(rs.getInt("uid"));
		oldQetA.setUid(rs.getInt("qid"));
		oldQetA.setQuestion(rs.getString("question"));
		oldQetA.setTime_of_answ(rs.getTimestamp("time_of_answ"));
		oldQetA.setAnswer(rs.getString("answer"));
		return oldQetA;
	}
}