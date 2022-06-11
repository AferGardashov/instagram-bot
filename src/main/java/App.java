import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    private WebDriver driver;
    private String baseURL = "https://www.instagram.com/";
    private By usernameBtn = By.cssSelector("input[name='username']");
    private By passwordBtn = By.cssSelector("input[name='password']");
    private By loginBtn = By.cssSelector("button[type='submit']");
    private By logo = By.cssSelector("div[data-testid='instagram-homepage-logo']");
    private By firstPicture = By.className("_aagu");
    private By htmlTag = By.tagName("html");
    private By likeBtn = By.xpath("//section[@class='_aamu _aat0']/span[1]/button");
    private By posts = By.xpath("//li[@class='_aa_5']/div[@class='_aacl _aacp _aacu _aacx _aad6 _aade']/span[1]");
    private By closeBtn = By.cssSelector("path[clip-rule='evenodd']");
    private String username;
    private String password;
    private Integer postCount;

    public App(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void runBot(String profileName) {
        launch();
        login();
        navigateToProfile(profileName);
        Integer postCount = getPostCount();
        this.postCount = postCount;
        clickPicture();
        likeAllPictures();
    }

    private void launch() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
    }

    private void login() {
        waitfor(usernameBtn);

        driver.findElement(usernameBtn).sendKeys(username);
        driver.findElement(passwordBtn).sendKeys(password);
        driver.findElement(loginBtn).click();

    }

    private void navigateToProfile(String profileName) {
        waitfor(logo);
        String profileURL = "https://www.instagram.com/" + profileName;
        driver.navigate().to(profileURL);
    }


    private void clickPicture() {
        waitfor(firstPicture);
        driver.findElement(firstPicture).click();
    }

    private void likeAllPictures() {
        while (postCount > 0) {
            waitfor(likeBtn);
            driver.findElement(likeBtn).click();
            driver.findElement(htmlTag).sendKeys(Keys.ARROW_RIGHT);
            postCount--;
        }
        waitfor(closeBtn);
        driver.findElement(closeBtn).click();
    }


    private Integer getPostCount() {
        waitfor(posts);
        String text = driver.findElement(posts).getText();
        return Integer.parseInt(text);
    }

    private void waitfor(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
