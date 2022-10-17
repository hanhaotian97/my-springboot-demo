package seleniumhq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2022/10/17
 * @author hanhaotian
 */
public class testIboxArtData {
    public static void main(String[] args) throws InterruptedException {
        // System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        addArguments(options);
        ChromeDriver driver = new ChromeDriver(options);
        //一级市场-藏品
        driver.get("https://www.ibox.art/zh-cn/find/");
        Thread.sleep(1000);

        Actions action = new Actions(driver);
        //藏品列表最外层
        /*List<WebElement> elementsByClassName = driver.findElementsByClassName("product-container");
        for (WebElement webElement : elementsByClassName) {
            WebElement img = webElement.findElement(By.tagName("img"));
            String imgSrc = img.getAttribute("src");
            System.out.println(driver.getCurrentUrl() + "   " + imgSrc);

            //鼠标点击进入藏品详情
            WebElement button = webElement.findElement(By.tagName("button"));
            WebElement spanText = button.findElement(By.className("text"));
            //先将光标移动到封面图聚焦，再点击进入详情
            action.moveToElement(img).perform();
            action.moveToElement(spanText).perform();
            action.click().perform();

            Thread.sleep(1000);

            //藏品详情页操作
            WebElement pName = driver.findElement(By.className("p-name"));
            String productName = pName.getText();
            String currentUrl = driver.getCurrentUrl();
            System.out.println(currentUrl + "   " + productName);

            // 浏览器后退，回到列表页
            driver.navigate().back();
        }*/

        //藏品列表最外层, 每500ms检测一次目标元素是否可见, 否则等待30s
        List<WebElement> elementsByClassName2 = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("product-container")));
        Map<Integer, String> imgMap = new HashMap<>();
        int currentNum = 0;
        for (WebElement webElement : elementsByClassName2) {
            //等待, 直到获取图片src
            WebElement img = webElement.findElement(By.tagName("img"));
            String imgSrc = img.getAttribute("src");
            while (!StrUtil.sub(imgSrc, 0, 4).equals("http")) {
                Thread.sleep(3000);
                img = webElement.findElement(By.tagName("img"));
                imgSrc = img.getAttribute("src");
                System.out.println("获取藏品概览图片地址中...");

                //由于图片是懒加载的需要向下滚动
                new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
            }
            imgMap.put(currentNum++, imgSrc);
        }

        for (int i = 0; i < 30; i++) {
            //每次都查找一次列表元素
            List<WebElement> elementsByClassName = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("product-container")));
            WebElement webElement = elementsByClassName.get(i);

            //等待, 直到获取图片src
            WebElement img = webElement.findElement(By.tagName("img"));

            //先将光标移动到封面图聚焦, 再移动到触发点, 点击进入藏品详情
            WebElement button = webElement.findElement(By.tagName("button"));
            WebElement spanText = button.findElement(By.className("text"));
            action.moveToElement(img).perform();
            action.moveToElement(spanText).perform();
            action.click().perform();

            Thread.sleep(3000);
            //藏品详情页操作
            WebElement pNameElement = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("p-name")));
            String pId = StrUtil.subBetween(driver.getCurrentUrl(), "id=", "&");
            String pName = StrUtil.subBefore(pNameElement.getText(), "#", true);
            System.out.println("当前次数:" + i + ", pId:" + pId + ", pName:" + pName + ", imgSrc:" + imgMap.get(i));


            List<WebElement> dcnTitles = driver.findElements(By.className("dcn-title"));
            if (CollUtil.isNotEmpty(dcnTitles) && dcnTitles.size() >= 2) {

            }


            // 浏览器后退，回到列表页
            driver.navigate().back();
            Thread.sleep(3000);
        }
        driver.quit();
    }

    public static boolean isDisplayed(WebElement element) {
        /*while (!isDisplayed(pNameElement)) {
            Thread.sleep(3000);
            System.out.println("Element is not visible yet");
        }*/
        try {
            if (element.isDisplayed()) return element.isDisplayed();
        } catch (NoSuchElementException ex) {
            return false;
        }
        return false;
    }

    @Test
    public void test() {
        String currentUrl = "https://www.ibox.art/zh-cn/item/?id=100515008&gid=106038283 ";
        String pId = StrUtil.subBetween(currentUrl, "id=", "&");
        String pName = StrUtil.subBefore("嵌泅庭#356", "#", true);
        System.out.println("pId:" + pId + ", pName:" + pName);

        System.out.println(StrUtil.sub(currentUrl, 0, 4));
    }

    private static void addArguments(ChromeOptions options) {
        // EAGER 等待整个dom树加载完成，即DOMContentLoaded这个事件完成，也就是只要 HTML 完全加载和解析完毕就开始执行操作。放弃等待图片、样式、子帧的加载。
        // 因为要获取图片, 所以使用NORMAL模式
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // 启用开发者工具
        //options.addArguments("auto-open-devtools-for-tabs");

        /*// 无界面模式
        options.addArguments("headless");
        // 指定用户客户端-模拟手机浏览
        options.addArguments("user-agent=\"MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1\"");
        // 禁用图片加载
        options.addArguments("blink-settings=imagesEnabled=false");
        // 隐身模式
        options.addArguments("incognito");
        // 设置窗口尺寸，注意宽高之间使用逗号而不是x
        options.addArguments("window-size=300,600");
        // 设置窗口启动位置（左上角坐标）
        options.addArguments("window-position=120,0");
        // 禁用gpu渲染
        options.addArguments("disable-gpu");
        // 全屏启动
        options.addArguments("start-fullscreen");
        // 全屏启动，无地址栏
        options.addArguments("kiosk");
        // 启动时，不激活（前置）窗口
        options.addArguments("no-startup-window");*/
    }
}