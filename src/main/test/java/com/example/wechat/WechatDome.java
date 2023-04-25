package com.example.wechat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: kdm
 * @createDate: 2023/4/25
 */
@Epic("企业微信")
public class WechatDome {
    public static WebDriver driver;
    public static String PATH="src\\main\\test\\java\\com\\example\\wechat\\cookies.yaml";

    @BeforeAll
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "D:\\Webdriver\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void close() {
        driver.close();
    }

    //登录-记录cookie
    @Test
    public void loginWxs() throws Exception {
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        Thread.sleep(15000);
        //获取登录的cookies
        Set<Cookie> cookies = driver.manage().getCookies();
        //将cookies 写到yaml文件中
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File f = new File(PATH);
        //写文件之前先删除之前的文件
        if (f.exists()) {
            f.delete();
        }
        mapper.writeValue(new File(PATH), cookies);
    }

    //复用cookie
    public void reLoginWx() throws IOException, InterruptedException {
        //访问微信登录页面
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        //文件中读取cookies
        List<HashMap<String, Object>> cookies = (List) mapper.readValue(new File(PATH), typeReference);
        //登录信息中添加cookies
        cookies.forEach(cookieMap -> {
            driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString()));
        });
         Thread.sleep(1000);
         driver.navigate().refresh();//刷新页面

    }

    @Test
    @Story("搜索功能")
    @Description("搜索词为单字符")
    @DisplayName("搜索词为单字符")
    public void souSuo_1() throws IOException, InterruptedException {
        //登录微信企业号
        reLoginWx();
        Thread.sleep(3000);
        driver.findElement(By.id("menu_contacts")).click();
        jieTu();
        Thread.sleep(2000);
        driver.findElement(By.id("memberSearchInput")).click();
        jieTu();
        Thread.sleep(2000);
        driver.findElement(By.id("memberSearchInput")).sendKeys("孔");
        jieTu();
        Thread.sleep(2000);
    }


    //截图
    private static void jieTu()throws IOException{
        File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String fileName=System.currentTimeMillis()+".jpg";
        FileUtil.copyFile(file,new File("jietu/"+fileName));
        Allure.addAttachment(fileName,"image/jpg","jietu/"+fileName,".jpg");
    }

}