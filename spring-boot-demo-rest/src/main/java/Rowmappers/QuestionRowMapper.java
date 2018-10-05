package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import queta.Question;

public class QuestionRowMapper{
	public static RowMapper<Question> questionRowMapper = new RowMapper<Question>() {	
		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Question(
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
				rs.getString("def_val"),
				rs.getString("value_1"),
				rs.getString("value_2"),
				rs.getString("value_3"),
				rs.getString("value_4"),
				rs.getString("value_5")
			);
		}
	};
}