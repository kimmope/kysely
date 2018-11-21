package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import queta.PastQandA;

public class PastQandARowMapper{
	public static RowMapper<PastQandA> pastQandARowMapper = new RowMapper<PastQandA>() {
		public PastQandA mapRow(ResultSet rs, int row) throws SQLException {
			return new PastQandA(
				rs.getInt("uid"),
				rs.getInt("qid"),
				rs.getString("question"),
				rs.getTimestamp("timeOfAnswer"),
				rs.getString("answer")
			);
		}
	};
}