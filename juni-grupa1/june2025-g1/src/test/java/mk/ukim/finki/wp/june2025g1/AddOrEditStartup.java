package mk.ukim.finki.wp.june2025g1;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditStartup extends AbstractPage {
    private WebElement name;
    private WebElement valuation;
    private WebElement yearFounded;
    private WebElement industry;
    private WebElement founderId;
    private WebElement submit;

    public AddOrEditStartup(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String valuation, String yearFounded, String industry, String founderId) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditStartup addOrEditStartup = PageFactory.initElements(driver, AddOrEditStartup.class);
        addOrEditStartup.assertNoError();
        addOrEditStartup.name.sendKeys(name);
        addOrEditStartup.valuation.sendKeys(valuation);
        addOrEditStartup.yearFounded.sendKeys(yearFounded);

        Select selectIndustry = new Select(addOrEditStartup.industry);
        selectIndustry.selectByValue(industry);

        Select selectFounder = new Select(addOrEditStartup.founderId);
        selectFounder.selectByValue(founderId);

        addOrEditStartup.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static AddOrEditStartup getEditPage(WebDriver driver, WebElement editButton) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        return PageFactory.initElements(driver, AddOrEditStartup.class);
    }

    public static ItemsPage update(WebDriver driver, AddOrEditStartup addOrEditStartup, String name, String valuation, String yearFounded, String industry, String founderId) {
        addOrEditStartup.name.clear();
        addOrEditStartup.name.sendKeys(name);
        addOrEditStartup.valuation.clear();
        addOrEditStartup.valuation.sendKeys(valuation);
        addOrEditStartup.yearFounded.clear();
        addOrEditStartup.yearFounded.sendKeys(yearFounded);

        Select selectIndustry = new Select(addOrEditStartup.industry);
        selectIndustry.selectByValue(industry);

        Select selectFounder = new Select(addOrEditStartup.founderId);
        selectFounder.selectByValue(founderId);

        addOrEditStartup.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertEditFormIsPrefilled(String name, String valuation, String yearFounded, String industry, String founderId) {
        ExamAssert.assertEquals("Name is not prefilled", name, this.name.getAttribute("value"));
        ExamAssert.assertEquals("Valuation is not prefilled", valuation, this.valuation.getAttribute("value"));
        ExamAssert.assertEquals("Year Founded is not prefilled", yearFounded, this.yearFounded.getAttribute("value"));

        boolean checkIndustry = ExamAssert.assertNotEquals("Industry is not preselected", 0, new Select(this.industry).getAllSelectedOptions().size());
        if (checkIndustry) {
            ExamAssert.assertEquals("Industry is not preselected", industry, new Select(this.industry).getFirstSelectedOption().getAttribute("value"));
        }

        boolean checkFounder = ExamAssert.assertNotEquals("Founder is not preselected", 0, new Select(this.founderId).getAllSelectedOptions().size());
        if (checkFounder) {
            ExamAssert.assertEquals("Founder is not preselected", founderId, new Select(this.founderId).getFirstSelectedOption().getAttribute("value"));
        }
    }
}
