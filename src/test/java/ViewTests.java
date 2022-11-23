import com.inivos.util.AppiumTestSupport;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewTests {
    private final AndroidDriver<?> driver;
    private final String AUTOCOMPLETE_SCREENTOP_TEST = ".view.AutoComplete1";
    private final String AUTOCOMPLETE_SCROLL_TEST = ".view.AutoComplete3";
    private final String AUTOCOMPLETE_Multiple_TEST = ".view.AutoComplete6";
    private final String TOGGLE_BUTTON_TEST = ".view.Buttons1";
    private final String CHRONOMETER_TEST = ".view.ChronometerDemo";
    private final String DATEWIDGET_TEST = ".view.DateWidgets1";
    private final String DRAGDROP_TEST = ".view.DragAndDropDemo";
    private final String EXPANDABLE_LIST_TEST = ".view.ExpandableList1";
    private final String IMAGE_SWITCHER_TEST = ".view.ImageSwitcher1";
    private final String SCROLL_BARS_TEST = ".view.ScrollBar3";
    private final String RATING_BAR_TEST = ".view.RatingBar1";
    private final String SLIDING_PICKER_TEST = ".view.CustomPicker1";
    private final String SPLITTING_TOUCH_TEST = ".view.SplitTouchView";
    private final String PACKAGE;

    public ViewTests(AndroidDriver<?> driver, String packageName){
        this.PACKAGE = packageName;
        this.driver = driver;
    }

//    @BeforeMethod
//    public void TestMethodStart(){
//        System.out.println("A test Started");
//    }

    @Test
    public void testAutoCompleteScreenTop() throws InterruptedException{
        //Opening autocomplete screentop activity
        driver.startActivity(new Activity("io.appium.android.apis",AUTOCOMPLETE_SCREENTOP_TEST));

        AndroidElement TextInput = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/edit","Id");
        System.out.println(TextInput.isDisplayed());
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.S);
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.R);

        int x = TextInput.getCenter().x;
        int y = TextInput.getCenter().y + 100;

        AppiumTestSupport.tapOnCoordinate(driver,new Point(x,y));

        String actual = AppiumTestSupport.getElementText(TextInput);
        String expected = "Sri Lanka";

        driver.hideKeyboard();

        Assert.assertEquals(actual,expected);
    }
    @Test
    public void testAutoCompleteScroll() throws InterruptedException {
        //Opening autocomplete scroll activity
        driver.startActivity(new Activity(PACKAGE,AUTOCOMPLETE_SCROLL_TEST));

        //Scroll down to input
        AndroidElement ScrollView = AppiumTestSupport.locateElement(driver,"//android.widget.ScrollView","xpath");

        int stx = ScrollView.getCenter().x;
        int sty = ScrollView.getCenter().y + 600;
        int enx = ScrollView.getCenter().x;
        int eny = ScrollView.getCenter().y - 600;

        AppiumTestSupport.swipeByCoordinate(driver,new Point(stx,sty),new Point(enx,eny));

        AndroidElement TextInput = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/edit","id");
        AppiumTestSupport.pressOnElement(driver,TextInput);

        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.S);
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.R);

        AppiumTestSupport.tapOnCoordinate(driver,new Point(TextInput.getCenter().getX(),TextInput.getCenter().getY() -100));
        String actual = TextInput.getText();
        String expected = "Sri Lanka";

        AppiumTestSupport.hideKeyboard(driver);

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testAutoCompleteMultiple() throws InterruptedException {
        //Opening autocomplete Multiple Items activity
        driver.startActivity(new Activity(PACKAGE,AUTOCOMPLETE_Multiple_TEST));

        AndroidElement TextInput = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/edit","id");

        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.S);
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.R);

        int x = TextInput.getCenter().x;
        int y = TextInput.getCenter().y + 100;
        AppiumTestSupport.tapOnCoordinate(driver,new Point(x,y));

        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.U);
        AppiumTestSupport.pressAndroidKey(driver,AndroidKey.N);

        AppiumTestSupport.tapOnCoordinate(driver,new Point(x,y));

        AppiumTestSupport.hideKeyboard(driver);

        int actual = TextInput.getText().split(",").length - 1;
        int expected = 2;

        Assert.assertEquals(actual,expected);
    }

    @Test
    public void testToggleButton() throws InterruptedException {
        //Opening Views Button Activity
        driver.startActivity(new Activity(PACKAGE,TOGGLE_BUTTON_TEST));

        AndroidElement ToggleButton = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/button_toggle","id");
        String text = AppiumTestSupport.getElementText(ToggleButton);
        ToggleButton.click();
        String text1 = AppiumTestSupport.getElementText(ToggleButton);

        if(text.equals("ON")) {
            Assert.assertTrue(text1.equals("OFF"));
        }
        else{
            Assert.assertTrue(text1.equals("ON"));
        }
    }

    @Test
    public void testChronometerStart() throws InterruptedException {
        //Open Chronometer Activity
        driver.startActivity(new Activity(PACKAGE,CHRONOMETER_TEST));

        AndroidElement ChronometerText = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/chronometer","id");

        AndroidElement ChronometerStart = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/start","id");
        AndroidElement ChronometerStop = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/stop","id");

        AppiumTestSupport.tapOnElement(driver,ChronometerStart,5000);
        AppiumTestSupport.tapOnElement(driver,ChronometerStop);

//        new TouchAction<>(driver).tap(TapOptions.tapOptions()
//                        .withElement(ElementOption.element(ChronometerStart)))
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000)))
//                .perform().tap(TapOptions.tapOptions().withElement(ElementOption.element(ChronometerStop))).perform();;

        //Filter The Text
        String text = AppiumTestSupport.getElementText(ChronometerText).replaceAll("[^\\d]","");

        Assert.assertEquals(text,"0006");

        AndroidElement ChronometerResetButton = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/reset","id");
        ChronometerResetButton.click();

        text = ChronometerText.getText().replaceAll("[^\\d]","");

        Assert.assertEquals(text,"0000");
    }

    @Test
    public void testDateWidget() throws InterruptedException {
        //Open Views Date Widget Activity
        driver.startActivity(new Activity(PACKAGE, DATEWIDGET_TEST));

        //Elements
        AndroidElement Date = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/dateDisplay","id");
        AndroidElement DatePicker = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/pickDate","id");
        AndroidElement TimePicker = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/pickTime","id");
        AndroidElement TimeSpinner = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/pickTimeSpinner","id");

        //Date Text Test
        String date = AppiumTestSupport.getElementText(Date).split(" ")[0].replaceAll("-","/");
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "MM/dd/yyyy" );
        LocalDate today = LocalDate.now();
        try{
            LocalDate ld = LocalDate.parse( date , f );
            Assert.assertEquals(ld,today);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Change Date Test
        AppiumTestSupport.buttonClick(DatePicker);
        String month = today.getMonth().name();
        int newDay = today.getDayOfMonth() + 7;
        String ContentDesc = newDay + " " + month.substring(0,1) + month.substring(1).toLowerCase() + " " + today.getYear();
        String ContentDescFormatted = today.getMonthValue() + "/" + newDay + "/" + today.getYear();
        //Select Date
        AppiumTestSupport.locateElement(driver,ContentDesc,"AccessibilityId").click();
        //Click Ok
        AppiumTestSupport.locateElement(driver,"android:id/button1","id").click();

//        WebDriverWait wait = new WebDriverWait(driver,20);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("io.appium.android.apis:id/dateDisplay")));
//        date = Date.getText().split(" ")[0].replaceAll("-","/");

        date = AppiumTestSupport.getElementText(Date).split(" ")[0].replaceAll("-","/");

        try{
            LocalDate ld = LocalDate.parse( date , f );
            LocalDate nd = LocalDate.parse( ContentDescFormatted,f);
            Assert.assertEquals(ld,nd);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        //Change Time Test

        TimePicker.click();
        AppiumTestSupport.locateElement(driver,"android:id/hours","id").click();
        AppiumTestSupport.locateElement(driver,"9","AccessibilityId").click();
        AppiumTestSupport.locateElement(driver,"android:id/minutes","id").click();
        AppiumTestSupport.locateElement(driver,"45","AccessibilityId").click();
        AppiumTestSupport.locateElement(driver,"android:id/button1","id").click();

        //Time Text
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("io.appium.android.apis:id/dateDisplay")));
//        String time = Date.getText().split(" ")[1];

        String time = AppiumTestSupport.getElementText(Date).split(" ")[1];

        Assert.assertEquals(time,"09:45");
    }

    @Test
    public void testDragDrop() throws InterruptedException {
        //Opening Views Drag and drop Activity
        driver.startActivity(new Activity(PACKAGE,DRAGDROP_TEST));

        AndroidElement dot1 = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/drag_dot_1","id");
        AndroidElement dot2 = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/drag_dot_2","id");

        AppiumTestSupport.dragNDrop(driver,dot1,dot2);
        String result = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/drag_result_text","id").getText();

        Assert.assertEquals(result,"Dropped!");
    }

    @Test
    public void testExpandableList() throws InterruptedException {
        driver.startActivity(new Activity(PACKAGE,EXPANDABLE_LIST_TEST));

        List<AndroidElement> ListElements = AppiumTestSupport.locateElements(driver,"//android.widget.ExpandableListView/android.widget.TextView","xpath");

        int original = ListElements.size();

        ListElements.get(0).click();

        ListElements = AppiumTestSupport.locateElements(driver,"//android.widget.ExpandableListView/android.widget.TextView","xpath");
        int extented = ListElements.size();

        Assert.assertEquals(extented,8);

        ListElements.get(0).click();

        ListElements = AppiumTestSupport.locateElements(driver,"//android.widget.ExpandableListView/android.widget.TextView","xpath");
        int subtrcated = ListElements.size();

        Assert.assertEquals(subtrcated,original);
    }

    @Test
    public void testImageSwitcher() throws InterruptedException {
        //Opening Image Switcher Activity
        driver.startActivity(new Activity(PACKAGE,IMAGE_SWITCHER_TEST));

        //Image Gallery
        AndroidElement Gallery = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/gallery","id");

        AppiumTestSupport.swipeByPercentage(driver,0.9,0.4,0.98,"Horizontal",1000);
//        //ImageView
//        AndroidElement Image4 = com.inivos.util.AppiumTestSupport.locateElement(driver,"//android.widget.Gallery/android.widget.ImageView[4]","xpath");
//
//        com.inivos.util.AppiumTestSupport.tapOnElement(driver, Image4);
//
//        Image4 = com.inivos.util.AppiumTestSupport.locateElement(driver,"//android.widget.Gallery/android.widget.ImageView[5]","xpath");
//
//        com.inivos.util.AppiumTestSupport.tapOnElement(driver, Image4);
    }

    @Test
    public void testScrollBars() throws InterruptedException {
        //Opening Views ScrollBars Style Activity
        driver.startActivity(new Activity(PACKAGE,SCROLL_BARS_TEST));

        AndroidElement ScrollView3 = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/view3","id");

        AppiumTestSupport.swipeByPercentageOnElement(driver,ScrollView3,0.8,0.2,0.5,"Vertical",3000);

        AndroidElement startElement = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView[@content-desc=\"Lorem ipsum dolor sit amet.\"])[4]");
        AndroidElement endElement = (AndroidElement) driver.findElementByXPath("(//android.widget.TextView[@content-desc=\"Lorem ipsum dolor sit amet.\"])[1]");

        AppiumTestSupport.swipeByElements(driver,startElement,endElement);
    }

    @Test
    public void testRatingBar() throws InterruptedException {
        //Opening Views Rating bar Activity
        driver.startActivity(new Activity(PACKAGE,RATING_BAR_TEST));

        AndroidElement RatingBar = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/ratingbar2","id");
        double StartPerc = 0.1;

        double y = AppiumTestSupport.getElementCenterY(RatingBar);
        double width = AppiumTestSupport.getScreenWidth(driver);
        double x = 0.7*AppiumTestSupport.getElementWidth(RatingBar);
        double EndPerc = x/width;
        double height = AppiumTestSupport.getScreenHeight(driver);
        double AnchorPerc = y/height;

        AppiumTestSupport.swipeByPercentage(driver,StartPerc,EndPerc,AnchorPerc,"Horizontal",3000);

        AndroidElement RatingText = AppiumTestSupport.locateElement(driver,"io.appium.android.apis:id/rating","id");
        String Actual = RatingText.getText().split(":")[1].trim().split("/")[0];
        String Expected = "3.5";

        Assert.assertEquals(Actual,Expected);
    }

    @Test
    public void testSlidingPicker(){
        //Opening S
        driver.startActivity(new Activity(PACKAGE,SLIDING_PICKER_TEST));

        AndroidElement Picker = (AndroidElement) driver.findElementById("android:id/numberpicker_input");

        int startY = Picker.getCenter().getY() + 200;
        int x = Picker.getCenter().getY();
        int endY = Picker.getCenter().getY() - 200;
        new TouchAction(driver)
                .press(PointOption.point(x,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(x, endY))
                .release().perform();

        WebDriverWait wait = new WebDriverWait(driver,19);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("io.appium.android.apis:id/textView1")));

        AndroidElement PickerText = (AndroidElement) driver.findElementById("io.appium.android.apis:id/textView1");

        Assert.assertNotNull(PickerText.getText());
    }

    @Test
    public void testSplittingTouches() throws InterruptedException {
        driver.startActivity(new Activity(PACKAGE,SPLITTING_TOUCH_TEST));

        AndroidElement text1 = AppiumTestSupport.locateElement(driver,"//android.widget.ListView[1]/android.widget.TextView[1]","xpath");
        text1.click();

        AndroidElement toast = AppiumTestSupport.locateElement(driver,"//android.widget.Toast","xpath");

        String text = AppiumTestSupport.getElementText(toast);

        Assert.assertNotNull(text);

        Thread.sleep(2000);

        AppiumTestSupport.swipeByPercentage(driver,0.85,0.25,0.2,"Vertical",500);
        AppiumTestSupport.swipeByPercentage(driver,0.85,0.25,0.8,"Vertical",500);
    }
}