package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import queta.PastQandA;

public class OldQetARowMapper implements RowMapper<PastQandA>  {
	public PastQandA mapRow(ResultSet rs, int row) throws SQLException {
		PastQandA oldQetA = new PastQandA();
		oldQetA.setUid(rs.getInt("uid"));
		oldQetA.setUid(rs.getInt("qid"));
		oldQetA.setQuestion(rs.getString("question"));
		oldQetA.setTime_of_answ(rs.getTimestamp("time_of_answ"));
		oldQetA.setAnswer(rs.getString("answer"));
		return oldQetA;
	}
}