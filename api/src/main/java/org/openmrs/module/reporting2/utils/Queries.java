package org.openmrs.module.reporting2.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.reporting2.model.Result;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Queries {
	
	private static Connection connection;
	
	static {
		try {
			connection = testSqlConnection();
		}
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer executeQuery(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		return stmt.executeUpdate();
	}
	
	public static List<Integer> gender(String type) throws SQLException {
        String query = String.format("select person_id from person where gender = '%s' and voided = 0", type);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }
        return data;
    }
	
	public static List<Integer> ageBetween(String endDate, String from, String to) throws SQLException {
        String query = "SELECT  pp.person_id,\n" +
                "  YEAR('2017-03-31') - YEAR(birthdate) - (RIGHT('2017-03-31', 5) < RIGHT(birthdate, 5)) AS age\n" +
                "FROM person pp INNER JOIN patient p ON pp.person_id = p.patient_id\n" +
                "HAVING age BETWEEN start AND end"
                        .replaceAll("2017-03-31", endDate)
                        .replaceAll("start", from).replaceAll("end", to);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }
        return data;
    }
	
	public static List<Integer> ageBefore(String endDate, String to) throws SQLException {
        String query = "SELECT  pp.person_id,\n" +
                "  YEAR('2017-03-31') - YEAR(birthdate) - (RIGHT('2017-03-31', 5) < RIGHT(birthdate, 5)) AS age\n" +
                "FROM person pp INNER JOIN patient p ON pp.person_id = p.patient_id\n" +
                "HAVING age < end"
                        .replaceAll("2017-03-31", endDate).replaceAll("end", to);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }

        return data;
    }
	
	public static List<Integer> lastEncounters(String encounter_type, String startDate, String endDate) throws SQLException {
        String query = String.format("SELECT t1.encounter_id\n" +
                "FROM encounter t1\n" +
                "WHERE t1.encounter_id = (SELECT t2.encounter_id\n" +
                "                         FROM encounter t2\n" +
                "                         WHERE t2.patient_id = t1.patient_id AND t2.encounter_type = t1.encounter_type AND\n" +
                "                               t2.encounter_type = '%s' AND t2.encounter_datetime BETWEEN '%s' AND '%s'\n" +
                "                         ORDER BY t2.encounter_datetime DESC\n" +
                "                         LIMIT 1);", encounter_type, startDate, endDate);
        System.out.println(query);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }

        return data;
    }
	
	public static ResultSet obs() throws SQLException {
		String query = "SELECT * from summary";
		Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
		    java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		return stmt.executeQuery(query);
	}
	
	public static List<Integer> lastEncounters(String encounter_type, String endDate) throws SQLException {
        String query = "SELECT t1.encounter_id\n" +
                "FROM encounter t1\n" +
                "WHERE t1.encounter_id = (SELECT t2.encounter_id\n" +
                "                         FROM encounter t2\n" +
                "                         WHERE t2.patient_id = t1.patient_id AND t2.encounter_type = t1.encounter_type AND\n" +
                "                               t2.encounter_type = 'ec_type' AND t2.encounter_datetime <= '1900-03-31'\n" +
                "                         ORDER BY t2.encounter_datetime DESC\n" +
                "                         LIMIT 1);".replaceAll("ec_type", encounter_type)
                        .replaceAll("1900-03-31", endDate);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }

        return data;
    }
	
	public static List<Integer> ageAfter(String endDate, String from) throws SQLException {
        String query = "SELECT  pp.person_id,\n" +
                "  YEAR('2017-03-31') - YEAR(birthdate) - (RIGHT('2017-03-31', 5) < RIGHT(birthdate, 5)) AS age\n" +
                "FROM person pp INNER JOIN patient p ON pp.person_id = p.patient_id\n" +
                "HAVING age > start"
                        .replaceAll("2017-03-31", endDate).replaceAll("start", from);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }
        return data;
    }
	
	public static List<Integer> ageEqual(String endDate, String equal) throws SQLException {
        String query = "SELECT  pp.person_id,\n" +
                "  YEAR('2017-03-31') - YEAR(birthdate) - (RIGHT('2017-03-31', 5) < RIGHT(birthdate, 5)) AS age\n" +
                "FROM person pp INNER JOIN patient p ON pp.person_id = p.patient_id\n" +
                "HAVING age = equal"
                        .replaceAll("2017-03-31", endDate).replaceAll("equal", equal);
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Integer> data = new ArrayList<>();
        while (rs.next()) {
            data.add(rs.getInt(1));
        }
        return data;
    }
	
	public static int createValueCodedTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS value_coded (\n" + "  encounter_type INT(3) NULL,\n"
		        + "  concept        INT(10) NULL,\n" + "  val            TEXT,\n" + "  y              INT(4)   NULL,\n"
		        + "  m              INT(1)   NULL,\n" + "  q              INT(1)   NULL,\n"
		        + "  ym             INT(6)   NULL,\n" + "  yq             INT(6)   NULL,\n" + "  age_gender     LONGTEXT\n"
		        + ");";
		return executeQuery(sql);
	}
	
	public static int createValueEncounterTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS value_encounter (\n" + "  encounter_type INT(3) NULL,\n"
		        + "  y              INT(4)   NULL,\n" + "  m              INT(1)   NULL,\n"
		        + "  q              INT(1)   NULL,\n" + "  ym             INT(6)   NULL,\n"
		        + "  yq             INT(6)   NULL,\n" + "  patients     LONGTEXT,\n" + "  obs            LONGTEXT,\n"
		        + "  total          INT(5)\n" + ");";
		return executeQuery(sql);
	}
	
	public static void summarizeObs(String date) throws SQLException {
		createValueCodedTable();
		executeQuery("SET @@group_concat_max_len = 10000000;");
		executeQuery(valueCodedQuery(date));
		executeQuery(encounterQuery(date));
		executeQuery(deathQuery(date));
	}
	
	public static String encounterQuery(String date) {
		String sql = "INSERT INTO value_coded (encounter_type, y, m, q, ym, yq, age_gender)\n"
		        + "  SELECT\n"
		        + "    IFNULL((SELECT et.encounter_type_id\n"
		        + "            FROM encounter_type AS et\n"
		        + "            WHERE et.encounter_type_id = e.encounter_type), NULL) AS encounter_type,\n"
		        + "    YEAR(e.encounter_datetime)                                    AS y,\n"
		        + "    MONTH(e.encounter_datetime)                                   AS m,\n"
		        + "    QUARTER(e.encounter_datetime)                                 AS q,\n"
		        + "    concat(YEAR(encounter_datetime), MONTH(encounter_datetime))   AS ym,\n"
		        + "    concat(YEAR(encounter_datetime), QUARTER(encounter_datetime)) AS yq,\n"
		        + "    group_concat(concat_ws(':', patient_id, 0, encounter_id, IF(encounter_id IN\n"
		        + "                                                                (SELECT t1.encounter_id\n"
		        + "                                                                 FROM encounter t1\n"
		        + "                                                                 WHERE t1.encounter_id =\n"
		        + "                                                                       (SELECT t2.encounter_id\n"
		        + "                                                                        FROM encounter t2\n"
		        + "                                                                        WHERE t2.patient_id = t1.patient_id AND\n"
		        + "                                                                              t2.encounter_type = t1.encounter_type\n"
		        + "                                                                        ORDER BY t2.encounter_datetime ASC\n"
		        + "                                                                        LIMIT 1)), 1, 0), 0,\n"
		        + "                           (SELECT concat_ws(':', p.gender, p.birthdate, YEAR(e.encounter_datetime) - YEAR(birthdate) -\n"
		        + "                                                                         (RIGHT(e.encounter_datetime, 5) <\n"
		        + "                                                                          RIGHT(birthdate, 5)))\n"
		        + "                            FROM person p\n"
		        + "                            WHERE p.person_id = e.patient_id),\n"
		        + "                           DATE(e.encounter_datetime)))           AS age_gender\n"
		        + "  FROM encounter e\n" + "  WHERE e.date_created > '1900-01-01' AND voided = 0\n"
		        + "  GROUP BY encounter_type, y, q, m;";
		return sql.replace("1900-01-01", date);
	}
	
	public static String valueCodedQuery(String date) {
		String sql = "INSERT INTO value_coded (encounter_type, concept, val, y, m, q, ym, yq, age_gender)\n"
		        + "  SELECT\n"
		        + "    IFNULL((SELECT et.encounter_type_id\n"
		        + "            FROM encounter_type AS et\n"
		        + "            WHERE et.encounter_type_id =\n"
		        + "                  (SELECT e.encounter_type\n"
		        + "                   FROM encounter AS e\n"
		        + "                   WHERE e.encounter_id = o.encounter_id)), NULL)                 AS encounter_type,\n"
		        + "    concept_id                                                                    AS concept,\n"
		        + "    concat_ws(':', value_coded, value_numeric, value_text, DATE_FORMAT(value_datetime, '%Y%m'), value_coded_name_id,\n"
		        + "              value_complex, value_drug, value_modifier, value_group_id)          AS val,\n"
		        + "    YEAR(if(value_datetime IS NOT NULL, value_datetime, obs_datetime))            AS y,\n"
		        + "    MONTH(if(value_datetime IS NOT NULL, value_datetime, obs_datetime))           AS m,\n"
		        + "    QUARTER(if(value_datetime IS NOT NULL, value_datetime, obs_datetime))         AS q,\n"
		        + "    concat(YEAR(if(value_datetime IS NOT NULL, value_datetime, obs_datetime)),\n"
		        + "           MONTH(if(value_datetime IS NOT NULL, value_datetime, obs_datetime)))   AS ym,\n"
		        + "    concat(YEAR(if(value_datetime IS NOT NULL, value_datetime, obs_datetime)),\n"
		        + "           QUARTER(if(value_datetime IS NOT NULL, value_datetime, obs_datetime))) AS yq,\n"
		        + "    group_concat(concat_ws(':', o.person_id, o.obs_id, coalesce(o.encounter_id, 0),\n"
		        + "                           IF(o.encounter_id IN\n"
		        + "                              (SELECT t1.encounter_id\n"
		        + "                               FROM encounter t1\n"
		        + "                               WHERE t1.encounter_id =\n"
		        + "                                     (SELECT t2.encounter_id\n"
		        + "                                      FROM encounter t2\n"
		        + "                                      WHERE t2.patient_id = t1.patient_id AND t2.encounter_type = t1.encounter_type\n"
		        + "                                      ORDER BY t2.encounter_datetime ASC\n"
		        + "                                      LIMIT 1)), 1, 0),\n"
		        + "                           coalesce(o.obs_group_id, 0),\n"
		        + "                           (SELECT concat_ws(':', p.gender, p.birthdate,\n"
		        + "                                             YEAR(if(o.value_datetime IS NOT NULL, o.value_datetime, o.obs_datetime)) -\n"
		        + "                                             YEAR(p.birthdate) -\n"
		        + "                                             (RIGHT(if(o.value_datetime IS NOT NULL, o.value_datetime, o.obs_datetime),\n"
		        + "                                                    5) <\n"
		        + "                                              RIGHT(p.birthdate, 5)))\n"
		        + "                            FROM person p\n"
		        + "                            WHERE p.person_id = o.person_id)))                    AS age_gender\n"
		        + "  FROM obs o\n" + "  WHERE o.date_created > '1900-10-01' AND voided = 0\n"
		        + "  GROUP BY encounter_type, concept, val, y, q, m;";
		return sql.replace("1900-01-01", date);
	}
	
	public static String deathQuery(String date) {
		String sql = "INSERT INTO value_coded (concept, y, m, q, ym, yq, age_gender)\n"
		        + "  SELECT\n"
		        + "    0                                                                                                   AS concept,\n"
		        + "    YEAR(p.death_date)                                                                                  AS y,\n"
		        + "    MONTH(p.death_date)                                                                                 AS m,\n"
		        + "    QUARTER(p.death_date)                                                                               AS q,\n"
		        + "    concat(YEAR(p.death_date), MONTH(\n"
		        + "        p.death_date))                                                                                  AS ym,\n"
		        + "    concat(YEAR(p.death_date), QUARTER(\n"
		        + "        p.death_date))                                                                                  AS yq,\n"
		        + "    group_concat(concat_ws(':', person_id, 0, 0, 0, 0, p.gender, p.birthdate,\n"
		        + "                           YEAR(p.death_date) - YEAR(birthdate) - (RIGHT(p.death_date, 5) < RIGHT(birthdate,\n"
		        + "                                                                                                  5)))) AS age_gender\n"
		        + "  FROM person p\n" + "  WHERE p.death_date IS NOT NULL AND p.date_created > '1900-01-01'\n"
		        + "  GROUP BY y, q, m;";
		return sql.replace("1900-01-01", date);
	}
	
	public static ResultSet summary(String where) throws SQLException {
		String query = "SELECT * FROM summary";
		if (StringUtils.isNotBlank(where)) {
			query = String.format(query + " WHERE %s", where);
		}
		Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
		    java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		return stmt.executeQuery(query);
	}
	
	public static List<Result> getSummarizedObs(String conditions) throws SQLException {

        String query = "SELECT encounter_type, concept, val, y, m, q, ym, yq, age_gender FROM value_coded";

        if (StringUtils.isNotBlank(conditions)) {
            query = String.format(query + " WHERE %s", conditions);
        }
        Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        stmt.setFetchSize(Integer.MIN_VALUE);
        ResultSet rs = stmt.executeQuery(query);
        List<Result> summarizedObs = new ArrayList<>();
        while (rs.next()) {
            String encounterType = rs.getString(1);
            String concept = rs.getString(2);
            String value = rs.getString(3);
            String y = rs.getString(4);
            String m = rs.getString(5);
            String q = rs.getString(6);
            String ym = rs.getString(7);
            String yq = rs.getString(8);

            for (String ageGender : Splitter.on(",").splitToList(rs.getString(9))) {
                List<String> splitter = Splitter.on(":").splitToList(ageGender);
                Result obs = new Result();
                obs.set("encounter_type", encounterType == null ? "0" : encounterType);
                obs.set("concept", concept);
                obs.set("value", value);
                obs.set("y", y);
                obs.set("m", m);
                obs.set("q", q);
                obs.set("ym", ym);
                obs.set("yq", yq);
                obs.set("patient", splitter.get(0));
                obs.set("obs", splitter.get(1));
                obs.set("encounter", splitter.get(2));
                obs.set("first_encounter", splitter.get(3));
                obs.set("obs_group", splitter.get(4));
                obs.set("gender", splitter.get(5));
                obs.set("dob", splitter.get(6));
                obs.set("age", splitter.get(7));
                //obs.set("voided", splitter.get(8));
                summarizedObs.add(obs);
            }
        }
        rs.close();
        stmt.close();
        return summarizedObs;
    }
	
	public static ResultSet summarized(String conditions) throws SQLException {
		
		String query = "SELECT * FROM value_coded";
		
		if (StringUtils.isNotBlank(conditions)) {
			query = String.format(query + " WHERE %s", conditions);
		}
		Statement stmt = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
		    java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		return stmt.executeQuery(query);
		
	}
	
	public static Q query(String column, String value) {
		return new Q(column + " = '" + value + "'");
	}
	
	public static Q l(String column, String value) {
		return new Q(column + " < '" + value + "'");
	}
	
	public static Q le(String column, String value) {
		return new Q(column + " <= '" + value + "'");
	}
	
	public static Q q(String column, String value) {
		return new Q(column + " > '" + value + "'");
	}
	
	public static Q qe(String column, String value) {
		return new Q(column + " >= '" + value + "'");
	}
	
	public static Q n(String column) {
		return new Q(column + " IS NULL");
	}
	
	public static Q b(String column, String val1, String val2) {
		return new Q(column + " BETWEEN '" + val1 + "' AND '" + val2 + "'");
	}
	
	public static Q in(String column, Collection<String> data) {
        String s = Joiner.on(",").join(data.stream().map(e -> "'" + e + "'").collect(Collectors.toList()));
        return new Q(column + " IN (" + s + ")");
    }
	
	public static Q in(String column, String... data) {
        String s = Joiner.on(",").join(Arrays.stream(data).map(e -> "'" + e + "'").collect(Collectors.toList()));
        return new Q(column + " IN (" + s + ")");
    }
	
	public static Q join(String joiner, Q... q) {
		return new Q(Joiner.on(joiner).join(q));
	}
	
	public static Q join(String joiner, Collection<Q> q) {
		return new Q(Joiner.on(joiner).join(q));
	}
	
	public static java.sql.Connection getDatabaseConnection(Properties props) throws ClassNotFoundException, SQLException {
		
		String driverClassName = props.getProperty("driver.class");
		String driverURL = props.getProperty("driver.url");
		String username = props.getProperty("user");
		String password = props.getProperty("password");
		Class.forName(driverClassName);
		return DriverManager.getConnection(driverURL, username, password);
	}
	
	public static java.sql.Connection testSqlConnection() throws SQLException, ClassNotFoundException {
		Properties props = new Properties();
		props.setProperty("driver.class", "com.mysql.jdbc.Driver");
		props.setProperty("driver.url", "jdbc:mysql://localhost:3306/ugandaemr");
		props.setProperty("user", "openmrs");
		props.setProperty("password", "openmrs");
		return getDatabaseConnection(props);
	}
}
