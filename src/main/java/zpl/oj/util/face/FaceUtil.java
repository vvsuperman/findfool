package zpl.oj.util.face;

import java.io.File;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import zpl.oj.util.http.HttpRequests;

import com.facepp.error.FaceppParseException;
import com.facepp.http.PostParameters;

@Service
public class FaceUtil {
	
	// replace api_key and api_secret here (note)
	HttpRequests httpRequests = new HttpRequests(
		    "7d3951d36130a99453f2e2d53ee60325",
		    "GYXsI9N6y17Hln6GUwdkfhQFt_UoMH1f", true, true);
	
	
	public JSONObject faceDetect(byte[] bytes) {
			// detection/detect
			JSONObject result = null;
			try {
				result = httpRequests
				    .detectionDetect(new PostParameters().setImg(bytes));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	        .setUrl(filename));
			return result;
		}
	
	public JSONObject faceCompare(String faceid1,String facdid2) {
		// recognition/compare
		JSONObject result = null;
		try {
			result = httpRequests.recognitionCompare(new PostParameters().setFaceId1(faceid1).setFaceId2(facdid2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	        .setUrl(filename));
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		FaceUtil face = new FaceUtil();
	//	String file = "F:\\Temp\\ImgUploadHome\\16_17_55.jpg";
		File imgFile = new File( "F:\\Temp\\ImgUploadHome\\16_17_55.jpg");
//		System.out.println(face.faceDetect( imgFile));
		System.out.println(imgFile
				);
	}
	

}
