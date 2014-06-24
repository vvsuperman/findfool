package zpl.oj.util.PropertiesUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String filePath){
		//将properties文件加载到输入字节流中 
        InputStream is;
		try {
			is = new FileInputStream(filePath);
	        //创建一个Properties容器 
	        Properties prop = new Properties(); 
	        //从流中加载properties文件信息 
	        try {
				prop.load(is);
				return prop;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;

	}
}
