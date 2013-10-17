import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;


public class VPNAuth {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
	
	public static byte[] computeSignature(PrivateKey privateKey, byte[] message){
		try {
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(privateKey);
			sig.update(message);
			byte[] signature = sig.sign();
			return signature;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean verifySignature(PublicKey pubKey, byte[] message, byte[] signature){
		try {
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(pubKey);
			sig.update(message);
			return sig.verify(signature);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
}
