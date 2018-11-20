package Rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import queta.Statistics;

public class StatisticsRowMapper {
	public static RowMapper<Statistics> STATISTICS_ROW_MAPPER = new RowMapper<Statistics>() {
		public Statistics mapRow(ResultSet rs, int row) throws SQLException {
			return new Statistics(
				rs.getInt("amount"),
				rs.getDouble("mean"),
				rs.getDouble("medi"),
				rs.getDouble("mode"),
				rs.getString("classMode"),
				rs.getInt("amntAnswVal1"),
				rs.getInt("amntAnswVal2"),
				rs.getInt("amntAnswVal3"),
				rs.getInt("amntAnswVal4"),
				rs.getInt("amntAnswVal5"),
				rs.getString("id")
			);
		}
	};
}