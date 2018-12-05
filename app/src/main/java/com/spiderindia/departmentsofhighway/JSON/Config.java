package com.spiderindia.departmentsofhighway.JSON;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pyr on 19-May-18.
 */

public class Config
{
    private JSON jsonParser;

    public static final String POSITION="pos";

    public  static Typeface font,fontMedium,fontBold,fontBoldI;

    public  static String baseUrl="http://tnhighwaysmobileapp.com/highways/";

    private static String url = baseUrl+"webservice/user/login";

    private static String sendOtp_Url = baseUrl+"webservice/user/otpVerify";

    private static String getBridgeCircle_Url = baseUrl+"bms-rest/bridge/getCircleData";

    private static String getCircleHolder_Url = baseUrl+"bms-rest/bridge/getAllRemainigData";

    private static String getDivision_Url = baseUrl+"bms-rest/bridge/getDivisionData";

    private static String getSubDivision_Url = baseUrl+"bms-rest/bridge/getSubDivisionData";

    private static String getRoad_Url = baseUrl+"bms-rest/bridge/getRoadData";

    private static String getLink_Url = baseUrl+"bms-rest/bridge/getLinkData";

    private static String sendBridgeDetails_Url = baseUrl+"bms-rest/bridge/addBridgeDetails";

    private static String logout_Url = baseUrl+"bms-rest/user/logout";

    // constructor
    public Config()
    {
        jsonParser = new JSON();
    }

    public Config(Context context)
    {
        //font = Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Regular.ttf");
        font = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-Regular.ttf");
        fontBold = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-Bold.ttf");
        fontBoldI = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-BoldItalic.ttf");
       /* fontMedium = Typeface.createFromAsset(context.getAssets(),"fonts/Montserrat-Medium.ttf");
        fontBold = Typeface.createFromAsset(context.getAssets(),"fonts/Muli-Bold.ttf");*/
    }

    public JSONObject LoginProcess(String userName)
    {
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("phone_no",userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(url, postDataParams);
        return json;
    }

    public JSONObject LogoutProcess(String userId, String authenticationToken) {
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("client_id",userId);
            postDataParams.put("authentication_token",authenticationToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(logout_Url, postDataParams);
        return json;

    }

    public JSONObject senOtpToServer(String mob_no, String otp) {
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("phone_no",mob_no);
            postDataParams.put("otp",otp);
            //postDataParams.put("fcmRegId",fcmRegId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = JSON.postJSONObject(sendOtp_Url, postDataParams);



        return json;
    }
    public JSONObject getBridgeCircleDetailsFromServer(String userId, String authenticationToken) {
        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getBridgeCircle_Url, postDataParams);
        return json;
    }

    public JSONObject getCircleHolderDetailsFromServer( String userId, String authenticationToken,String circle_id) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("circle_id",circle_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getCircleHolder_Url, postDataParams);
        return json;

    }
    public JSONObject getDivisionDetailsFromServer( String userId, String authenticationToken,String circle_id) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("circle_id",circle_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getDivision_Url, postDataParams);
        return json;

    }
    public JSONObject getSubDivisionDetailsFromServer( String userId, String authenticationToken,String division_id) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("division_id",division_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getSubDivision_Url, postDataParams);
        return json;

    }

    public JSONObject getRoadDetailsFromServer( String userId, String authenticationToken,String sub_division_id) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("sub_division_id",sub_division_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getRoad_Url, postDataParams);
        return json;

    }
    public JSONObject getLinkIdDetailsFromServer( String userId, String authenticationToken,String link_id) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("road_id",link_id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject json = jsonParser.postJSONObject(getLink_Url, postDataParams);
        return json;

    }
    public JSONObject sendBridgeDetailsToServer(String userId, String authenticationToken, String circleId, String divisionId, String subDivisionId,
                                                String roadId, String linkId, String encodedFirstImagePath, String encodedSecondImagePath, JSONObject jObj) {

        JSONObject postDataParams = new JSONObject();
        try {
            postDataParams.put("user_id",userId);
            postDataParams.put("authentication_token",authenticationToken);
            postDataParams.put("circle_id",circleId);
            postDataParams.put("division_id",divisionId);
            postDataParams.put("sub_division_id",subDivisionId);
            postDataParams.put("road_id",roadId);
            postDataParams.put("link_id",linkId);
            postDataParams.put("first_image",encodedFirstImagePath);
            postDataParams.put("second_image",encodedSecondImagePath);
            postDataParams.put("bridgeDetails",jObj);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("postDataParams final "+jObj);

        JSONObject json = jsonParser.postJSONObject(sendBridgeDetails_Url, postDataParams);
        return json;

    }
}
