package zpl.oj.util.base64;

import org.apache.commons.codec.binary.Base64;

/**   
 * BASE64加密解密   
 */    
public class BASE64     
{     
    
    /**    
     * BASE64解密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static byte[] decodeBASE64(String key) throws Exception {               
        return Base64.decodeBase64(key.getBytes());               
    }               
                  
    /**         
     * BASE64加密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String encodeBASE64(byte[] key) throws Exception {               
        return new String(Base64.encodeBase64(key));             
    }       
          
}    
