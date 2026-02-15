package mk.ukim.finki.wp.june2025g1;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.wp.exam.util.CodeExtractor;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import mk.ukim.finki.wp.exam.util.SubmissionHelper;
import mk.ukim.finki.wp.june2025g1.model.Founder;
import mk.ukim.finki.wp.june2025g1.model.Industry;
import mk.ukim.finki.wp.june2025g1.model.Startup;
import mk.ukim.finki.wp.june2025g1.service.FounderService;
import mk.ukim.finki.wp.june2025g1.service.StartupService;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {


    static {
        SubmissionHelper.exam = "2025-june-g1";
        //TODO: CHANGE THE VALUE OF THE SubmissionHelper.index WITH YOUR INDEX NUMBER!!!
        SubmissionHelper.index = "TODO";
    }

    @Autowired
    StartupService startupService;

    @Autowired
    FounderService founderService;

    @Order(1)
    @Test
    public void test_list_10pt() {
        SubmissionHelper.startTest("test-list-10", 10);
        List<Startup> items = this.startupService.listAll();
        int itemNum = items.size();

        ExamAssert.assertNotEquals("Empty db", 0, itemNum);

        ItemsPage listPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);
        listPage.assertItems(itemNum);

        SubmissionHelper.endTest();
    }

    @Order(2)
    @Test
    public void test_pagination_10pt() {
        SubmissionHelper.startTest("test-pagination-10", 10);
        int pageSize = 5;
        int pageNum = 1;

        ExamAssert.assertNotEquals("Empty db", 0, pageSize);

        ItemsPage listPage = ItemsPage.to(this.driver, pageSize, pageNum);
        AbstractPage.assertRelativeUrl(this.driver, "/?pageSize=" + pageSize + "&pageNum=" + pageNum);
        listPage.assertItems(pageSize);

        SubmissionHelper.endTest();
    }

    @Order(3)
    @Test
    public void test_filter_5pt() {
        SubmissionHelper.startTest("test-filter-5", 5);
        ItemsPage listPage = ItemsPage.to(this.driver);

        listPage.filter("", "", "", "", "");
        listPage.assertItems(10);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 2", "", "", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "1200", "", "", "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "2015", "", "");
        listPage.assertItems(5);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "CYBERSECURITY", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "", "1");
        listPage.assertItems(4);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("1", "500", "", "", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 1", "", "2010", "", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 1", "", "", "BIOTECH", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "1000", "2017", "", "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "600", "", "AI", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "2014", "", "2");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "BIOTECH", "2");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 1", "500", "", "BIOTECH", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 2", "", "2015", "", "2");
        listPage.assertItems(0);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("Startup 1", "600", "2015", "BIOTECH", "");
        listPage.assertItems(0);

        SubmissionHelper.endTest();
    }


    @Order(4)
    @Test
    public void test_filter_service_5pt() {
        SubmissionHelper.startTest("test-filter-service-5", 5);

        ExamAssert.assertEquals("without filter", 10, this.startupService.findPage(null, null, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name only", 1, this.startupService.findPage("Startup 2", null, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by valuation only", 3, this.startupService.findPage(null, 1200.0, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by year founded only", 5, this.startupService.findPage(null, null, 2015, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by industry only", 2, this.startupService.findPage(null, null, null, Industry.CYBERSECURITY, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by founder only", 4, this.startupService.findPage(null, null, null, null, 1L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and valuation", 2, this.startupService.findPage("1", 500.0, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and year founded", 2, this.startupService.findPage("Startup 1", null, 2010, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and industry", 1, this.startupService.findPage("Startup 1", null, null, Industry.BIOTECH, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by valuation and year founded", 3, this.startupService.findPage(null, 1000.0, 2017, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by valuation and industry", 2, this.startupService.findPage(null, 600.0, null, Industry.AI, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by year founded and founder", 2, this.startupService.findPage(null, null, 2014, null, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by industry and founder", 1, this.startupService.findPage(null, null, null, Industry.BIOTECH, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, valuation, and industry", 1, this.startupService.findPage("Startup 1", 500.0, null, Industry.BIOTECH, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, year founded and founder", 0, this.startupService.findPage("Startup 2", null, 2015, null, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, valuation, year founded, industry", 0, this.startupService.findPage("Startup 1", 600.0, 2015, Industry.BIOTECH, null, 0, 20).getNumberOfElements());

        SubmissionHelper.endTest();
    }

    @Order(5)
    @Test
    public void test_create_10pt() {
        SubmissionHelper.startTest("test-create-10", 10);
        List<Founder> founders = this.founderService.listAll();
        List<Startup> startups = this.startupService.listAll();

        int itemNum = startups.size();
        ItemsPage listPage = null;

        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
        }


        listPage = AddOrEditStartup.add(this.driver, ADD_URL, "testName", "250", "2025", Industry.BIOTECH.name(), founders.get(0).getId().toString());
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, LIST_URL);
        listPage.assertNoError();
        listPage.assertItems(itemNum + 1);

        SubmissionHelper.endTest();
    }

    @Order(6)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_create_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-create-mvc-10", 10);
        List<Founder> founders = this.founderService.listAll();
        List<Startup> startups = this.startupService.listAll();

        int itemNum = startups.size();

        MockHttpServletRequestBuilder addRequest = MockMvcRequestBuilders
                .post("/startups")
                .param("name", "testName")
                .param("valuation", "200")
                .param("yearFounded", "2023")
                .param("industry", Industry.BIOTECH.name())
                .param("founderId", founders.get(0).getId().toString());

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        startups = this.startupService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum + 1, startups.size());

        addRequest = MockMvcRequestBuilders
                .get("/startups/add");

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(7)
    @Test
    public void test_edit_10pt() {
        SubmissionHelper.startTest("test-edit-10", 10);
        List<Founder> founders = this.founderService.listAll();
        List<Startup> startups = this.startupService.listAll();

        int itemNum = startups.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        AddOrEditStartup editPage = AddOrEditStartup.getEditPage(this.driver, listPage.getEditButtons().get(itemNum - 1));
        Startup startup = startups.get(itemNum - 1);
        editPage.assertEditFormIsPrefilled(startup.getName(), startup.getValuation().toString(), startup.getYearFounded().toString(), startup.getIndustry().name(), startup.getFounder().getId().toString());

        listPage = AddOrEditStartup.update(this.driver, editPage, "testName", "25", "2024", Industry.CYBERSECURITY.name(), founders.get(0).getId().toString());
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, LIST_URL);
        if (listPage.assertItems(itemNum)) {
            ExamAssert.assertEquals("The updated startup title is not as expected.", "testName", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(0).getText().trim());
        }

        SubmissionHelper.endTest();
    }

    @Order(8)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_edit_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-edit-mvc-10", 10);
        List<Founder> founders = this.founderService.listAll();
        List<Startup> startups = this.startupService.listAll();

        int itemNum = startups.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/startups/" + startups.get(itemNum - 1).getId())
                .param("name", "testName")
                .param("valuation", "200")
                .param("yearFounded", "2023")
                .param("industry", Industry.CYBERSECURITY.name())
                .param("founderId", founders.get(0).getId().toString());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        startups = this.startupService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum, startups.size());
        ExamAssert.assertEquals("The updated startup name is not as expected.", "testName", startups.get(itemNum - 1).getName());
        ExamAssert.assertEquals("The updated valuation is not as expected.", 200.0, startups.get(itemNum - 1).getValuation());
        ExamAssert.assertEquals("The updated yearFounded is not as expected.", 2023, startups.get(itemNum - 1).getYearFounded());
        ExamAssert.assertEquals("The updated industry is not as expected.", Industry.CYBERSECURITY.name(), startups.get(itemNum - 1).getIndustry().name());
        ExamAssert.assertEquals("The updated founder is not as expected.", founders.getFirst().getId().toString(), startups.get(itemNum - 1).getFounder().getId().toString());

        request = MockMvcRequestBuilders
                .get("/startups/edit/" + startups.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(9)
    @Test
    public void test_delete_3pt() throws Exception {
        SubmissionHelper.startTest("test-delete-3", 3);
        List<Startup> items = this.startupService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getDeleteButtons().get(itemNum - 1).click();
        listPage.assertNoError();


        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        listPage = ItemsPage.to(this.driver);
        listPage.assertItems(itemNum - 1);

        SubmissionHelper.endTest();
    }

    @Order(10)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_delete_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-delete-mvc-2", 2);
        List<Startup> items = this.startupService.listAll();
        int itemNum = items.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/startups/delete/" + items.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        items = this.startupService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum - 1, items.size());

        SubmissionHelper.endTest();
    }

    @Order(11)
    @Test
    public void test_security_urls_10pt() {
        SubmissionHelper.startTest("test-security-urls-10", 10);
        List<Startup> items = this.startupService.listAll();
        String editUrl = "/startups/edit/" + items.get(0).getId();

        ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);

        AbstractPage.get(this.driver, DEFAULT_URL);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, "/random");
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        LoginPage.doLogin(this.driver, loginPage, admin, admin);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);

        AbstractPage.get(this.driver, DEFAULT_URL);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);

        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, ADD_URL);

        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, editUrl);

        LoginPage.logout(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        SubmissionHelper.endTest();
    }

    @Order(12)
    @Test
    public void test_security_buttons_10pt() {
        SubmissionHelper.startTest("test-security-buttons-10", 10);
        List<Startup> items = this.startupService.listAll();
        int itemNum = items.size();

        ItemsPage itemsPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);
        itemsPage.assertButtons(0, 0, 0, 0);

        LoginPage loginPage1 = LoginPage.openLogin(this.driver);
        itemsPage = LoginPage.doLogin(this.driver, loginPage1, admin, admin);
        itemsPage.assertButtons(itemNum, itemNum, 1, 10);
        LoginPage.logout(this.driver);

        LoginPage loginPage2 = LoginPage.openLogin(this.driver);
        itemsPage = LoginPage.doLogin(this.driver, loginPage2, user, user);
        itemsPage.assertButtons(0, 0, 0, 0);
        LoginPage.logout(this.driver);
        SubmissionHelper.endTest();
    }

    @Order(13)
    @Test
    public void test_additional_functionality_3pt() throws Exception {
        SubmissionHelper.startTest("test-additional-functionality-3", 3);
        List<Startup> items = this.startupService.listAll();

        int itemNum = items.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getCloseButtons().get(itemNum - 1).click();
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        listPage = ItemsPage.to(this.driver);
        ExamAssert.assertEquals("The deactivate startup text is not as expected.", "DEACTIVATED", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(5).getText().trim());
        ExamAssert.assertEquals("The deactivate startup buttons are not as expected.", false, listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(6).getText().contains("Deactivate"));

        SubmissionHelper.endTest();
    }

    @Order(14)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_additional_functionality_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-additional-functionality-mvc-2", 2);
        List<Startup> items = this.startupService.listAll();

        int itemNum = items.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/startups/deactivate/" + items.get(0).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        items = this.startupService.listAll();
        ExamAssert.assertEquals("First startup is now deactivated", true, !items.get(0).isActive());

        SubmissionHelper.endTest();
    }

    private HtmlUnitDriver driver;
    private MockMvc mockMvc;

    private static String admin = "admin";
    private static String user = "user";

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.driver = new HtmlUnitDriver(true);
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    @AfterAll
    public static void finalizeAndSubmit() throws JsonProcessingException {
        CodeExtractor.submitSourcesAndLogs();
    }

    public static final String LIST_URL = "/startups?pageSize=20&pageNum=1";
    public static final String DEFAULT_URL = "/startups";
    public static final String BASE_DEFAULT_URL = "/?pageSize=20&pageNum=1";
    public static final String ADD_URL = "/startups/add";
    public static final String LOGIN_URL = "/login";

    static class ViewMatcher implements Matcher<String> {

        final String baseName;

        ViewMatcher(String baseName) {
            this.baseName = baseName;
        }

        @Override
        public boolean matches(Object o) {
            if (o instanceof String) {
                String s = (String) o;
                return s.startsWith(baseName);
            }
            return false;
        }

        @Override
        public void describeMismatch(Object o, Description description) {
        }

        @Override
        public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        }

        @Override
        public void describeTo(Description description) {
        }
    }
}
