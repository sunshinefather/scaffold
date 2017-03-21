package test;


public class Test {
	public static byte[] int2ByteArray(int i) {   
		  byte[] result = new byte[4];
		  result[0] = (byte)((i >> 24) & 0xFF);
		  result[1] = (byte)((i >> 16) & 0xFF);
		  result[2] = (byte)((i >> 8) & 0xFF); 
		  result[3] = (byte)(i & 0xFF);
		  return result;
		}
	 public static byte[] toByteArray(int number) {

		    int temp = number;
		    
		    byte[] b = new byte[4];

		    for (int i = b.length - 1; i > -1; i--)
		    {

		      b[i] = new Long(temp & 0xff).byteValue();

		      temp = temp >> 8;

		    }

		    return b;
	 }
	 public static int bytesToInt(byte[] src) {  
		    int value;    
		    value = (int)(
		    		((src[0] & 0xFF)<<24)  
		            |((src[1] & 0xFF)<<16)  
		            |((src[2] & 0xFF)<<8)  
		            |(src[3] & 0xFF)
		            );  
		    return value;  
		}  
	public static void main(String[] args) throws Exception {
		
    }
}