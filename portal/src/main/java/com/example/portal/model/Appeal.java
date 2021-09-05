package com.example.portal.model;

import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.math.BigInteger;

@Data
public class Appeal {

    private Long id;
    private String uid;
    private Date createTime;
    private Date updateTime;
    private String createUser;


    private Date request_time;
    private String last_name;
    private String first_name;
    private String patronimic;
    private String organization;
    private String ogrn;
    private Integer timezone;
    private String representative_fio;
    private String outgoing_document_number;
    private Date outgoing_document_date;
    private String delivery_email;
    private String delivery_zip;
    private String delivery_city;
    private String delivery_street;
    private String delivery_house;
    private String delivery_building;
    private String delivery_apartment;
    private String delivery_phone_number;
    private UUID delivery_world_region_uuid;
    private UUID delivery_subregion_uuid;

    private String message;
    private Boolean confirmation_status;
    private String request_number;
    private String status;

    /*
    private NsiUnitEntity nsi_unit_uuid;
    @ManyToOne(targetEntity = RequestTypeEntity.class)
    @Type(type = "pg-uuid")
    @JoinColumn(name = "REQUEST_TYPE_UUID", nullable = false, referencedColumnName = "uid")
    private RequestTypeEntity request_type_uuid;
    @ManyToOne(targetEntity = RequestKindEntity.class)
    @Type(type = "pg-uuid")
    @JoinColumn(name = "REQUEST_KIND_UUID", nullable = false, referencedColumnName = "uid")
    private RequestKindEntity request_kind_uuid;
    @ManyToOne(targetEntity = RequestMessageSubject.class)
    @Type(type = "pg-uuid")
    @JoinColumn(name = "REQUEST_MESSAGE_SUBJECT_UID", referencedColumnName = "uid")
    private RequestMessageSubject request_message_subject_uid;
    */


    private String business_line_procuracy;
    private String business_line_procuracy_id;
    private String business_line_procuracy_date;
    private UUID business_line_region_uid;
    private String business_line_region_living;
    private String business_line_address;
    private String extr_type;
    private String extr_source;
    private String extr_link;
    private String extr_flag;
    private String extr_paywall;
    private Boolean isFirstRequest;
    private BigInteger attachmentAggrChecksum;
    private String attachmentFileNames;
    private UUID request_action_uuid;



    private Set<Application2RegionLink> regions;
    private Set<RequestAttachment> files;

    private String ip;

}
