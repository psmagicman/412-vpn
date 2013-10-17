
public class MessageGram {
	byte[] signature;
	AESCipher ciphertext;
	
	public MessageGram(AESCipher ciphertext, byte[] signature){
		this.signature = signature;
		this.ciphertext = ciphertext;
	}
}
