package entrata.website.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {
	public static Properties prop = null;

	public static void readProperties() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(
					System.getProperty("user.dir") + "//com//entrata//website//data//setting.properties");
			prop.load(input);
		} catch (IOException e) {
			System.out.println("ERROR failed reading setting.properties file:");
			System.out.println(e.getMessage());
		}

	}

	public static String readPropStart(String key) {
		return prop.getProperty(key);
	}

	public static WebDriver getDriver(String browser) {
		WebDriver driver = null;
		switch (browser) {
		case "Chrome":
            driver = new ChromeDriver();           
			break;
		case "Edge":
			driver = new EdgeDriver(); 
			break; 
		default:
			System.out.println("No browser found");
			break;
		}
		return driver;
	}

	}
