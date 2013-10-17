import java.io.Serializable;


public class MessageGram implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3078148301408414865L;
	byte[] signature;
	AESCipher ciphertext;
	
	public MessageGram(AESCipher ciphertext, byte[] signature){
		this.signature = signature;
		this.ciphertext = ciphertext;
	}
}
