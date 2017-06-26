package com.leptonmaps.wirelinefeasibility.appnetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class AppNetwork {

    private static final String TAG = AppNetwork.class.getSimpleName();
    private static int CONNECTION_TIMEOUT = 8000;
    public static AppNetwork instance = null;
    private boolean init = false;
    private Context ctx = null;
    //private MsgLoopThread msgLoopThread = null;
    public static Handler uiHandlr = null;
    public Handler netHandlr = null;
    //private ObjectMapper mapperObj = null;
    int serverResponseCode = 0;
    private AppNetwork() {
    }

    public static AppNetwork getInstance() {
        if (instance == null)
            instance = new AppNetwork();
        return instance;
    }

    /**
     * To initialize network message loop to handle network calls.
     *
     * @param ctx
     * @return {@link if initialization is success then return <code>true</code>
     *         else <code>false</code>}
     */
    public boolean init(Context ctx) {
        if (init)
            return true;
        if (ctx == null)
            return init;
        this.ctx = ctx;
        init = true;
        return init;
    }

    /** Checks whether the device currently has a network connection
     * @param context TODO*/
    public static boolean isDeviceOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }// End of isDeviceOnline().


    public ReqRespBean sendHttpRequest(ReqRespBean mReqRespBean){

        if(mReqRespBean == null ){
            Log.e(TAG, "Request is null");
            Log.e(TAG,"sendHttpRequest(): Request params are null");
            return null;
        }
        String method = mReqRespBean.getRequestmethod();
        String url = mReqRespBean.getUrl();
        String requestJson = mReqRespBean.getJson();
        HashMap<String, String> header = mReqRespBean.getHeader();
        String mimeType = mReqRespBean.getMimeType();
        Log.i(TAG,"sendHttpRequest():method:"+method);
        Log.i(TAG,"sendHttpRequest():Request jason:"+requestJson);
        Log.i(TAG,"sendHttpRequest():mimeType:"+mimeType);
        int read;
        if((url == null || url.length() == 0)){
            System.out.println( TAG + "httpPostRequest: IllegalArgument: Url is null");
            Log.e(TAG,"sendHttpRequest(): Request URl is null");
            Log.e(TAG, "URL is null");
            return null;
        }/*else if(! mReqRespBean.getRequestmethod().equalsIgnoreCase("get")
				|| ! mReqRespBean.getRequestmethod().equalsIgnoreCase("post")){
			System.out.println( TAG + "httpPostRequest: IllegalArgument:invalid method type:"+mReqRespBean.getRequestmethod());
			Log.e(TAG, "invalid method type:" + mReqRespBean.getRequestmethod());
			return  null;
		}*/
        Log.i(TAG, "Request data:"+requestJson);
        HttpURLConnection conn = null;
        try{
            URL outUrl = new URL(url);
            URI uri = new URI(outUrl.getProtocol(), outUrl.getUserInfo(), outUrl.getHost(), outUrl.getPort(), outUrl.getPath(), outUrl.getQuery(), outUrl.getRef());
            outUrl = uri.toURL();
            Log.i(TAG, "Url:"+outUrl);
            conn = (HttpURLConnection)outUrl.openConnection();
            if(conn != null) {
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                HttpURLConnection.setFollowRedirects(true);
                conn.setDoOutput(!method.equalsIgnoreCase("GET"));
                conn.setRequestMethod(method.toUpperCase());
                conn.setRequestProperty("Content-Type", mimeType);
                conn.setRequestProperty("Connection", "Close");
                System.setProperty("http.keepAlive", "false");
                if(header != null){
                    //conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    Iterator it = header.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        //System.out.println(pair.getKey() + " = " + pair.getValue());
                        Log.i(TAG,"Header:"+pair.getKey().toString()+":"+pair.getValue().toString());
                        conn.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                        it.remove();
                    }
                }
                if(requestJson != null) {
					/*OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					wr.write(requestJson);
					wr.flush();
					wr.close();*/
                    OutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                    writer.write(requestJson);
                    writer.flush();
                    writer.close();
                    outputStream.close();
                }
            }
            conn.connect();
            mReqRespBean.setHttpResp(conn.getResponseCode());
            InputStream iStrm = null;
            //AppLogUtil.getInstance().writeLog(TAG,"sendHttpRequest():Http Response Code:"+conn.getResponseCode());
            Log.i(TAG,"sendHttpRequest(): HttpResponse:"+conn.getResponseCode());
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i(TAG,"sendHttpRequest(): HttpResponse");
                iStrm = conn.getInputStream();

            }else{
                int respCode = conn.getResponseCode();
                iStrm = conn.getErrorStream();
            }
            mReqRespBean.setHttpResponseCode(conn.getResponseCode());
            if(iStrm == null){
                System.out.println(TAG + "httpPostRequest: No inputStream is created.");
                return null;
            }
            else{
				/*StringBuffer sb = new StringBuffer();
				while((read = iStrm.read() ) != -1){
					sb.append((char) read);
				}
				responseJson = sb.toString();*/
                BufferedReader br = new BufferedReader(new InputStreamReader(iStrm));
                StringBuffer sb  = new StringBuffer();
                String line = "";
                while(( line = br.readLine())  != null){
                    sb.append(line);
                }
                mReqRespBean.setJson(sb.toString());
                Log.i(TAG, "Response json:" + sb.toString());
                br.close();
            }
        }
        catch(Exception e){
            mReqRespBean.setJson(e.getMessage());
            Log.e(TAG, "sendHttpRequest():Exception:" + e);
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return mReqRespBean;
    }// End of httpPostRequest().

    /**
     * Inner class for preparing msg Queue.
     *
     * @author Naresh
     *
     */


	/*public String convertBeanToJson(Object bean) {
		String reqQuery = null;
		if (bean == null)
			return reqQuery;
		try {
			reqQuery = mapperObj.writeValueAsString(bean);
			Log.d(TAG, "convertBeanToJson(): request query is:" + reqQuery);
		} catch (JsonGenerationException e) {
			Log.e(TAG, "convertBeanToJson():" + e);
		} catch (JsonMappingException e) {
			Log.e(TAG, "convertBeanToJson():" + e);
		} catch (IOException e) {
			Log.e(TAG, "convertBeanToJson(): " + e);
		}
		return reqQuery;
	}// End of convertBeanToJson()

	public Object convertJsonToBean(APIType type, String jsonResp) {
		if (jsonResp == null)
			return null;
		Object obj = null;
		try {
			switch (type) {
			case LOGIN:
				UserBean userbean = mapperObj.readValue(jsonResp,
						UserBean.class);
				obj = userbean;
				break;
}
		} catch (JsonParseException e) {
			Log.e(TAG, "convertJsonToBean():" + e);
		} catch (JsonMappingException e) {
			Log.e(TAG, "convertJsonToBean():" + e);
		} catch (IOException e) {
			Log.e(TAG, "convertJsonToBean():" + e);
		}

		return obj;
	}

	public String convertHashMapToJson(HashMap<String, String> map){
		String json = "";
		try {
			json = mapperObj.writeValueAsString(map);
		} catch (JsonGenerationException e) {
			Log.e(TAG, "convertHashMapToJson():" + e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			Log.e(TAG, "convertHashMapToJson():" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "convertHashMapToJson():" + e);
			e.printStackTrace();
		}
		return json;
	}*/
	/*public int uploadFile(String sourceFileUri,String upLoadServerUri) {
		String fileName = sourceFileUri;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);

		if (!sourceFile.isFile()) {
			//TODO ERROR
			return 0;
		}
		else{
			try {
				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(upLoadServerUri);
				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName);
				//conn.setRequestProperty("username", "testing");
				dos = new DataOutputStream(conn.getOutputStream());

			        // sending appid
			        dos.writeBytes(twoHyphens + boundary + lineEnd);
			        dos.writeBytes("Content-Disposition: form-data; name=\"username\""+ lineEnd);
			        dos.writeBytes(lineEnd);
			        dos.writeBytes("testing");
			        dos.writeBytes(lineEnd);

			        dos.writeBytes(twoHyphens + boundary + lineEnd);
			        dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="  + fileName + "" + lineEnd);
			        dos.writeBytes(lineEnd);

				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				while (bytesRead > 0) {
					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();
				Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
				if(serverResponseCode == 200){
					//TODO Success
					InputStream iStrm = conn.getInputStream();
					if (iStrm == null) {
						System.out
						.println(TAG
								+ "httpPostRequest: No inputStream is created to data from network");

					} else {

						//StringBuffer sb = new StringBuffer();

						while((read = iStrm.read() ) != -1){
							sb.append((char) read); }
						// responseJson = sb.toString();
						char[] inbuffer = new char[2048];
						BufferedReader br = new BufferedReader(new InputStreamReader(iStrm),inbuffer.length);
						StringBuffer sb = new StringBuffer(inbuffer.length);
						String line = "";
						while ((line = br.readLine()) != null) {
							sb.append(line);
						}
						String responseJson = sb.toString();
						Log.i(TAG, "sendHttpRequest(): Response:" + responseJson);
						br.close();
						iStrm.close();
						sb = null;
					}
				}
				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			} catch (Exception e) {
				Log.e("Upload file to server Exception", "Exception : "	+ e.getMessage(), e);
			}
			return serverResponseCode;

		} // End else block
	}
	 */


    public String sendPLaceRequest(String reqMethod, String url, String requestJson) {
        int read;
        if ((url == null)) {
            System.out.println(TAG + "httpPostRequest: IllegalArgument are passed to method");
            Log.e(TAG, "URL is null");
            return null;
        }
        Log.i(TAG, "Request data:" + requestJson);
        String responseJson = null;
        try {
            InputStream iStrm = null;
            HttpURLConnection conn = null;
            URL outUrl = new URL(url);
            Log.i(TAG, "sendHttpRequest(): Url:" + outUrl);
            conn = (HttpURLConnection) outUrl.openConnection();
            if (conn != null) conn.setConnectTimeout(8000);
            if (reqMethod == "post") {
                HttpURLConnection.setFollowRedirects(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setRequestProperty("Authorization", AppConstants.TOKEN);
                OutputStreamWriter wr = new OutputStreamWriter(	conn.getOutputStream());
                wr.write(requestJson);
                wr.flush();
                wr.close();
            }
            conn.connect();
            iStrm = conn.getInputStream();
            if (iStrm == null) {
                System.out
                        .println(TAG
                                + "httpPostRequest: No inputStream is created to data from network");
                return null;
            } else {

                //StringBuffer sb = new StringBuffer();

				/*while((read = iStrm.read() ) != -1){
					sb.append((char) read); } */
                // responseJson = sb.toString();
                char[] buffer = new char[2048];
                BufferedReader br = new BufferedReader(new InputStreamReader(iStrm),buffer.length);
                StringBuffer sb = new StringBuffer(buffer.length);
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                responseJson = sb.toString();
                Log.i(TAG, "sendHttpRequest(): Response:" + responseJson);
                br.close();
                iStrm.close();
                sb = null;
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(TAG+ "httpPostRequest:Caught an exception when sending request:"	+ e);
            Log.e(TAG, "sendHttpRequest():" + e);
        }
        return responseJson;
    }// End of httpPostRequest().

}// End of class.
