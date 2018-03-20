package org.openmrs.module.reporting2;

import org.apache.http.HttpResponse;
import org.junit.Test;
import org.openmrs.module.reporting2.model.Result;
import org.openmrs.module.reporting2.utils.HTTP;
import org.openmrs.module.reporting2.utils.Q;
import org.openmrs.module.reporting2.utils.Queries;
import tech.tablesaw.api.Table;
import tech.tablesaw.table.ViewGroup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.openmrs.module.reporting2.utils.Constants.DATA_ELEMENT_PARAMS;
import static org.openmrs.module.reporting2.utils.Constants.PAGING;
import static org.openmrs.module.reporting2.utils.Helper.convert;
import static org.openmrs.module.reporting2.utils.Queries.getSummarizedObs;
import static org.openmrs.module.reporting2.utils.Queries.summarizeObs;

public class UtilsTest {
	
	private String URL = "play.dhis2.org/2.28/api";
	
	@Test
	public void testHttpGet() throws URISyntaxException, IOException {
		URI uri = HTTP.buildURI(URL, "/metadata", Arrays.asList(PAGING, DATA_ELEMENT_PARAMS));
		HttpResponse result = HTTP.get(uri, "admin", "district");
		Path path = Paths.get("./metadata.json");
		HTTP.write(result, path.toFile());
	}
	
	/*@Test
	public void testDownload() throws URISyntaxException, IOException {
	    URI uri = HTTP.buildURI(URL, "/categoryOptions.csv", params);
	    Path path = Paths.get("./categoryOptions.csv");
	    HTTP.download(uri, "admin", "district", path.toFile());
	}

	@Test
	public void testHttpPost() throws URISyntaxException, IOException {
	    String json = "{\n" +
	            "  \"dataSet\": \"pBOMPrpg1QX\",\n" +
	            "  \"completeDate\": \"2014-02-03\",\n" +
	            "  \"period\": \"201401\",\n" +
	            "  \"orgUnit\": \"DiszpKrYNg8\",\n" +
	            "  \"dataValues\": [\n" +
	            "    { \"dataElement\": \"f7n9E0hX8qk\", \"value\": \"1\" },\n" +
	            "    { \"dataElement\": \"Ix2HsbDMLea\", \"value\": \"2\" },\n" +
	            "    { \"dataElement\": \"eY5ehpbEsB7\", \"value\": \"3\" }\n" +
	            "  ]\n" +
	            "}";
	    URI uri = HTTP.buildURI(URL, "/dataValueSets");
	    String result = HTTP.post(uri, "admin", "district", json);
	    HTTP.write("./test.txt", result);

	}
	*/
	@Test
	public void testFileWrite() throws IOException {
		HTTP.write("./test.txt", "test string");
	}
	
	@Test
	public void variousTests() throws Exception {
		String d = "21.3";
		
		boolean correct = d.matches("^\\d+\\.\\d*$");
		assertTrue(correct);
		
	}
	
	@Test
	public void testSummary() throws Exception {
		summarizeObs("1900-01-01");
	}
	
	@Test
	public void testQuery() throws Exception {
		String sql = "SELECT encounter_type, concept, val,  y, m, q, ym, yq, age_gender FROM value_coded LIMIT 10";
		List<Result> data = getSummarizedObs(sql);
		System.out.println(convert(data));
	}
	
	@Test
	public void testQ() throws SQLException, ClassNotFoundException, IOException {
		
		List<String> concepts = Arrays.asList("99037", "99033", "90236", "5090", "99082", "99160", "90217", "99110",
		    "90315", "99072", "99603", "90041", "90012", "90200", "90221", "90216", "99030", "68", "460");
		List<String> concepts2 = Arrays.asList("99604", "99604", "99161", "90299");
		//List<Integer> lastEncounters = Queries.lastEncounters("9", "2017-01-01", "2017-03-31");
		
		Q encounterObs = Queries.query("encounter_type", "9").e("yq", "20171").in("concept", concepts).bracket();
		Q be4QuarterObs = Queries.le("yq", "20171").in("concept", concepts2).bracket();
		Q summaryPage = Queries.n("concept").e("encounter_type", "8").le("yq", "20171").bracket();
		
		Q summaryObs = Queries.in("concept", "99110", "99160").e("encounter_type", "8").e("yq", "20171").bracket();
		Q dead = Queries.query("concept", "0").le("yq", "20171").bracket();
		
		ResultSet r = Queries.join(" OR ", encounterObs, summaryObs).summarized();
		
		Table table = Table.read().db(r, "Carapai");
		
		System.out.println(table.rows().length);
		
		/* // Predicates
		 Predicate<Result> transferIn = Predicates.eq("99110").or(Predicates.eq("99160"));

		 Predicate<Result> startedArtOnOrB4Q = Predicates.eq("99161");
		 Predicate<Result> artRegimen = Predicates.eq("90315");
		 Predicate<Result> pregnantOrLactatingAtArtStart = Predicates.has(Arrays.asList("99072", "99603"));
		 Predicate<Result> tbThisQuarter = Predicates.eq("90216");
		 Predicate<Result> inhThisQ = Predicates.has(Arrays.asList("99604", "99605"));
		 Predicate<Result> inhBe4Q = Predicates.has(Arrays.asList("99604", "99605"));
		 Predicate<Result> pregnantThisQuarter = Predicates.has(Arrays.asList("90041", "90012")).and(Predicates.has("val", Arrays.asList("90003", "1065")));
		 Predicate<Result> entryPoint = Predicates.eq("90200").and(Predicates.eq("val", "90012"));
		 Predicate<Result> predicate = Predicates.eq("encounter_type", "9");
		 Predicate<Result> concept = Predicates.eq("90315");
		 Predicate<Result> cptThisQuarter = Predicates.has(Arrays.asList("99037", "99033"));
		 Predicate<Result> tbDiagnosedThisQuarter = Predicates.eq("val", "90078");
		 Predicate<Result> startedTbThisQuarter = Predicates.eq("90217");
		 Predicate<Result> assessed4MalNumeric = Predicates.has(Arrays.asList("90236", "5090"));
		 Predicate<Result> assessed4MalCoded = Predicates.has(Arrays.asList("99030", "460", "68"));
		 Predicate<Result> malnutrition = Predicates.eq("68").and(Predicates.has("val", Arrays.asList("99271", "99272", "99273")));
		 Predicate<Result> eligibleAndReady = Predicates.eq("90299");
		 Predicate<Result> artBasedOnCD4 = Predicates.eq("99082");

		 List<Result> enrolledBeforeQuarter = summaryObs.filter(transferIn).getFiltered();

		 System.out.println(enrolledBeforeQuarter.size());*/
		
	}
	
	@Test
	public void testGender() throws SQLException, ClassNotFoundException {
		assertNotEquals(Queries.lastEncounters("9", "2017-01-01", "2017-03-31"), 0);
		assertNotEquals(Queries.lastEncounters("9", "2017-03-31"), 0);
	}
	
	@Test
	public void testLastEncounters() throws SQLException, ClassNotFoundException {
		assertNotEquals(Queries.gender("M"), 0);
	}
	
	@Test
	public void testAge() throws SQLException, ClassNotFoundException {
		assertNotEquals(Queries.ageBetween("2012-01-01", "10", "40"), 0);
	}
	
	@Test
	public void testTable() throws IOException, SQLException {
		Table table = Table.read().db(Queries.obs(), "Carapai");
		
		ViewGroup group = new ViewGroup(table, table.column("person_id"));
		
		System.out.println(group.size());
		
	}
}
