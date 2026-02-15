package mk.ukim.finki.wp.june2025g1;

import lombok.Getter;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@Getter
public class ItemsPage extends AbstractPage {

    private WebElement name;
    private WebElement valuation;
    private WebElement yearFounded;
    private WebElement industry;
    private WebElement founderId;
    private WebElement filter;

    @FindBy(css = "tr[class=item]")
    private List<WebElement> rows;

    @FindBy(css = ".delete-item")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-item")
    private List<WebElement> editButtons;

    @FindBy(css = ".close-item")
    private List<WebElement> closeButtons;

    @FindBy(css = ".add-item")
    private List<WebElement> addButton;

    public ItemsPage(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage to(WebDriver driver) {
        get(driver, "/?pageSize=20&pageNum=1");
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage to(WebDriver driver, int pageSize, int pageNum) {
        get(driver, "/?pageSize=" + pageSize + "&pageNum=" + pageNum);
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public ItemsPage filter(String name, String valuation, String yearFounded, String industry, String founderId) {
        System.out.println(driver.getCurrentUrl());
        this.name.sendKeys(name);
        this.valuation.sendKeys(valuation);
        this.yearFounded.sendKeys(yearFounded);

        Select selectIndustry = new Select(this.industry);
        selectIndustry.selectByValue(industry);

        Select selectFounder = new Select(this.founderId);
        selectFounder.selectByValue(founderId);

        this.filter.click();

        String url = "?name=" + name + "&valuation=" + valuation + "&yearFounded=" + yearFounded + "&industry=" + industry + "&founderId=" + founderId;
        if (driver.getCurrentUrl().contains("/startups")) {
            url = "/startups" + url;
        } else {
            url = "/" + url;
        }
        assertRelativeUrl(this.driver, url.replaceAll(" ", "+"));
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertButtons(int deleteButtonsCount, int editButtonsCount, int addButtonsCount, int closeButtonsCount) {
        ExamAssert.assertEquals("delete btn count", deleteButtonsCount, this.getDeleteButtons().size());
        ExamAssert.assertEquals("edit btn count", editButtonsCount, this.getEditButtons().size());
        ExamAssert.assertEquals("add btn count", addButtonsCount, this.getAddButton().size());
        ExamAssert.assertEquals("close btn count", closeButtonsCount, this.getCloseButtons().size());
    }

    public boolean assertItems(int expectedItemsNumber) {
        return ExamAssert.assertEquals("Number of items", expectedItemsNumber, this.getRows().size());
    }
}
