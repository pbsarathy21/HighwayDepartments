package com.spiderindia.departmentsofhighway.JSON;

import com.spiderindia.departmentsofhighway.ModelClasses.ModelAddBridgeResponse.AddBridgeResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.BridgeResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCircleResponse.CircleResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvertAddResponse.CulvertAddResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvertEditResponse.CulvetEditResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvetResponse.CulvertResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelDivisionResponse.DivisionResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelEdidBridgeResponse.EditBridgeResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelLinkResponse.LinkResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelLoginResponse.LoginResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelRoadResponse.RoadResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelSubdivisionResponse.SubdivisionResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("webservice/user/otpVerify")
    Call<LoginResponse> getUserDetails(
            @Body String Login
    );

    @Headers("Content-Type: application/json")
    @POST("webservice/user/getCircleDetails")
    Call<CircleResponse> getCircleDetails(
            @Body String circle
    );

    @Headers("Content-Type: application/json")
    @POST("webservice/user/getDivisionDetails")
    Call<DivisionResponse> getDivisionDetails(
            @Body String division
    );

    @Headers("Content-Type: application/json")
    @POST("webservice/user/getSubDivisionDetails")
    Call<SubdivisionResponse> getSubdivisionDetails(
            @Body String subdivision
    );


    @Headers("Content-Type: application/json")
    @POST("webservice/user/getRoadsDetails")
    Call<RoadResponse> getRoadDetails(
            @Body String RoadId
    );


    @Headers("Content-Type: application/json")
    @POST("webservice/user/getLinksDetails")
    Call<LinkResponse> getLinkDetails(
            @Body String LinkId
    );

    @Headers("Content-Type: application/json")
    @POST("webservice/user/getBridgeDetails")
    Call<BridgeResponse> getBridgeDetails(
            @Body String bridge
    );

    @Multipart
    @POST("webservice/user/addBridge")
    Call<AddBridgeResponse> addBridgeDetails(
                                             @Part("circle_key_id") String CIRCLE_KEY_ID,
                                             @Part("division_id") String DIVISION_ID,
                                             @Part("sub_division_id") String SUB_DIVISION_ID,
                                             @Part("road_id") String ROAD_ID,
                                             @Part("link_id") String LINK_ID,
                                             @Part("user_id") String USER_ID,
                                             @Part("location") String LOCATION,
                                             @Part("start_chainage") String START_CHAINAGE,
                                             @Part("bridge_id") String BRIDGE_ID,
                                             @Part("bridge_name") String BRIDGE_NAME,
                                             @Part("bridge_no") String BRIDGE_NO,
                                             @Part("year_constuct") String YEAR_CONSTUCT,
                                             @Part("construction_cost") String CONSTRUCTION_COST,
                                             @Part MultipartBody.Part attach_photo,
                                             @Part MultipartBody.Part attach_document,
                                             @Part("bridge_type") String BRIDGE_TYPE,
                                             @Part("bridge_length") String BRIDGE_LENGTH,
                                             @Part("loading") String LOADING,
                                             @Part("partial_widening") String PARTIAL_WIDENING,
                                             @Part("linear_water_way") String LINEAR_WATER_WAY,
                                             @Part("close_to_dam") String CLOSE_TO_DAM,
                                             @Part("bridge_width") String BRIDGE_WIDTH,
                                             @Part("river_name") String RIVER_NAME,
                                             @Part("flow_of_river") String FLOW_OF_RIVER,
                                             @Part("slab_design") String SLAB_DESIGN,
                                             @Part("ownership_of_river") String OWNERSHIP_OF_RIVER,
                                             @Part("no_of_lanes") String NO_OF_LANES,
                                             @Part("no_of_spans") String NO_OF_SPANS,
                                             @Part("max_span_width") String MAX_SPAN_WIDTH,
                                             @Part("vertical_clearance") String VERTICAL_CLEARANCE,
                                             @Part("left_foot_path") String LEFT_FOOT_PATH,
                                             @Part("right_foot_path") String RIGHT_FOOT_PATH,
                                             @Part("super_structure_type") String SUPER_STRUCTURE_TYPE,
                                             @Part("slab_thickness") String SLAB_THICKNESS,
                                             @Part("bearing_type") String BEARING_TYPE,
                                             @Part("parapet_hand_rail_type") String PARAPET_HAND_RAIL_TYPE,
                                             @Part("wearing_coat_type") String WEARING_COAT_TYPE,
                                             @Part("pier_foundation") String PIER_FOUNDATION,
                                             @Part("abutment_foundation") String ABUTMENT_FOUNDATION,
                                             @Part("mfl") String MFL,
                                             @Part("approach_type") String APPROACH_TYPE,
                                             @Part("floor_protection") String FLOOR_PROTECTION,
                                             @Part("floor_protection_type") String FLOOR_PROTECTION_TYPE,
                                             @Part("bridge_angle") String BRIDGE_ANGLE,
                                             @Part("bank_protection_type") String BANK_PROTECTION_TYPE,
                                             @Part("design_discharge") String DESIGN_DISCHARGE,
                                             @Part("foundation_cracks_ul") String FOUNDATION_CRACKS_UL,
                                             @Part("foundation_cracks_ur") String FOUNDATION_CRACKS_UR,
                                             @Part("foundation_cracks_dl") String FOUNDATION_CRACKS_DL,
                                             @Part("foundation_cracks_dr") String FOUNDATION_CRACKS_DR,
                                             @Part("foundation_settlement_ul") String FOUNDATION_SETTLEMENT_UL,
                                             @Part("foundation_settlement_ur") String FOUNDATION_SETTLEMENT_UR,
                                             @Part("foundation_settlement_dl") String FOUNDATION_SETTLEMENT_DL,
                                             @Part("foundation_settlement_dr") String FOUNDATION_SETTLEMENT_DR,
                                             @Part("foundation_spality_ul") String FOUNDATION_SPALITY_UL,
                                             @Part("foundation_spality_ur") String FOUNDATION_SPALITY_UR,
                                             @Part("foundation_spality_dl") String FOUNDATION_SPALITY_DL,
                                             @Part("foundation_spality_dr") String FOUNDATION_SPALITY_DR,
                                             @Part("foundation_scour_piers") String FOUNDATION_SCOUR_PIERS,
                                             @Part("foundation_spalled_piers") String FOUNDATION_SPALLED_PIERS,
                                             @Part("foundation_cracked_piers") String FOUNDATION_CRACKED_PIERS,
                                             @Part("sbs_cracks_ul") String SBS_CRACKS_UL,
                                             @Part("sbs_cracks_ur") String SBS_CRACKS_UR,
                                             @Part("sbs_cracks_dl") String SBS_CRACKS_DL,
                                             @Part("sbs_cracks_dr") String SBS_CRACKS_DR,
                                             @Part("sbs_vegetation_ul") String SBS_VEGETATION_UL,
                                             @Part("sbs_vegetation_ur") String SBS_VEGETATION_UR,
                                             @Part("sbs_vegetation_dl") String SBS_VEGETATION_DL,
                                             @Part("sbs_vegetation_dr") String SBS_VEGETATION_DR,
                                             @Part("sbs_tilting_ul") String SBS_TILTING_UL,
                                             @Part("sbs_tilting_ur") String SBS_TILTING_UR,
                                             @Part("sbs_tilting_dl") String SBS_TILTING_DL,
                                             @Part("sbs_tilting_dr") String SBS_TILTING_DR,
                                             @Part("sbs_spality_ul") String SBS_SPALITY_UL,
                                             @Part("sbs_spality_ur") String SBS_SPALITY_UR,
                                             @Part("sbs_spality_dl") String SBS_SPALITY_DL,
                                             @Part("sbs_spality_dr") String SBS_SPALITY_DR,
                                             @Part("sbs_cracked_piers") String SBS_CRACKED_PIERS,
                                             @Part("sbs_vegetation_piers") String SBS_VEGETATION_PIERS,
                                             @Part("sbs_tilting_piers") String SBS_TILTING_PIERS,
                                             @Part("sbs_spaling_piers") String SBS_SPALING_PIERS,
                                             @Part("sbs_concreate_cracked") String SPS_CONCREATE_CRACKED,
                                             @Part("sbs_concreate_leached") String SPS_CONCREATE_LEACHED,
                                             @Part("sbs_concreate_salliyo") String SPS_CONCREATE_SALLIYO,
                                             @Part("sbs_concreate_spality") String SPS_CONCREATE_SPALITY,
                                             @Part("sbs_steel_corrosion") String SPS_STEEL_CORROSION,
                                             @Part("sbs_steel_buckled") String SPS_STEEL_BUCKLED,
                                             @Part("sbs_arch_spalled") String SPS_ARCH_SPALLED,
                                             @Part("sbs_arch_vegetation") String SPS_ARCH_VEGETATION,
                                             @Part("sbs_arch_scaled") String SPS_ARCH_SCALED,
                                             @Part("handrails_broken") String HANDRAILS_BROKEN,
                                             @Part("handrails_corroded") String HANDRAILS_CORRODED,
                                             @Part("handrails_spalling") String HANDRAILS_SPALLING,
                                             @Part("footpath_broken") String FOOTPATH_BROKEN,
                                             @Part("footpath_disintegration") String FOOTPATH_DISINTEGRATION,
                                             @Part("wearing_coat_cracks") String WEARINGCOAT_CRACKS,
                                             @Part("wearing_coat_potholes") String WEARINGCOAT_POTHOLES,
                                             @Part("wearing_coat_ravelled") String WEARINGCOAT_RAVELLED,
                                             @Part("wearing_coat_failed_joints") String WEARINGCOAT_FAILED_JOINTS,
                                             @Part("wearing_coat_poor_drainage") String WEARINGCOAT_POOR_DRAINAGE,
                                             @Part("bearings_steel_rusted") String BEARINGS_STEEL_RUSTED,
                                             @Part("bearings_steel_tilted") String BEARINGS_STEEL_TILTED,
                                             @Part("bearings_elasto_flattering") String BEARINGS_ELASTO_FLATTERING,
                                             @Part("bearings_elasto_cracking") String BEARINGS_ELASTO_CRACKING,
                                             @Part("expansion_joints_wornout") String EXPANSION_JOINTS_WORNOUT,
                                             @Part("expansion_joints_bleed") String EXPANSION_JOINTS_BLEED,
                                             @Part("expansion_joints_cracked") String EXPANSION_JOINTS_CRACKED,
                                             @Part("vent_waterway_silted") String VENT_WATERWAY_SILTED,
                                             @Part("vent_waterway_scoured") String VENT_WATERWAY_SCOURED,
                                             @Part("bed_level") String BED_LEVEL,
                                             @Part("bed_slope") String BED_SLOPE,
                                             @Part("closed_date") String CLOSED_DATE,
                                             @Part("work_flow_status") String WORKFLOW_STATUS,
                                             @Part("session_id") String SESSION_ID,
                                             @Part("survey_date") String SURVEY_DATE,
                                             @Part("type_of_substructure") String TYPE_OF_SUBSTRUCTURE,
                                             @Part("shape_of_pier") String SHAPE_OF_PIER,
                                             @Part("bridge_desc") String BRIDGE_DESC,
                                             @Part("latitude") String LATITUDE,
                                             @Part("longitude") String LONGITUDE
    );


    @Multipart
    @POST("webservice/user/editBridge")
    Call<EditBridgeResponse> editBridgeResponse(@Part("bridge_key_id") String BRIDGE_KEY_ID,
                                                @Part("circle_key_id") String CIRCLE_KEY_ID,
                                             @Part("division_id") String DIVISION_ID,
                                             @Part("sub_division_id") String SUB_DIVISION_ID,
                                             @Part("road_id") String ROAD_ID,
                                             @Part("link_id") String LINK_ID,
                                             @Part("user_id") String USER_ID,
                                             @Part("location") String LOCATION,
                                             @Part("start_chainage") String START_CHAINAGE,
                                             @Part("bridge_id") String BRIDGE_ID,
                                             @Part("bridge_name") String BRIDGE_NAME,
                                             @Part("bridge_no") String BRIDGE_NO,
                                             @Part("year_constuct") String YEAR_CONSTUCT,
                                             @Part("construction_cost") String CONSTRUCTION_COST,
                                             @Part MultipartBody.Part attach_photo,
                                             @Part MultipartBody.Part attach_document,
                                             @Part("bridge_type") String BRIDGE_TYPE,
                                             @Part("bridge_length") String BRIDGE_LENGTH,
                                             @Part("loading") String LOADING,
                                             @Part("partial_widening") String PARTIAL_WIDENING,
                                             @Part("linear_water_way") String LINEAR_WATER_WAY,
                                             @Part("close_to_dam") String CLOSE_TO_DAM,
                                             @Part("bridge_width") String BRIDGE_WIDTH,
                                             @Part("river_name") String RIVER_NAME,
                                             @Part("flow_of_river") String FLOW_OF_RIVER,
                                             @Part("slab_design") String SLAB_DESIGN,
                                             @Part("ownership_of_river") String OWNERSHIP_OF_RIVER,
                                             @Part("no_of_lanes") String NO_OF_LANES,
                                             @Part("no_of_spans") String NO_OF_SPANS,
                                             @Part("max_span_width") String MAX_SPAN_WIDTH,
                                             @Part("vertical_clearance") String VERTICAL_CLEARANCE,
                                             @Part("left_foot_path") String LEFT_FOOT_PATH,
                                             @Part("right_foot_path") String RIGHT_FOOT_PATH,
                                             @Part("super_structure_type") String SUPER_STRUCTURE_TYPE,
                                             @Part("slab_thickness") String SLAB_THICKNESS,
                                             @Part("bearing_type") String BEARING_TYPE,
                                             @Part("parapet_hand_rail_type") String PARAPET_HAND_RAIL_TYPE,
                                             @Part("wearing_coat_type") String WEARING_COAT_TYPE,
                                             @Part("pier_foundation") String PIER_FOUNDATION,
                                             @Part("abutment_foundation") String ABUTMENT_FOUNDATION,
                                             @Part("mfl") String MFL,
                                             @Part("approach_type") String APPROACH_TYPE,
                                             @Part("floor_protection") String FLOOR_PROTECTION,
                                             @Part("floor_protection_type") String FLOOR_PROTECTION_TYPE,
                                             @Part("bridge_angle") String BRIDGE_ANGLE,
                                             @Part("bank_protection_type") String BANK_PROTECTION_TYPE,
                                             @Part("design_discharge") String DESIGN_DISCHARGE,
                                             @Part("foundation_cracks_ul") String FOUNDATION_CRACKS_UL,
                                             @Part("foundation_cracks_ur") String FOUNDATION_CRACKS_UR,
                                             @Part("foundation_cracks_dl") String FOUNDATION_CRACKS_DL,
                                             @Part("foundation_cracks_dr") String FOUNDATION_CRACKS_DR,
                                             @Part("foundation_settlement_ul") String FOUNDATION_SETTLEMENT_UL,
                                             @Part("foundation_settlement_ur") String FOUNDATION_SETTLEMENT_UR,
                                             @Part("foundation_settlement_dl") String FOUNDATION_SETTLEMENT_DL,
                                             @Part("foundation_settlement_dr") String FOUNDATION_SETTLEMENT_DR,
                                             @Part("foundation_spality_ul") String FOUNDATION_SPALITY_UL,
                                             @Part("foundation_spality_ur") String FOUNDATION_SPALITY_UR,
                                             @Part("foundation_spality_dl") String FOUNDATION_SPALITY_DL,
                                             @Part("foundation_spality_dr") String FOUNDATION_SPALITY_DR,
                                             @Part("foundation_scour_piers") String FOUNDATION_SCOUR_PIERS,
                                             @Part("foundation_spalled_piers") String FOUNDATION_SPALLED_PIERS,
                                             @Part("foundation_cracked_piers") String FOUNDATION_CRACKED_PIERS,
                                             @Part("sbs_cracks_ul") String SBS_CRACKS_UL,
                                             @Part("sbs_cracks_ur") String SBS_CRACKS_UR,
                                             @Part("sbs_cracks_dl") String SBS_CRACKS_DL,
                                             @Part("sbs_cracks_dr") String SBS_CRACKS_DR,
                                             @Part("sbs_vegetation_ul") String SBS_VEGETATION_UL,
                                             @Part("sbs_vegetation_ur") String SBS_VEGETATION_UR,
                                             @Part("sbs_vegetation_dl") String SBS_VEGETATION_DL,
                                             @Part("sbs_vegetation_dr") String SBS_VEGETATION_DR,
                                             @Part("sbs_tilting_ul") String SBS_TILTING_UL,
                                             @Part("sbs_tilting_ur") String SBS_TILTING_UR,
                                             @Part("sbs_tilting_dl") String SBS_TILTING_DL,
                                             @Part("sbs_tilting_dr") String SBS_TILTING_DR,
                                             @Part("sbs_spality_ul") String SBS_SPALITY_UL,
                                             @Part("sbs_spality_ur") String SBS_SPALITY_UR,
                                             @Part("sbs_spality_dl") String SBS_SPALITY_DL,
                                             @Part("sbs_spality_dr") String SBS_SPALITY_DR,
                                             @Part("sbs_cracked_piers") String SBS_CRACKED_PIERS,
                                             @Part("sbs_vegetation_piers") String SBS_VEGETATION_PIERS,
                                             @Part("sbs_tilting_piers") String SBS_TILTING_PIERS,
                                             @Part("sbs_spaling_piers") String SBS_SPALING_PIERS,
                                             @Part("sbs_concreate_cracked") String SPS_CONCREATE_CRACKED,
                                             @Part("sbs_concreate_leached") String SPS_CONCREATE_LEACHED,
                                             @Part("sbs_concreate_salliyo") String SPS_CONCREATE_SALLIYO,
                                             @Part("sbs_concreate_spality") String SPS_CONCREATE_SPALITY,
                                             @Part("sbs_steel_corrosion") String SPS_STEEL_CORROSION,
                                             @Part("sbs_steel_buckled") String SPS_STEEL_BUCKLED,
                                             @Part("sbs_arch_spalled") String SPS_ARCH_SPALLED,
                                             @Part("sbs_arch_vegetation") String SPS_ARCH_VEGETATION,
                                             @Part("sbs_arch_scaled") String SPS_ARCH_SCALED,
                                             @Part("handrails_broken") String HANDRAILS_BROKEN,
                                             @Part("handrails_corroded") String HANDRAILS_CORRODED,
                                             @Part("handrails_spalling") String HANDRAILS_SPALLING,
                                             @Part("footpath_broken") String FOOTPATH_BROKEN,
                                             @Part("footpath_disintegration") String FOOTPATH_DISINTEGRATION,
                                             @Part("wearing_coat_cracks") String WEARINGCOAT_CRACKS,
                                             @Part("wearing_coat_potholes") String WEARINGCOAT_POTHOLES,
                                             @Part("wearing_coat_ravelled") String WEARINGCOAT_RAVELLED,
                                             @Part("wearing_coat_failed_joints") String WEARINGCOAT_FAILED_JOINTS,
                                             @Part("wearing_coat_poor_drainage") String WEARINGCOAT_POOR_DRAINAGE,
                                             @Part("bearings_steel_rusted") String BEARINGS_STEEL_RUSTED,
                                             @Part("bearings_steel_tilted") String BEARINGS_STEEL_TILTED,
                                             @Part("bearings_elasto_flattering") String BEARINGS_ELASTO_FLATTERING,
                                             @Part("bearings_elasto_cracking") String BEARINGS_ELASTO_CRACKING,
                                             @Part("expansion_joints_wornout") String EXPANSION_JOINTS_WORNOUT,
                                             @Part("expansion_joints_bleed") String EXPANSION_JOINTS_BLEED,
                                             @Part("expansion_joints_cracked") String EXPANSION_JOINTS_CRACKED,
                                             @Part("vent_waterway_silted") String VENT_WATERWAY_SILTED,
                                             @Part("vent_waterway_scoured") String VENT_WATERWAY_SCOURED,
                                             @Part("bed_level") String BED_LEVEL,
                                             @Part("bed_slope") String BED_SLOPE,
                                             @Part("closed_date") String CLOSED_DATE,
                                             @Part("work_flow_status") String WORKFLOW_STATUS,
                                             @Part("session_id") String SESSION_ID,
                                             @Part("survey_date") String SURVEY_DATE,
                                             @Part("type_of_substructure") String TYPE_OF_SUBSTRUCTURE,
                                             @Part("shape_of_pier") String SHAPE_OF_PIER,
                                             @Part("bridge_desc") String BRIDGE_DESC,
                                             @Part("latitude") String LATITUDE,
                                             @Part("longitude") String LONGITUDE
    );


    @Headers("Content-Type: application/json")
    @POST("webservice/user/getCulvertsDetails")
    Call<CulvertResponse> getCulvertDetails(
            @Body String LinkId
    );

    @Multipart
    @POST("webservice/user/addCulvert")
    Call<CulvertAddResponse> addCulvetDetails(@Part("culvert_desc") String culvert_desc,
                                              @Part("chainage") String chainage,
                                              @Part("culvert_no") String culvert_no,
                                              @Part("culvert_id") String culvert_id,
                                              @Part("year_constuct") String year_constuct,
                                              @Part("circle_key_id") String circle_key_id,
                                              @Part("division_id") String division_id,
                                              @Part("sub_division_id") String sub_division_id,
                                              @Part("road_id") String road_id,
                                              @Part("link_id") String link_id,
                                              @Part("user_id") String user_id,
                                              @Part("no_of_rows") String no_of_rows,
                                              @Part("pipes_dia_meter") String pipes_dia_meter,
                                              @Part("no_of_span") String no_of_span,
                                              @Part("vent_width") String vent_width,
                                              @Part("vent_height") String vent_height,
                                              @Part("culvert_length") String culvert_length,
                                              @Part("culvert_condition") String culvert_condition,
                                              @Part("closed_date") String closed_date,
                                              @Part("work_flow_status") String work_flow_status,
                                              @Part("session_id") String session_id,
                                              @Part("latitude") String latitude,
                                              @Part("longitude") String longitude,
                                              @Part("culvert_type") String culvert_type,
                                              @Part MultipartBody.Part attach_photo
    );



    @Multipart
    @POST("webservice/user/editCulvert")
    Call<CulvetEditResponse> EditCulvetDetails(@Part("culvert_key_id") String culvert_key_id,
                                               @Part("culvert_desc") String culvert_desc,
                                               @Part("chainage") String chainage,
                                               @Part("culvert_no") String culvert_no,
                                               @Part("culvert_id") String culvert_id,
                                               @Part("year_constuct") String year_constuct,
                                               @Part("circle_key_id") String circle_key_id,
                                               @Part("division_id") String division_id,
                                               @Part("sub_division_id") String sub_division_id,
                                               @Part("road_id") String road_id,
                                               @Part("link_id") String link_id,
                                               @Part("user_id") String user_id,
                                               @Part("no_of_rows") String no_of_rows,
                                               @Part("pipes_dia_meter") String pipes_dia_meter,
                                               @Part("no_of_span") String no_of_span,
                                               @Part("vent_width") String vent_width,
                                               @Part("vent_height") String vent_height,
                                               @Part("culvert_length") String culvert_length,
                                               @Part("culvert_condition") String culvert_condition,
                                               @Part("closed_date") String closed_date,
                                               @Part("work_flow_status") String work_flow_status,
                                               @Part("session_id") String session_id,
                                               @Part("latitude") String latitude,
                                               @Part("longitude") String longitude,
                                               @Part("culvert_type") String culvert_type,
                                               @Part MultipartBody.Part attach_photo
    );


}
