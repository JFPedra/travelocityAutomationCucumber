package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightResultsPage extends BasePage{
    private final By searchResultsContainer = By.cssSelector("main[data-test-id='search-results']");
    private final By sortingBoxLocator = By.id("sort-filter-dropdown-SORT");
    private final Select boxSortBy = new Select(findElementBy(sortingBoxLocator));
    private final By resultContainer = By.cssSelector("li[data-test-id='offer-listing']");
    private LinkedList<String> sortingBoxValues = new LinkedList<>();

    public FlightResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(sortingBoxLocator);
        initSortingBoxValueList();

    }

    public boolean areSortingBoxValuesCorrect() {
        int index = 0;
        boolean isValueCorrect;
        List<String> sortingBoxValues = getSortingBoxValues();
        do {
            isValueCorrect = sortingBoxValues.get(index).equals(this.sortingBoxValues.get(index));
            index++;
        } while (index < sortingBoxValues.size() && isValueCorrect);
        return isValueCorrect;
    }

    private List<String> getSortingBoxValues() {
        return boxSortBy.getOptions().stream()
                .map(element -> element.getAttribute("value"))
                .collect(Collectors.toList());
    }

    private void initSortingBoxValueList() {
        sortingBoxValues.add("PRICE_INCREASING");
        sortingBoxValues.add("PRICE_DECREASING");
        sortingBoxValues.add("DURATION_INCREASING");
        sortingBoxValues.add("DURATION_DECREASING");
        sortingBoxValues.add("DEPARTURE_INCREASING");
        sortingBoxValues.add("DEPARTURE_DECREASING");
        sortingBoxValues.add("ARRIVAL_INCREASING");
        sortingBoxValues.add("ARRIVAL_DECREASING");
    }


}
