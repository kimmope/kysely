package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import queta.Question2;

public class QuestionRowMapper{
	public static RowMapper<Question2> questionRowMapper = new RowMapper<Question2>() {	
		public Question2 mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Question2(
				rs.getInt("uid"),
				rs.getInt("qid"),
				rs.getString("question"),
				rs.getInt("amount_answs"),
				rs.getInt("sum_answers"),
				rs.getDouble("average"),
				rs.getInt("true_answer"),
				rs.getInt("type"),
				rs.getInt("amount"),
				rs.getInt("maxlength"),
				rs.getInt("min"),
				rs.getInt("max"),
				rs.getInt("step"),
				rs.getString("value"),
				rs.getString("value_1"),
				rs.getString("value_2"),
				rs.getString("value_3"),
				rs.getString("value_4"),
				rs.getString("value_5")
			);
		}
	};
}