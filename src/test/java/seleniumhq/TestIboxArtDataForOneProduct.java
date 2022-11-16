package seleniumhq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * <br/>Description : 描述
 * <br/>CreateTime : 2022/10/17
 * @author hanhaotian
 */
@Slf4j
public class TestIboxArtDataForOneProduct {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        TestIboxArtDataForList.addArguments(options);

        ChromeDriver driver = new ChromeDriver(options);
        Actions action = new Actions(driver);
        Long startPid = 100513304L;  //库中最新的藏品id
        Long latestPId = 100513305L;  //最新的藏品id
        //1.获取采集的范围: 一级市场列表页的第一个藏品id -> 库中最新的藏品id
        if (latestPId == null || latestPId <= 0) {
            driver.get("https://www.ibox.art/zh-cn/find/");
            Thread.sleep(3000);

            //藏品列表最外层, 每500ms检测一次目标元素是否可见, 否则等待30s
            List<WebElement> webElementsList = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("product-container")));
            WebElement productElement = webElementsList.get(0);

            //先将光标移动到封面图聚焦, 再移动到触发点, 点击进入藏品详情
            WebElement coverImgElement = productElement.findElement(By.tagName("img"));
            WebElement coverButton = productElement.findElement(By.tagName("button"));
            WebElement coverSpanText = coverButton.findElement(By.className("text"));
            action.moveToElement(coverImgElement).perform();
            action.moveToElement(coverSpanText).perform();
            action.click().perform();
            Thread.sleep(3000);

            //藏品详情页操作, 获取最新的pid
            String latestPIdStr = StrUtil.subBetween(driver.getCurrentUrl(), "id=", "&");
            latestPId = Long.valueOf(latestPIdStr);
            System.out.println(latestPId + " " + driver.getCurrentUrl());
        }

        //2.遍历pid,获取每个藏品详情,从库中最老藏品 到 列表中最新藏品
        for (Long i = startPid; i <= latestPId; i++) {
            driver.get("https://ibox.art/zh-cn/item/?id=" + i);
            Thread.sleep(3000);

            //藏品详情页操作
            WebElement pNameElement = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("p-name")));
            String pName = StrUtil.subBefore(pNameElement.getText(), "#", true);
            //过滤名称为"--#"的藏品
            if (pName.equalsIgnoreCase("--")) {
                log.error("无效的pid:{}", i);
                continue;
            }
            if (!TestIboxArtDataForList.filterBlockWord(pName)) {
                log.error("无效的pid:{}, 藏品名称不符合规则:{}", i, pName);
                continue;
            }

            //查看藏品状态
            WebElement buyBtnEle = driver.findElement(By.className("btn-buy"));
            WebElement buyStatusEle = buyBtnEle.findElement(By.className("text"));
            String text = buyStatusEle.getText();
            if (text.equalsIgnoreCase("已提出平台")) {
                log.error("无效的pid:{}, 该藏品已被提出平台:{}", i, pName);
                continue;
            }

            //获取藏品图片
            WebElement imgContainer = driver.findElement(By.className("media-wrapper"));
            WebElement imgElement = imgContainer.findElement(By.tagName("img"));
            String imgSrc = imgElement.getAttribute("src");

            //获取铸造量和流通量
            Long distributeNum = 0L;
            Long circulationNum = 0L;
            List<WebElement> dcnContainer = driver.findElements(By.className("dcn-container"));
            if (CollUtil.isNotEmpty(dcnContainer) && dcnContainer.size() >= 2) {
                WebElement dcn1 = dcnContainer.get(2);
                List<WebElement> span1 = dcn1.findElements(By.tagName("span"));
                if (CollUtil.isNotEmpty(span1) && span1.size() >= 2) {
                    String numStrFull = span1.get(1).getText();
                    String numStr = StrUtil.subBefore(numStrFull, "份", false);
                    distributeNum = Long.valueOf(numStr);
                }

                WebElement dcn2 = dcnContainer.get(3);
                List<WebElement> span2 = dcn2.findElements(By.tagName("span"));
                if (CollUtil.isNotEmpty(span2) && span2.size() >= 2) {
                    WebElement webElement = span2.get(1);
                    String numStr = StrUtil.subBefore(webElement.getText(), "份", false);
                    circulationNum = Long.valueOf(numStr);
                }
            }

            //获取藏品价格:当没有挂单时页面展示[已售出],此时设置价格为0
            Double price;
            try {
                WebElement priceElement = driver.findElement(By.className("o-price"));
                String priceText = priceElement.getText();
                String priceStr = StrUtil.subAfter(priceText, "￥", false).trim();
                price = Double.valueOf(priceStr);
            } catch (Exception e) {
                price = 0.0;
            }

            log.info("当前藏品pId:{}, pName:{}, 最新价格:{}, 发行量:{}, 流通量:{}, imgSrc:{}", i, pName, price, distributeNum, circulationNum, imgSrc);
            //System.out.println("当前藏品pId:" + i + ", pName:" + pName + ", 最新价格:" + price + ", 发行量:" + distributeNum + ", 流通量:" + circulationNum + ", imgSrc:" + imgSrc);
        }
    }

}