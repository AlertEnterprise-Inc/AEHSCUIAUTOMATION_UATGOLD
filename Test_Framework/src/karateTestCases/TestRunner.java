package karateTestCases;


import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

//@RunWith(Karate.class) - do not use this tag for parallel execution
public class TestRunner {
    
	
	/*
	 * @BeforeClass public static void beforeClass() throws Exception {
	 */
    
    @Test
    public void testParallel() {
//		String env = System.getProperty("Karate.Env");
//		System.setProperty("karate.env", env);
//		String tags = System.getProperty("tags");
		//System.setProperty("karate.env", env);
        //System.setProperty("karate.env", "qa"); // ensure reset if other tests (e.g. mock) had set env in CI
        //Results results = Runner.path("classpath:features").tags("~@ignore").parallel(5);
        Results results = Runner.path("classpath:karateTestCases/identityManagement.feature").tags("@regression").parallel(5);
        generateReport(results.getReportDir());
        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);        
    }
    public static void generateReport(String karateOutputPath) {        
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] {"json"}, true);
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "features");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();        
    }

}