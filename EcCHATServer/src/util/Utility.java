package util;

import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

public class Utility {

	

	public static int getRandomNumber() {
		Random random = new Random();
		
		int randomNumber = (random.nextInt(9) + 
						+ (int) (Math.random() * ((10000 - 1000) + 1))) ;
		return randomNumber;
	}


	public static void main(String[] args) {
		System.out.println(getRandomNumber());
	}
	
	//send msg

		public static void sendMessage(String toNumber, String msg) {
			HttpClient client = null;
			PostMethod post = null;
			String sURL;
			client = new HttpClient(new MultiThreadedHttpConnectionManager());
			/* Set your proxy settings --if you have a proxy */
			// client.getHostConfiguration().setProxy("192.168.1.1",8082);

			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(30000);// set your time
			sURL = "http://smslane.com/vendorsms/pushsms.aspx";
			post = new PostMethod(sURL);
			// give all in string
			post.addParameter("user", "ajesh");
			post.addParameter("password", "DRDOCAIR");
			post.addParameter("msisdn", toNumber);
			post.addParameter("msg", msg);
			post.addParameter("sid", "WebSMS");
			post.addParameter("fl", "0");
			// System.out.println("SMS content=>"+msg);
			/* PUSH the URL */
			try {
				int statusCode = client.executeMethod(post);
				// System.out.println(post.getStatusLine().toString());
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("sndSms::Method failed:"
							+ post.getStatusLine());
				}
				System.out.println("sndSms=>" + post.getResponseBodyAsString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				post.releaseConnection();
			}
		}

	
	
	
	
}
