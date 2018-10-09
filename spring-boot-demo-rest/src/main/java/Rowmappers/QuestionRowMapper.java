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
				rs.getInt("amountAnswrs"),
				rs.getInt("sumAnswers"),
				rs.getDouble("average"),
				rs.getInt("trueAnswer"),
				rs.getInt("type"),
				rs.getInt("amount"),
				rs.getInt("maxLength"),
				rs.getInt("min"),
				rs.getString("minTitle"),
				rs.getInt("max"),
				rs.getString("maxTitle"),
				rs.getInt("step"),
				rs.getString("defval1"),
				rs.getString("defval2"),
				rs.getString("defval3"),
				rs.getString("defval4"),
				rs.getString("defval5"),
				rs.getString("colHead1"),
				rs.getString("colHead2"),
				rs.getString("colHead3"),
				rs.getString("colHead4"),
				rs.getString("colHead5"),
				rs.getString("rowHead1"),
				rs.getString("rowHead2"),
				rs.getString("rowHead3"),
				rs.getString("rowHead4"),
				rs.getString("rowHead5"),
				rs.getString("message"),
				rs.getString("info")
			);
		}
	};
}