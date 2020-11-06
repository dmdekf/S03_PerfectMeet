package com.POM.MatNam;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumTests {
	private WebDriver driver;
	private String CHROMEDRIVER_PATH = "C:/selenium/chromedriver.exe";

	//드라이버 시작
	private void startDriver() throws IOException, WebDriverException{
		System.out.println("Driver Start!!!");
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
		driver = new ChromeDriver();
		
		//브라우저 로딩 타임아웃 5초
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				
		//웹브라우저 창 사이즈
		driver.manage().window().setSize(new Dimension(1024, 768));
		//Driver SetUp
//		ChromeOptions options = new ChromeOptions();
//		options.setCapability("ignoreProtectedModeSettings", true);
//		driver = new ChromeDriver(options);
	}
	
	//슬립 함수
	private void sleep(int sec) {
		try {
			System.out.println("[ " + sec + " ] 초간 기다립니다.");
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws WebDriverException, IOException {
		
	}
	@Test
	public void test01() throws WebDriverException, IOException {
		if(driver==null) {
			startDriver();
		}
		System.out.println("여기");
		driver.get("https://map.naver.com/v5/search/강남구 음식점");
		sleep(2);
		driver.switchTo().frame("searchIframe");
		//필터 클릭
		WebElement wel = driver.findElement(By.xpath("//*[@id=\"app-root\"]/div/div[1]/span[1]/a"));
		wel.click();
		sleep(1);
		//상견례 선택
		wel = driver.findElement(By.xpath("//*[@id=\"modal-root\"]/div/div/div/div[1]/div/div/ul/li[3]/div/a[8]"));
		wel.click();
		//적용
		wel = driver.findElement(By.xpath("//*[@id=\"modal-root\"]/div/div/div/div[2]/a[2]"));
		wel.click();
		sleep(1);
		wel = driver.findElement(By.xpath("//*[@id=\"_pcmap_list_scroll_container\"]/ul"));
		List<WebElement> list = wel.findElements(By.tagName("li"));
		for(WebElement w: list) {
			if(w.getAttribute("class").equals("_36o75 _3jJcF")) {
				w.findElement(By.className("Tx7az")).click();
				driver.switchTo().parentFrame();
				driver.switchTo().frame("entryIframe");
				System.out.println("가게 이름: "+driver.findElement(By.className("_3XamX")).getText());
				System.out.println("카테고리: "+driver.findElement(By.className("_3ocDE")).getText());
				List<WebElement> liList = driver.findElements(By.className("_1M_Iz"));
				for(int i=0;i<5;i++) {	
					WebElement temp = liList.get(i);
					
					if(temp.getAttribute("class").equals("_1M_Iz _3xPmJ")) {
						System.out.println("전화번호: "+temp.findElement(By.className("_3ZA0S")).getText());
					}else if(temp.getAttribute("class").equals("_1M_Iz _1aj6-")) {
						System.out.println("도로명주소: "+temp.findElement(By.className("_2yqUQ")).getText());
					}else if(temp.getAttribute("class").equals("_1M_Iz _2KHqk")) {
						temp.findElement(By.className("_2ZP3j")).click();
						System.out.println("영업시간: "+temp.findElement(By.className("_1h3B_")).getText());
					}else if(temp.getAttribute("class").equals("_1M_Iz undefined")&&i>2) {
						System.out.println("편의: "+temp.findElement(By.className("_1h3B_")).getText());
						break;
					}
				}
				WebElement h = driver.findElement(By.className("place_section_header"));
				List<WebElement> menuList = h.findElement(By.xpath("./../div[1]")).findElements(By.tagName("li"));
				System.out.println("메뉴: ");
				for(WebElement menu: menuList) {
					System.out.println(menu.getText());
					//System.out.println(menu.findElement(By.className("_17tyM")).getText());
				}
				driver.switchTo().parentFrame();
				driver.switchTo().frame("searchIframe");
			}
			
		}
		
	}
}
