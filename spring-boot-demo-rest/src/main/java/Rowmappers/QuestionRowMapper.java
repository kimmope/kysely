package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import queta.Question;

public class QuestionRowMapper implements RowMapper<Question>
{
	public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
		Question question = new Question();
		question.setQid(rs.getInt("qid"));
		question.setQuestion(rs.getString("question"));
		return question;
	}
}