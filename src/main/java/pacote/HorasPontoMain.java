package pacote;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HorasPontoMain {

	private static Properties properties;
	private static WebDriver driver;
	
	private static List<String> listaHoras= new ArrayList<String>();
	
	
	public static void main(String[] args) throws InterruptedException {
		abrirPagina("www.site.com");
		Thread.sleep(1000);
		logarSite();
		
		listaHoras.add("11/04/2017");
		
		for (String hora: listaHoras) {
			lancarHora(hora);
		}
		
		Thread.sleep(1000);
		//driver.quit();
	}
	
	private static void logarSite() {
		driver.findElement(By.id("j_username")).sendKeys(getProperties("usuario"));
		driver.findElement(By.id("j_password")).sendKeys(getProperties("senha"));
		driver.findElement(By.id("proceed")).click();
	}
	
	private static void abrirPagina(String pagina) {
		System.setProperty("webdriver.gecko.driver","C:\\geckodriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(pagina);
	}
	
	private static void lancarHora(String hora) throws InterruptedException {
		Thread.sleep(1000);
		driver.get("www.site.com/proximapagina");
		Thread.sleep(1000);
		
		driver.findElement(By.id("_workHours_id")).sendKeys("8");
		driver.findElement(By.id("_note_id")).sendKeys(getProperties("note"));
		driver.findElement(By.id("_taskComplete_id")).click();
		driver.findElement(By.id("_estimateFinish_id")).sendKeys("0");
		
		String script = "document.getElementsByName('occurrenceDate')[0].value = '"+hora+"'";
		((JavascriptExecutor) driver).executeScript(script, driver.findElement(By.name("occurrenceDate")));
		
		script = "document.getElementsByName('project')[0].value = "+getProperties("project");
		((JavascriptExecutor) driver).executeScript(script, driver.findElement(By.name("project")));
		
		script = "document.getElementsByName('category')[0].value = "+getProperties("category");
		((JavascriptExecutor) driver).executeScript(script, driver.findElement(By.name("category")));
		
		script = "document.getElementsByName('task')[0].value = "+getProperties("task");
		((JavascriptExecutor) driver).executeScript(script, driver.findElement(By.name("task")));
		
		driver.findElement(By.id("proceed")).click();
		Thread.sleep(1000);
	}
	
	public static String getProperties(String chave) {
		
		if (properties == null) {
			properties = new Properties();
			try {
				FileInputStream input = new FileInputStream(new File("C:\\app\\HorasSellenium\\config.properties"));
				properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));
			} catch (IOException e) {
			  System.out.println("ERRO ao pegar arquivo");
			}
		}

		String valor = "";

		for(String key : properties.stringPropertyNames()) {
		  if (chave.equals(key)) {
			  valor = properties.getProperty(key);
			  break;
		  }
		}
		return valor;
	}
}
