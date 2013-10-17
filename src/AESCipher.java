import java.io.Serializable;


public class AESCipher implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	byte[] iv;
	byte[] ciphertext;
	public AESCipher(byte[] iv, byte[] ciphertext){
		this.iv = iv;
		this.ciphertext = ciphertext;
	}
}
