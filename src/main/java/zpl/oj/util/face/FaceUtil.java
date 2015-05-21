package zpl.oj.util.face;

import java.io.File;

import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class FaceUtil {
	
	public JSONObject faceDetect(String filename) throws FaceppParseException{
		
		// replace api_key and api_secret here (note)
		HttpRequests httpRequests = new HttpRequests(
		    "4480afa9b8b364e30ba03819f3e9eff5",
		    "Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M ", true, true);


		// detection/detect
		JSONObject result = httpRequests
		    .detectionDetect(new PostParameters()
		        .setUrl(filename));
		return result;
	}
	
	
public JSONObject faceDetect(File filename) throws FaceppParseException{
		
		// replace api_key and api_secret here (note)
		HttpRequests httpRequests = new HttpRequests(
		    "4480afa9b8b364e30ba03819f3e9eff5",
		    "Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M ", true, true);

		// detection/detect
		JSONObject result = httpRequests
		    .detectionDetect(new PostParameters().setImg(filename));
//		        .setUrl(filename));
		return result;
	}
	
	public static void main(String[] args) throws FaceppParseException{
		FaceUtil face = new FaceUtil();
	//	String file = "F:\\Temp\\ImgUploadHome\\16_17_55.jpg";
		File imgFile = new File( "F:\\Temp\\ImgUploadHome\\16_17_55.jpg");
		System.out.println(face.faceDetect( imgFile));
	}
	

}
