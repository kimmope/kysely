package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import queta.YearlyStatus;

public class YearlyStatusRowMapper {
	public static RowMapper<YearlyStatus> yearlyStatusRowMapper = new RowMapper<YearlyStatus>() {
		public YearlyStatus mapRow(ResultSet rs, int row) throws SQLException {
			return new YearlyStatus(
				rs.getInt("qid"),
				rs.getInt("year"),
				rs.getDouble("mean"),
				rs.getDouble("medi"),
				rs.getDouble("mode"),
				rs.getString("classMode"),
				rs.getInt("amntAnswVal1"),
				rs.getInt("amntAnswVal2"),
				rs.getInt("amntAnswVal3"),
				rs.getInt("amntAnswVal4"),
				rs.getInt("amntAnswVal5")
			);
		}
	};
}