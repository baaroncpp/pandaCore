--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: panda_core; Type: SCHEMA; Schema: -; Owner: boss
--

CREATE SCHEMA panda_core;


ALTER SCHEMA panda_core OWNER TO boss;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: email; Type: DOMAIN; Schema: public; Owner: boss
--

CREATE DOMAIN public.email AS character varying(50)
	CONSTRAINT email_check CHECK (((VALUE)::text ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'::text));


ALTER DOMAIN public.email OWNER TO boss;

--
-- Name: DOMAIN email; Type: COMMENT; Schema: public; Owner: boss
--

COMMENT ON DOMAIN public.email IS 'Custom email datatype with regex to match email';


--
-- Name: updated_on_column_updater(); Type: FUNCTION; Schema: panda_core; Owner: boss
--

CREATE FUNCTION panda_core.updated_on_column_updater() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.updatedon = now();
    RETURN NEW;   
END;
$$;


ALTER FUNCTION panda_core.updated_on_column_updater() OWNER TO boss;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_config; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_config (
    id integer NOT NULL,
    name character varying NOT NULL,
    value character varying NOT NULL,
    financialyear integer DEFAULT 0 NOT NULL,
    description text,
    createdon timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE panda_core.t_config OWNER TO boss;

--
-- Name: finance_config_id_seq; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.finance_config_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.finance_config_id_seq OWNER TO boss;

--
-- Name: finance_config_id_seq; Type: SEQUENCE OWNED BY; Schema: panda_core; Owner: boss
--

ALTER SEQUENCE panda_core.finance_config_id_seq OWNED BY panda_core.t_config.id;


--
-- Name: s_county; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_county
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_county OWNER TO boss;

--
-- Name: s_district; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_district
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_district OWNER TO boss;

--
-- Name: s_lease_offer; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_lease_offer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_lease_offer OWNER TO boss;

--
-- Name: s_parishes; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_parishes
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_parishes OWNER TO boss;

--
-- Name: s_payment_channel; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_payment_channel
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_payment_channel OWNER TO boss;

--
-- Name: s_subcounty; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_subcounty
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_subcounty OWNER TO boss;

--
-- Name: s_villages; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.s_villages
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.s_villages OWNER TO boss;

--
-- Name: t_agent_meta; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_agent_meta (
    userid character varying(36) NOT NULL,
    profilepath text,
    contractdocpath text,
    villageid integer NOT NULL,
    address character varying,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    agentcommissionrate numeric DEFAULT 0 NOT NULL,
    isdeactivated boolean DEFAULT false NOT NULL,
    deactivatedon date,
    isactive boolean DEFAULT false NOT NULL,
    activatedon date,
    shoplong numeric,
    companyname character varying(200) NOT NULL,
    postaladdress character varying(100),
    tinnumber character varying(50) NOT NULL,
    coipath text,
    agenttype character varying(10) DEFAULT 'DIRECT'::character varying NOT NULL,
    shoplat numeric,
    idtype character varying DEFAULT 'NATIONALID'::character varying NOT NULL,
    idnumber character varying(50) NOT NULL,
    CONSTRAINT t_agent_meta_agent_type_check CHECK (((agenttype)::text = ANY ((ARRAY['DIRECT'::character varying, 'INDIRECT'::character varying])::text[]))),
    CONSTRAINT t_agent_meta_check CHECK (((idtype)::text = ANY (ARRAY['NATIONALID'::text, 'PASSPORT'::text, 'DRIVINGPERMIT'::text])))
);


ALTER TABLE panda_core.t_agent_meta OWNER TO boss;

--
-- Name: t_agents_meta_id_seq; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.t_agents_meta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.t_agents_meta_id_seq OWNER TO boss;

--
-- Name: t_agents_meta_id_seq; Type: SEQUENCE OWNED BY; Schema: panda_core; Owner: boss
--

ALTER SEQUENCE panda_core.t_agents_meta_id_seq OWNED BY panda_core.t_agent_meta.userid;


--
-- Name: t_approval; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_approval (
    id character varying(36) NOT NULL,
    approvaltype smallint NOT NULL,
    approver character varying(36),
    approverrole character varying(10) NOT NULL,
    status smallint DEFAULT 1 NOT NULL,
    externalreference character varying(36),
    description text,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    approvalorder smallint NOT NULL,
    CONSTRAINT t_approvals_status_check CHECK ((status = ANY (ARRAY[1, 2, 3])))
);


ALTER TABLE panda_core.t_approval OWNER TO boss;

--
-- Name: t_approval_review; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_approval_review (
    approvalid character varying(36) NOT NULL,
    review text NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    id character varying(36) NOT NULL
);


ALTER TABLE panda_core.t_approval_review OWNER TO boss;

--
-- Name: t_approval_type; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_approval_type (
    id smallint NOT NULL,
    name character varying(30) NOT NULL
);


ALTER TABLE panda_core.t_approval_type OWNER TO boss;

--
-- Name: t_approver; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_approver (
    id character varying(36) NOT NULL,
    userid character varying(36) NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    itemapproved character varying(36) NOT NULL,
    itemid character varying(36) NOT NULL
);


ALTER TABLE panda_core.t_approver OWNER TO boss;

--
-- Name: t_capex; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_capex (
    id character varying(36) NOT NULL,
    employeeid character varying(36) NOT NULL,
    amount numeric NOT NULL,
    expensetype smallint NOT NULL,
    description text,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    approvedon timestamp without time zone
);


ALTER TABLE panda_core.t_capex OWNER TO boss;

--
-- Name: t_capex_type; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_capex_type (
    id smallint NOT NULL,
    name character varying(50)
);


ALTER TABLE panda_core.t_capex_type OWNER TO boss;

--
-- Name: t_channel; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_channel (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE panda_core.t_channel OWNER TO boss;

--
-- Name: t_county; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_county (
    id integer DEFAULT nextval('panda_core.s_county'::regclass) NOT NULL,
    name character varying(50),
    districtid integer NOT NULL,
    inreview boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_county OWNER TO boss;

--
-- Name: t_customer_meta; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_customer_meta (
    userid character varying(36) NOT NULL,
    secondaryphone character varying(36),
    secondaryemail public.email,
    consentformpath text,
    idcopypath text,
    profilephotopath text,
    address character varying(60),
    villageid integer,
    homelat numeric,
    homelong numeric,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    idtype character varying DEFAULT 'NATIONALID'::character varying NOT NULL,
    idnumber character varying(50) NOT NULL,
    CONSTRAINT t_customer_meta_idtype_check CHECK (((idtype)::text = ANY (ARRAY['NATIONALID'::text, 'PASSPORT'::text, 'DRIVINGPERMIT'::text])))
);


ALTER TABLE panda_core.t_customer_meta OWNER TO boss;

--
-- Name: TABLE t_customer_meta; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_customer_meta IS 'Additional information that may be used for identifying the customer';


--
-- Name: COLUMN t_customer_meta.idcopypath; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_customer_meta.idcopypath IS 'Path to the digital copy of identification scanned/photoed while registering customer';


--
-- Name: COLUMN t_customer_meta.profilephotopath; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_customer_meta.profilephotopath IS 'Path to customer''s photo (passport size) taken during registration';


--
-- Name: COLUMN t_customer_meta.address; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_customer_meta.address IS 'Address of customer, can be plot number and road or just closest road/trading centre';


--
-- Name: COLUMN t_customer_meta.villageid; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_customer_meta.villageid IS 'Id of village to which customer belongs, village links down to district and region';


--
-- Name: t_district; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_district (
    id integer DEFAULT nextval('panda_core.s_district'::regclass) NOT NULL,
    name character varying(50),
    regionid integer NOT NULL,
    inreview boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_district OWNER TO boss;

--
-- Name: t_employee_meta; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_employee_meta (
    userid character varying(36) NOT NULL,
    isterminated boolean DEFAULT false NOT NULL,
    mobile character varying(20),
    profilepath text,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    isapproved boolean DEFAULT false,
    terminatedon date,
    approvedon date,
    village integer NOT NULL,
    tinnumber character varying(30),
    idtype character varying DEFAULT 'NATIONALID'::character varying NOT NULL,
    idnumber character varying(50) NOT NULL,
    CONSTRAINT t_employee_meta_idtype_check CHECK (((idtype)::text = ANY (ARRAY['NATIONALID'::text, 'PASSPORT'::text, 'DRIVINGPERMIT'::text])))
);


ALTER TABLE panda_core.t_employee_meta OWNER TO boss;

--
-- Name: TABLE t_employee_meta; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_employee_meta IS 'Details of employees employed by Panda core, this is mainly to help with system authentication, i believe another internal platofrm is more preferred
preferred for this thus not alot of meta-data is available';


--
-- Name: COLUMN t_employee_meta.userid; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_employee_meta.userid IS 'Id of the employee in table';


--
-- Name: COLUMN t_employee_meta.isterminated; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_employee_meta.isterminated IS 'Whether employee is still with the company';


--
-- Name: COLUMN t_employee_meta.profilepath; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_employee_meta.profilepath IS 'Path to storage location of profile photo of employee';


--
-- Name: t_equip_category; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_equip_category (
    id character varying NOT NULL,
    name character varying NOT NULL,
    description text,
    createon timestamp without time zone DEFAULT now() NOT NULL,
    isactive boolean DEFAULT true
);


ALTER TABLE panda_core.t_equip_category OWNER TO boss;

--
-- Name: TABLE t_equip_category; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_equip_category IS 'The category to which the equipment falls, it might be equipment for a radio system, lighting etc';


--
-- Name: COLUMN t_equip_category.id; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equip_category.id IS 'Auto generated Id that for equipment category';


--
-- Name: COLUMN t_equip_category.name; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equip_category.name IS 'Name of the category of equipment that Panda Solar deals in';


--
-- Name: t_equipment; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_equipment (
    id character varying(36) NOT NULL,
    name character varying NOT NULL,
    model character varying NOT NULL,
    dateofmanufacture timestamp without time zone,
    categoryid character varying NOT NULL,
    available boolean DEFAULT true NOT NULL,
    description text NOT NULL,
    serial character varying NOT NULL,
    quantity numeric DEFAULT 1 NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    thumbnail text
);


ALTER TABLE panda_core.t_equipment OWNER TO boss;

--
-- Name: TABLE t_equipment; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_equipment IS 'Equipment is the list of equipment that is held by Panda Solar. Several equipment are combined together to form a product for Panda Solar';


--
-- Name: COLUMN t_equipment.id; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equipment.id IS 'ID for equipment in equipment table';


--
-- Name: COLUMN t_equipment.name; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equipment.name IS 'Name of the equipment that is being recored';


--
-- Name: COLUMN t_equipment.model; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equipment.model IS 'Model of the equipment that is being recorded in the table';


--
-- Name: COLUMN t_equipment.dateofmanufacture; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equipment.dateofmanufacture IS 'Date on which this specific device was manufactured';


--
-- Name: COLUMN t_equipment.categoryid; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_equipment.categoryid IS 'Reference to category table with categories to which the equipment belongs. Eg Lighting, music, telecom etc';


--
-- Name: t_installation; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_installation (
    id character varying(36) NOT NULL,
    saleagentid character varying(36) NOT NULL,
    customerid character varying(36) NOT NULL,
    coordinates numeric[] NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    endtime timestamp without time zone NOT NULL
);


ALTER TABLE panda_core.t_installation OWNER TO boss;

--
-- Name: t_lease; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_lease (
    id character varying(36) NOT NULL,
    customerid character varying(36) NOT NULL,
    leaseofferid integer NOT NULL,
    initialdeposit numeric DEFAULT 0 NOT NULL,
    couponcode character varying DEFAULT 0,
    dateinstalled timestamp without time zone,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    saleagentid character varying(36),
    dailypayment numeric NOT NULL,
    totalleaseperiod integer NOT NULL,
    expectedfinishdate date,
    totalleasevalue numeric NOT NULL,
    iscompleted boolean DEFAULT false NOT NULL,
    isactivated boolean DEFAULT false NOT NULL,
    paymentcompletedon timestamp without time zone,
    CONSTRAINT t_lease_initialdeposit_check CHECK ((initialdeposit >= (0)::numeric)),
    CONSTRAINT t_lease_initialdeposit_check1 CHECK ((initialdeposit >= (0)::numeric))
);


ALTER TABLE panda_core.t_lease OWNER TO boss;

--
-- Name: t_lease_offer; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_lease_offer (
    id integer DEFAULT nextval('panda_core.s_lease_offer'::regclass) NOT NULL,
    code character varying(10) NOT NULL,
    productid character varying NOT NULL,
    leaseperiod smallint DEFAULT 360 NOT NULL,
    percentlease smallint DEFAULT 10 NOT NULL,
    recurrentpaymentamount numeric NOT NULL,
    isactive boolean DEFAULT true NOT NULL,
    intialdeposit numeric NOT NULL,
    name character varying(40) NOT NULL,
    description text NOT NULL,
    createdon timestamp without time zone DEFAULT now(),
    CONSTRAINT t_lease_offer_leaseperiod_check CHECK ((leaseperiod >= 360)),
    CONSTRAINT t_lease_offer_percentlease_check CHECK ((percentlease > 0)),
    CONSTRAINT t_lease_offer_recurrentpaymentamount_check CHECK ((recurrentpaymentamount > (0)::numeric))
);


ALTER TABLE panda_core.t_lease_offer OWNER TO boss;

--
-- Name: t_lease_payment; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_lease_payment (
    id character varying(36) NOT NULL,
    leaseid character varying(36) NOT NULL,
    amount numeric NOT NULL,
    name character varying(100),
    msisdn character varying(20),
    channel smallint NOT NULL,
    status smallint DEFAULT 2 NOT NULL,
    transactionid character varying(20) NOT NULL,
    channeltransactionid character varying(36),
    channelstatuscode character varying(5),
    channelmessage text,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT t_lease_payments_amount_check CHECK ((amount > (0)::numeric)),
    CONSTRAINT t_lease_payments_channel_check CHECK ((channel = ANY (ARRAY[1, 2]))),
    CONSTRAINT t_lease_payments_status_check CHECK ((status = ANY (ARRAY[1, 2, 3])))
);


ALTER TABLE panda_core.t_lease_payment OWNER TO boss;

--
-- Name: t_lease_payment_extra; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_lease_payment_extra (
    id character varying(36) NOT NULL,
    leaseid character varying(36) NOT NULL,
    amount numeric NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    isrefunded boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_lease_payment_extra OWNER TO boss;

--
-- Name: t_opex; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_opex (
    id character varying(36) NOT NULL,
    employeeid character varying(36) NOT NULL,
    amount numeric NOT NULL,
    expensetype smallint NOT NULL,
    description text,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    approvedon timestamp without time zone
);


ALTER TABLE panda_core.t_opex OWNER TO boss;

--
-- Name: t_opex_type; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_opex_type (
    id smallint NOT NULL,
    name character varying(50)
);


ALTER TABLE panda_core.t_opex_type OWNER TO boss;

--
-- Name: t_parish; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_parish (
    id integer DEFAULT nextval('panda_core.s_parishes'::regclass) NOT NULL,
    name character varying(50),
    subcountyid integer NOT NULL,
    inreview boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_parish OWNER TO boss;

--
-- Name: t_payment_channel; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_payment_channel (
    id smallint DEFAULT nextval('panda_core.s_payment_channel'::regclass) NOT NULL,
    channelname character varying(20) NOT NULL,
    channelcode character varying(5) NOT NULL,
    isenabled boolean DEFAULT true,
    description text NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE panda_core.t_payment_channel OWNER TO boss;

--
-- Name: t_product; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_product (
    id character varying(36) NOT NULL,
    name character varying NOT NULL,
    equipmentlist character varying[] DEFAULT ARRAY['Light Bulb'::text, 'Solar Pandel'::text, 'Solar Control Box'::text] NOT NULL,
    unitcostselling numeric NOT NULL,
    description text NOT NULL,
    thumbnail text DEFAULT 'http://'::text NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    isactive boolean DEFAULT true NOT NULL,
    isleasable boolean DEFAULT true NOT NULL,
    usestoken boolean DEFAULT true NOT NULL
);


ALTER TABLE panda_core.t_product OWNER TO boss;

--
-- Name: TABLE t_product; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_product IS 'The grouping of products that that are available at Panda Solar';


--
-- Name: COLUMN t_product.name; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_product.name IS 'The name of the product grouping as shown on the website';


--
-- Name: COLUMN t_product.equipmentlist; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_product.equipmentlist IS 'An array list of all the equipment that make up this product category';


--
-- Name: COLUMN t_product.unitcostselling; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_product.unitcostselling IS 'The unit cost of the product is customer is not buying through asset financing';


--
-- Name: COLUMN t_product.description; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_product.description IS 'The description of the products, this will most likely be a HTML page saved ready to be added to description';


--
-- Name: COLUMN t_product.thumbnail; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_product.thumbnail IS 'The thumbnail for the product to be displayed on the website';


--
-- Name: t_region_ug; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_region_ug (
    id integer NOT NULL,
    name character varying(10)
);


ALTER TABLE panda_core.t_region_ug OWNER TO boss;

--
-- Name: t_role; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_role (
    id smallint NOT NULL,
    name character varying(50) NOT NULL,
    description text
);


ALTER TABLE panda_core.t_role OWNER TO boss;

--
-- Name: TABLE t_role; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON TABLE panda_core.t_role IS 'Roles that are supported in the system. 5 roles currently supported, 
1- Installation engineer, 2- marketing, 3- sales agent, 4-finance, 5- manager';


--
-- Name: t_sale; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_sale (
    id character varying(36) NOT NULL,
    saletype character varying,
    amount numeric DEFAULT 0 NOT NULL,
    description text,
    scannedserial character varying(50),
    agentcommission numeric DEFAULT 0 NOT NULL,
    agentid character varying(36) NOT NULL,
    long_ numeric DEFAULT 0 NOT NULL,
    lat_ numeric DEFAULT 0 NOT NULL,
    salestatus smallint DEFAULT 0 NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    productid character varying(36),
    quantity integer NOT NULL,
    customerid character varying(36) NOT NULL,
    completedon timestamp without time zone,
    CONSTRAINT t_sales_salestatus_check CHECK ((salestatus = ANY (ARRAY[1, 2, 3]))),
    CONSTRAINT t_sales_saletype_check CHECK (((saletype)::text = ANY (ARRAY[('Lease'::character varying)::text, ('Direct'::character varying)::text])))
);


ALTER TABLE panda_core.t_sale OWNER TO boss;

--
-- Name: t_sale_rollback; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_sale_rollback (
    id character varying(36) NOT NULL,
    saleid character varying(36) NOT NULL,
    description text,
    createdon timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE panda_core.t_sale_rollback OWNER TO boss;

--
-- Name: t_subcounty; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_subcounty (
    id integer DEFAULT nextval('panda_core.s_subcounty'::regclass) NOT NULL,
    name character varying(50),
    countyid integer NOT NULL,
    inreview boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_subcounty OWNER TO boss;

--
-- Name: t_token; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_token (
    leasepaymentid character varying(36) NOT NULL,
    token character varying(256),
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    times integer NOT NULL,
    days integer NOT NULL,
    value numeric,
    type integer DEFAULT 1 NOT NULL,
    CONSTRAINT t_token_check CHECK (((type >= 0) AND (type <= 4)))
);


ALTER TABLE panda_core.t_token OWNER TO boss;

--
-- Name: t_token_type; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_token_type (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE panda_core.t_token_type OWNER TO boss;

--
-- Name: t_total_lease_payments; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_total_lease_payments (
    id character varying(36) NOT NULL,
    leaseid character varying(36),
    lastpaidamount numeric NOT NULL,
    totalamountpaid numeric NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    nextpaymentdate timestamp without time zone NOT NULL,
    totalamountowed numeric NOT NULL,
    residueamount numeric DEFAULT 0 NOT NULL,
    times integer DEFAULT 0 NOT NULL,
    updatedon timestamp without time zone
);


ALTER TABLE panda_core.t_total_lease_payments OWNER TO boss;

--
-- Name: COLUMN t_total_lease_payments.times; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_total_lease_payments.times IS 'Token load times';


--
-- Name: COLUMN t_total_lease_payments.updatedon; Type: COMMENT; Schema: panda_core; Owner: boss
--

COMMENT ON COLUMN panda_core.t_total_lease_payments.updatedon IS 'Date on which this column has been updated';


--
-- Name: t_user; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_user (
    id character varying(36) NOT NULL,
    username character varying(30) NOT NULL,
    password character varying(60) NOT NULL,
    isactive boolean DEFAULT false NOT NULL,
    passwordreseton timestamp without time zone,
    createdon timestamp without time zone DEFAULT now() NOT NULL,
    usertype character varying(30) NOT NULL,
    firstname character varying(20) NOT NULL,
    middlename character varying(20),
    lastname character varying(20) NOT NULL,
    email character varying(50) NOT NULL,
    primaryphone character varying(20) NOT NULL,
    companyemail character varying(20) NOT NULL,
    dateofbirth date,
    lastlogon date,
    updatedon timestamp without time zone,
    isapproved boolean DEFAULT false NOT NULL,
    title character varying DEFAULT 'MR'::character varying NOT NULL,
    sex character varying(6) DEFAULT 'MALE'::character varying NOT NULL,
    CONSTRAINT t_user_sex_check CHECK (((sex)::text = ANY (ARRAY['MALE'::text, 'FEMALE'::text]))),
    CONSTRAINT t_user_title_check CHECK (((title)::text = ANY (ARRAY['MR'::text, 'MISS'::text, 'MS'::text, 'DOCTOR'::text, 'HON'::text]))),
    CONSTRAINT usertype_chk CHECK (((usertype)::text = ANY (ARRAY[('EMPLOYEE'::character varying)::text, ('CUSTOMER'::character varying)::text, ('AGENT'::character varying)::text])))
);


ALTER TABLE panda_core.t_user OWNER TO boss;

--
-- Name: t_user_role; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_user_role (
    userid character varying(36) NOT NULL,
    roleid smallint NOT NULL,
    createdon timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE panda_core.t_user_role OWNER TO boss;

--
-- Name: t_users_id_seq; Type: SEQUENCE; Schema: panda_core; Owner: boss
--

CREATE SEQUENCE panda_core.t_users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE panda_core.t_users_id_seq OWNER TO boss;

--
-- Name: t_users_id_seq; Type: SEQUENCE OWNED BY; Schema: panda_core; Owner: boss
--

ALTER SEQUENCE panda_core.t_users_id_seq OWNED BY panda_core.t_user.id;


--
-- Name: t_village; Type: TABLE; Schema: panda_core; Owner: boss
--

CREATE TABLE panda_core.t_village (
    id integer DEFAULT nextval('panda_core.s_villages'::regclass) NOT NULL,
    name character varying(50),
    parishid integer NOT NULL,
    inreview boolean DEFAULT false NOT NULL
);


ALTER TABLE panda_core.t_village OWNER TO boss;

--
-- Name: v_customer_finance_info; Type: VIEW; Schema: panda_core; Owner: boss
--

CREATE VIEW panda_core.v_customer_finance_info AS
 SELECT t.id,
    t.firstname,
    t.lastname,
    t.middlename,
    p.scannedserial,
    k.dailypayment,
    k.totalleaseperiod
   FROM ((panda_core.t_user t
     LEFT JOIN panda_core.t_lease k ON (((t.id)::text = (k.customerid)::text)))
     LEFT JOIN panda_core.t_sale p ON (((k.customerid)::text = (p.customerid)::text)))
  WHERE ((t.isactive = true) AND (k.isactivated = true) AND (k.iscompleted = false) AND (p.salestatus = 2));


ALTER TABLE panda_core.v_customer_finance_info OWNER TO boss;

--
-- Name: v_login_user; Type: VIEW; Schema: panda_core; Owner: boss
--

CREATE VIEW panda_core.v_login_user AS
 SELECT u.id,
    u.username,
    u.email,
    u.password,
    u.isapproved,
    u.isactive,
    k.name AS rolename
   FROM ((panda_core.t_user u
     LEFT JOIN panda_core.t_user_role p ON (((p.userid)::text = (u.id)::text)))
     LEFT JOIN panda_core.t_role k ON ((p.roleid = k.id)));


ALTER TABLE panda_core.v_login_user OWNER TO boss;

--
-- Name: t_approver; Type: TABLE; Schema: public; Owner: boss
--

CREATE TABLE public.t_approver (
    id character varying(36) NOT NULL,
    userid character varying(36) NOT NULL,
    approvalservice character varying(10) NOT NULL,
    approvalorder integer DEFAULT 1 NOT NULL,
    isenabled boolean,
    createdon timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.t_approver OWNER TO boss;

--
-- Name: t_agent_meta userid; Type: DEFAULT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_agent_meta ALTER COLUMN userid SET DEFAULT nextval('panda_core.t_agents_meta_id_seq'::regclass);


--
-- Name: t_config id; Type: DEFAULT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_config ALTER COLUMN id SET DEFAULT nextval('panda_core.finance_config_id_seq'::regclass);


--
-- Name: t_user id; Type: DEFAULT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user ALTER COLUMN id SET DEFAULT nextval('panda_core.t_users_id_seq'::regclass);


--
-- Data for Name: t_agent_meta; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_agent_meta (userid, profilepath, contractdocpath, villageid, address, createdon, agentcommissionrate, isdeactivated, deactivatedon, isactive, activatedon, shoplong, companyname, postaladdress, tinnumber, coipath, agenttype, shoplat, idtype, idnumber) FROM stdin;
fc179a74c902420bba3d16dfef1522af	profile_484884884	contract_488949949949949	1	Kanjokya House, Ntinda	2019-04-27 19:46:13.449442	10	f	\N	t	\N	4.2999	Juvunia Investments	P.O Box 7442, Kampala	5899949949944	coi_499949949494	DIRECT	44.3488	NATIONALID	0
\.


--
-- Data for Name: t_approval; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_approval (id, approvaltype, approver, approverrole, status, externalreference, description, createdon, approvalorder) FROM stdin;
\.


--
-- Data for Name: t_approval_review; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_approval_review (approvalid, review, createdon, id) FROM stdin;
0337066d055a488fbb8f4353e53284be	Profile Picture is not clear	2019-04-28 20:27:07.961467	0
0337066d055a488fbb8f4353e53284be	Passport Photo Not Picture is not clear	2019-04-28 20:36:57.697252	31c294a232964858abae609b3c61cbbc
\.


--
-- Data for Name: t_approval_type; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_approval_type (id, name) FROM stdin;
1	Employee
2	Expense
3	Agent
4	Equipment Dispatch
\.


--
-- Data for Name: t_approver; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_approver (id, userid, createdon, itemapproved, itemid) FROM stdin;
6f752a8f29894c33a36dd6c0f0a0bbd0	fc179a74c902420bba3d16dfef1522af	2019-04-29 11:42:35.340283	user	0337066d055a488fbb8f4353e53284be
fccc37da48f44761ab5819a1e6e8b9b9	fc179a74c902420bba3d16dfef1522af	2019-04-29 11:46:11.822162	user	0337066d055a488fbb8f4353e53284be
edcd19c184704592ace9068158e08c74	fc179a74c902420bba3d16dfef1522af	2019-04-29 11:47:46.15406	user	0337066d055a488fbb8f4353e53284be
\.


--
-- Data for Name: t_capex; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_capex (id, employeeid, amount, expensetype, description, createdon, approvedon) FROM stdin;
\.


--
-- Data for Name: t_capex_type; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_capex_type (id, name) FROM stdin;
1	Infrastructure
2	Furniture
3	Equipment Purchase
\.


--
-- Data for Name: t_channel; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_channel (id, name) FROM stdin;
1	MTN
2	Airtel
\.


--
-- Data for Name: t_config; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_config (id, name, value, financialyear, description, createdon) FROM stdin;
1	EMPLOYEEAPPROVALCOUNT	2	0	Total number of approvals required for an employee approval to be accepted and employee enabled in the system	2019-02-08 16:02:43.694823
2	EMPLOYEEAPPROVALROLES	FINANCE,ADMIN	0	The roles of users that are allowed to make an approval on the an employee	2019-02-08 16:02:43.694823
3	AGENTAPPROVALCOUNT	1	0	Total number of approvals required for an agent to be enrolled	2019-02-24 15:27:24.304989
4	AGENTAPPROVALROLES	MARKETING	0	The roles that are required to approve a new agent	2019-02-24 15:27:24.304989
6	NEWEMPLOYEEAPPROVE	0337066d055a488fbb8f4353e53284be	0	Approves all new employees	2019-04-27 18:16:52.281097
7	NEWAGENTAPPROVE	0337066d055a488fbb8f4353e53284be	0	Approves all new agents	2019-04-27 19:20:14.360015
\.


--
-- Data for Name: t_county; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_county (id, name, districtid, inreview) FROM stdin;
2	Ibuje	4	f
\.


--
-- Data for Name: t_customer_meta; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_customer_meta (userid, secondaryphone, secondaryemail, consentformpath, idcopypath, profilephotopath, address, villageid, homelat, homelong, createdon, idtype, idnumber) FROM stdin;
195b9edcc6e8452e9b554d8b02af983b	string	starnapho@gmail.com	consent_949949949494	id_488848848844	profile_48488844	Plot 17, Najanakumbi close	1	4	4	2019-04-27 21:09:02.619312	PASSPORT	4784884884848484884
\.


--
-- Data for Name: t_district; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_district (id, name, regionid, inreview) FROM stdin;
-46	Kakyeka	1	t
-44	Kakyeka1	1	t
-43	Kakyeka1	2	t
4	Lira	2	f
6	Abim	1	t
7	Kitgum	1	t
8	Gulu	1	t
9	Adjumani	1	t
10	Arua	1	t
11	Paidha	1	t
12	Kotido	1	t
13	Moroto	1	t
14	Kampala	2	t
15	Masaka	2	t
16	Mpigi	2	t
17	Luwero	2	t
18	Kagadi	2	t
19	Soroti	4	t
20	Jinja	4	t
21	Mbale	4	t
22	Tororo	4	t
23	Palisa	4	t
24	Mbarara	3	t
25	kaborole	3	t
26	Fort Portal	3	t
27	Nebbi	3	t
28	Masindi	5	t
29	Hoima	5	t
30	Alupot	5	t
\.


--
-- Data for Name: t_employee_meta; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_employee_meta (userid, isterminated, mobile, profilepath, createdon, isapproved, terminatedon, approvedon, village, tinnumber, idtype, idnumber) FROM stdin;
0337066d055a488fbb8f4353e53284be	f	256777110054	userid_488849499949494949	2019-04-27 18:39:13.629136	t	\N	\N	1	488388949949949444444	NATIONALID	0
\.


--
-- Data for Name: t_equip_category; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_equip_category (id, name, description, createon, isactive) FROM stdin;
1	General	Equipment that complements others in achieving a category e.g panel	2019-01-06 11:41:49.076744	t
3	Entertainment	Equipment used in setting up entertainment units	2019-01-06 11:41:49.076744	t
4	Payment	Units used to configure payment collection	2019-01-06 11:41:49.076744	t
5	Lighting	Equipment that is used to support house lighting	2019-01-06 11:41:49.076744	t
727c289ac47a4eb6babf838992662bc0	Light Bright Lamps	This is a test	2019-03-26 13:41:27.027553	t
c0310374272e42989cf456c180e77566	Light Bright Lamps	This is a test	2019-03-26 13:42:11.359999	t
2	House Heater	Equipment used in setup of house water heater	2019-01-06 11:41:49.076744	f
\.


--
-- Data for Name: t_equipment; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_equipment (id, name, model, dateofmanufacture, categoryid, available, description, serial, quantity, createdon, thumbnail) FROM stdin;
488984399492004938994888938938	Torch	zt489938	2018-10-21 00:00:00	1	t	Solar torch with 1 bulb	4899380995893	10	2019-01-11 07:31:17.525117	\N
488999020499395029289300498938	Panel	zt489938	2018-10-22 00:00:00	1	t	Panel of solar cells for charging inserting	5679380995893	11	2019-01-11 07:31:17.525117	\N
388984399492004935995999499499	Water Heater	wtt4898	2018-10-26 00:00:00	2	t	Water header equipment for roof mounting	339938599494	10	2019-01-11 07:31:17.525117	\N
588998439949629300483020489584	Pay Terminal	py489938	2018-10-21 00:00:00	1	t	Box with payment terminal	9879380995893	7	2019-01-11 07:31:17.525117	\N
47b2b789c91e41db93e07e1edfd69121	Torch	T439902	2018-03-26 03:00:00	727c289ac47a4eb6babf838992662bc0	f	Torch for night work	589394899599599595	10	2019-03-26 15:14:19.163402	47b2b789c91e41db93e07e1edfd69121.png
785ab07d5bbb4cad8aa656dbd608575c	Test Equipment	884884k48	2019-02-18 11:39:23.108	1	f	This is a test	4888949499494949	20	2019-02-18 11:45:43.107379	785ab07d5bbb4cad8aa656dbd608575c.png
\.


--
-- Data for Name: t_installation; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_installation (id, saleagentid, customerid, coordinates, createdon, endtime) FROM stdin;
\.


--
-- Data for Name: t_lease; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_lease (id, customerid, leaseofferid, initialdeposit, couponcode, dateinstalled, createdon, saleagentid, dailypayment, totalleaseperiod, expectedfinishdate, totalleasevalue, iscompleted, isactivated, paymentcompletedon) FROM stdin;
\.


--
-- Data for Name: t_lease_offer; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_lease_offer (id, code, productid, leaseperiod, percentlease, recurrentpaymentamount, isactive, intialdeposit, name, description, createdon) FROM stdin;
\.


--
-- Data for Name: t_lease_payment; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_lease_payment (id, leaseid, amount, name, msisdn, channel, status, transactionid, channeltransactionid, channelstatuscode, channelmessage, createdon) FROM stdin;
\.


--
-- Data for Name: t_lease_payment_extra; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_lease_payment_extra (id, leaseid, amount, createdon, isrefunded) FROM stdin;
\.


--
-- Data for Name: t_opex; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_opex (id, employeeid, amount, expensetype, description, createdon, approvedon) FROM stdin;
\.


--
-- Data for Name: t_opex_type; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_opex_type (id, name) FROM stdin;
1	Transport
2	Utilities
3	IT Infrastructure
4	Marketing
5	Miscelleneous
\.


--
-- Data for Name: t_parish; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_parish (id, name, subcountyid, inreview) FROM stdin;
2	Ogwel	2	f
3	Okellokiuch	2	f
4	Tessst	2	f
\.


--
-- Data for Name: t_payment_channel; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_payment_channel (id, channelname, channelcode, isenabled, description, createdon) FROM stdin;
\.


--
-- Data for Name: t_product; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_product (id, name, equipmentlist, unitcostselling, description, thumbnail, createdon, isactive, isleasable, usestoken) FROM stdin;
\.


--
-- Data for Name: t_region_ug; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_region_ug (id, name) FROM stdin;
1	North
2	South
3	East
4	West
5	Central
\.


--
-- Data for Name: t_role; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_role (id, name, description) FROM stdin;
1	ROLE_ENGINEER	Helps with equipment installation on site
2	ROLE_MARKETING	Suppport in marketing activiites
4	ROLE_FINANCE	Finance department team members
5	ROLE_MANAGER	Managment team
3	ROLE_AGENT	Sells equipment to the general public
6	ROLE_HR	Part of human resource team
7	ROLE_SENIOR_MANAGER	Senior management team
8	ROLE_CUSTOMER	Customer
\.


--
-- Data for Name: t_sale; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_sale (id, saletype, amount, description, scannedserial, agentcommission, agentid, long_, lat_, salestatus, createdon, productid, quantity, customerid, completedon) FROM stdin;
\.


--
-- Data for Name: t_sale_rollback; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_sale_rollback (id, saleid, description, createdon) FROM stdin;
\.


--
-- Data for Name: t_subcounty; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_subcounty (id, name, countyid, inreview) FROM stdin;
2	Ibuje	2	f
\.


--
-- Data for Name: t_token; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_token (leasepaymentid, token, createdon, times, days, value, type) FROM stdin;
\.


--
-- Data for Name: t_token_type; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_token_type (id, name) FROM stdin;
0	pay
1	open
2	block
3	unblock
4	reset
\.


--
-- Data for Name: t_total_lease_payments; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_total_lease_payments (id, leaseid, lastpaidamount, totalamountpaid, createdon, nextpaymentdate, totalamountowed, residueamount, times, updatedon) FROM stdin;
\.


--
-- Data for Name: t_user; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_user (id, username, password, isactive, passwordreseton, createdon, usertype, firstname, middlename, lastname, email, primaryphone, companyemail, dateofbirth, lastlogon, updatedon, isapproved, title, sex) FROM stdin;
195b9edcc6e8452e9b554d8b02af983b	25677899002	$2a$10$eBkXsAwM7DBBD7ww3lqxwOtJ2lxgD.P/waKSVpQFLEzHkgIqlrU1C	f	\N	2019-04-27 20:45:09.168171	CUSTOMER	Taban		Ojok	taban@gmail.com	25677899002	taban@gmail.com	1988-04-10	\N	\N	f	MR	MALE
fc179a74c902420bba3d16dfef1522af	256777181902	$2a$10$A5PP.PgO3vzksvGC/hTStuRyFA9UWaqpEXY5WpVHGjYPGwKXTE8WS	f	\N	2019-04-27 19:18:55.355423	AGENT	Herbert	Ian	Akora	herbkora@gmail.com	256777181902	herbkora@gmail.com	1988-04-10	\N	\N	t	MR	MALE
0337066d055a488fbb8f4353e53284be	256777110054	$2a$10$rns1SptIJAT9jcFHm/70eu2V75UY607lw7K31TxzPv01I0vw9zCpG	t	\N	2019-04-24 15:10:30.134847	EMPLOYEE	Naphlin	Peter	Akena	starnapho@gmail.com	256777110054	starnapho@gmail.com	2019-04-10	\N	\N	t	MR	MALE
\.


--
-- Data for Name: t_user_role; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_user_role (userid, roleid, createdon) FROM stdin;
195b9edcc6e8452e9b554d8b02af983b	2	2019-04-29 14:19:56.519756
195b9edcc6e8452e9b554d8b02af983b	1	2019-04-29 14:19:56.519756
195b9edcc6e8452e9b554d8b02af983b	4	2019-04-29 14:19:56.519756
195b9edcc6e8452e9b554d8b02af983b	5	2019-04-29 14:19:56.519756
195b9edcc6e8452e9b554d8b02af983b	3	2019-04-29 14:19:56.519756
0337066d055a488fbb8f4353e53284be	2	2019-04-29 15:17:40.206275
0337066d055a488fbb8f4353e53284be	1	2019-04-29 15:17:40.206275
0337066d055a488fbb8f4353e53284be	4	2019-04-29 15:17:40.206275
\.


--
-- Data for Name: t_village; Type: TABLE DATA; Schema: panda_core; Owner: boss
--

COPY panda_core.t_village (id, name, parishid, inreview) FROM stdin;
1	Test Village	2	f
\.


--
-- Data for Name: t_approver; Type: TABLE DATA; Schema: public; Owner: boss
--

COPY public.t_approver (id, userid, approvalservice, approvalorder, isenabled, createdon) FROM stdin;
\.


--
-- Name: finance_config_id_seq; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.finance_config_id_seq', 7, true);


--
-- Name: s_county; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_county', 4, true);


--
-- Name: s_district; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_district', 30, true);


--
-- Name: s_lease_offer; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_lease_offer', 3, true);


--
-- Name: s_parishes; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_parishes', 5, true);


--
-- Name: s_payment_channel; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_payment_channel', 1, false);


--
-- Name: s_subcounty; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_subcounty', 2, true);


--
-- Name: s_villages; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.s_villages', 1, true);


--
-- Name: t_agents_meta_id_seq; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.t_agents_meta_id_seq', 1, false);


--
-- Name: t_users_id_seq; Type: SEQUENCE SET; Schema: panda_core; Owner: boss
--

SELECT pg_catalog.setval('panda_core.t_users_id_seq', 1, false);


--
-- Name: t_user company_unq; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user
    ADD CONSTRAINT company_unq UNIQUE (email);


--
-- Name: t_user email_unq; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user
    ADD CONSTRAINT email_unq UNIQUE (email);


--
-- Name: t_config finance_config_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_config
    ADD CONSTRAINT finance_config_pkey PRIMARY KEY (id);


--
-- Name: t_equipment serial_key_unique; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_equipment
    ADD CONSTRAINT serial_key_unique UNIQUE (serial);


--
-- Name: t_agent_meta t_agent_meta_un; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_agent_meta
    ADD CONSTRAINT t_agent_meta_un UNIQUE (idtype, idnumber);


--
-- Name: t_agent_meta t_agents_meta_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_agent_meta
    ADD CONSTRAINT t_agents_meta_pkey PRIMARY KEY (userid);


--
-- Name: t_approval_review t_approval_review_pk; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approval_review
    ADD CONSTRAINT t_approval_review_pk PRIMARY KEY (id);


--
-- Name: t_approval_type t_approval_types_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approval_type
    ADD CONSTRAINT t_approval_types_pkey PRIMARY KEY (id);


--
-- Name: t_approval t_approvals_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approval
    ADD CONSTRAINT t_approvals_pkey PRIMARY KEY (id);


--
-- Name: t_approver t_approver_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approver
    ADD CONSTRAINT t_approver_pkey PRIMARY KEY (id);


--
-- Name: t_capex t_capex_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_capex
    ADD CONSTRAINT t_capex_pkey PRIMARY KEY (id);


--
-- Name: t_capex_type t_capex_type_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_capex_type
    ADD CONSTRAINT t_capex_type_pkey PRIMARY KEY (id);


--
-- Name: t_channel t_channel_pk; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_channel
    ADD CONSTRAINT t_channel_pk PRIMARY KEY (id);


--
-- Name: t_county t_county_name_districtid_key; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_county
    ADD CONSTRAINT t_county_name_districtid_key UNIQUE (name, districtid);


--
-- Name: t_county t_county_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_county
    ADD CONSTRAINT t_county_pkey PRIMARY KEY (id);


--
-- Name: t_customer_meta t_customer_meta_idtype_unique_un; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_customer_meta
    ADD CONSTRAINT t_customer_meta_idtype_unique_un UNIQUE (idtype, idnumber);


--
-- Name: t_customer_meta t_customer_meta_pk; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_customer_meta
    ADD CONSTRAINT t_customer_meta_pk PRIMARY KEY (userid);


--
-- Name: t_district t_district_name_regionid_key; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_district
    ADD CONSTRAINT t_district_name_regionid_key UNIQUE (name, regionid);


--
-- Name: t_district t_district_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_district
    ADD CONSTRAINT t_district_pkey PRIMARY KEY (id);


--
-- Name: t_employee_meta t_employee_meta_idtype_unique_un; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_employee_meta
    ADD CONSTRAINT t_employee_meta_idtype_unique_un UNIQUE (idtype, idnumber);


--
-- Name: t_employee_meta t_employees_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_employee_meta
    ADD CONSTRAINT t_employees_pkey PRIMARY KEY (userid);


--
-- Name: t_equip_category t_equip_category_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_equip_category
    ADD CONSTRAINT t_equip_category_pkey PRIMARY KEY (id);


--
-- Name: t_equipment t_equipment_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_equipment
    ADD CONSTRAINT t_equipment_pkey PRIMARY KEY (id);


--
-- Name: t_installation t_installation_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_installation
    ADD CONSTRAINT t_installation_pkey PRIMARY KEY (id);


--
-- Name: t_lease_offer t_lease_offer_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease_offer
    ADD CONSTRAINT t_lease_offer_pkey PRIMARY KEY (id);


--
-- Name: t_lease_payment_extra t_lease_payment_extra_pk; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease_payment_extra
    ADD CONSTRAINT t_lease_payment_extra_pk PRIMARY KEY (id);


--
-- Name: t_lease_payment t_lease_payments_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease_payment
    ADD CONSTRAINT t_lease_payments_pkey PRIMARY KEY (id);


--
-- Name: t_lease t_lease_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease
    ADD CONSTRAINT t_lease_pkey PRIMARY KEY (id);


--
-- Name: t_opex t_opex_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_opex
    ADD CONSTRAINT t_opex_pkey PRIMARY KEY (id);


--
-- Name: t_opex_type t_opex_type_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_opex_type
    ADD CONSTRAINT t_opex_type_pkey PRIMARY KEY (id);


--
-- Name: t_parish t_parishes_name_subcountyid_key; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_parish
    ADD CONSTRAINT t_parishes_name_subcountyid_key UNIQUE (name, subcountyid);


--
-- Name: t_parish t_parishes_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_parish
    ADD CONSTRAINT t_parishes_pkey PRIMARY KEY (id);


--
-- Name: t_payment_channel t_payment_channel_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_payment_channel
    ADD CONSTRAINT t_payment_channel_pkey PRIMARY KEY (id);


--
-- Name: t_product t_products_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_product
    ADD CONSTRAINT t_products_pkey PRIMARY KEY (id);


--
-- Name: t_region_ug t_region_ug_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_region_ug
    ADD CONSTRAINT t_region_ug_pkey PRIMARY KEY (id);


--
-- Name: t_role t_role_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_role
    ADD CONSTRAINT t_role_pkey PRIMARY KEY (id);


--
-- Name: t_sale_rollback t_sale_rollback_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_sale_rollback
    ADD CONSTRAINT t_sale_rollback_pkey PRIMARY KEY (id);


--
-- Name: t_sale t_sales_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_sale
    ADD CONSTRAINT t_sales_pkey PRIMARY KEY (id);


--
-- Name: t_subcounty t_subcounty_name_countyid_key; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_subcounty
    ADD CONSTRAINT t_subcounty_name_countyid_key UNIQUE (name, countyid);


--
-- Name: t_subcounty t_subcounty_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_subcounty
    ADD CONSTRAINT t_subcounty_pkey PRIMARY KEY (id);


--
-- Name: t_token_type t_token_type_pk; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_token_type
    ADD CONSTRAINT t_token_type_pk PRIMARY KEY (id);


--
-- Name: t_user t_users_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user
    ADD CONSTRAINT t_users_pkey PRIMARY KEY (id);


--
-- Name: t_village t_villages_name_parishid_key; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_village
    ADD CONSTRAINT t_villages_name_parishid_key UNIQUE (name, parishid);


--
-- Name: t_village t_villages_pkey; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_village
    ADD CONSTRAINT t_villages_pkey PRIMARY KEY (id);


--
-- Name: t_user username_unq; Type: CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user
    ADD CONSTRAINT username_unq UNIQUE (username);


--
-- Name: t_approver approval_order_unique; Type: CONSTRAINT; Schema: public; Owner: boss
--

ALTER TABLE ONLY public.t_approver
    ADD CONSTRAINT approval_order_unique UNIQUE (approvalservice, approvalorder);


--
-- Name: t_approver approval_ser_user_unqiue; Type: CONSTRAINT; Schema: public; Owner: boss
--

ALTER TABLE ONLY public.t_approver
    ADD CONSTRAINT approval_ser_user_unqiue UNIQUE (approvalservice, userid);


--
-- Name: t_approver t_approver_pkey; Type: CONSTRAINT; Schema: public; Owner: boss
--

ALTER TABLE ONLY public.t_approver
    ADD CONSTRAINT t_approver_pkey PRIMARY KEY (id);


--
-- Name: t_install_created_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_install_created_index ON panda_core.t_installation USING btree (createdon);


--
-- Name: t_install_customer_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_install_customer_index ON panda_core.t_installation USING btree (lower((customerid)::text));


--
-- Name: t_lease_customer_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_customer_index ON panda_core.t_lease USING btree (lower((customerid)::text));


--
-- Name: t_lease_offer_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_offer_index ON panda_core.t_lease_offer USING btree (lower((productid)::text));


--
-- Name: t_lease_offer_name_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_offer_name_index ON panda_core.t_lease_offer USING btree (lower((name)::text));


--
-- Name: t_lease_offer_prdt_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_offer_prdt_index ON panda_core.t_lease_offer USING btree (lower((productid)::text));


--
-- Name: t_lease_payment_extra_id_idx; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE UNIQUE INDEX t_lease_payment_extra_id_idx ON panda_core.t_lease_payment_extra USING btree (id);


--
-- Name: t_lease_payments_created_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_payments_created_index ON panda_core.t_lease_payment USING btree (createdon);


--
-- Name: t_lease_payments_customer_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_payments_customer_index ON panda_core.t_lease_payment USING btree (lower((leaseid)::text));


--
-- Name: t_lease_payments_transid_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_lease_payments_transid_index ON panda_core.t_lease_payment USING btree (lower((transactionid)::text));


--
-- Name: t_serial_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_serial_index ON panda_core.t_equipment USING btree (lower((serial)::text));


--
-- Name: t_tokens_token_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_tokens_token_index ON panda_core.t_token USING btree (token, createdon);


--
-- Name: t_total_trans_index; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE INDEX t_total_trans_index ON panda_core.t_total_lease_payments USING btree (leaseid);


--
-- Name: t_user_email_idx; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE UNIQUE INDEX t_user_email_idx ON panda_core.t_user USING btree (email);


--
-- Name: t_user_username_idx; Type: INDEX; Schema: panda_core; Owner: boss
--

CREATE UNIQUE INDEX t_user_username_idx ON panda_core.t_user USING btree (username);


--
-- Name: t_total_lease_payments panda_core.total_lease_payments_update_trig; Type: TRIGGER; Schema: panda_core; Owner: boss
--

CREATE TRIGGER "panda_core.total_lease_payments_update_trig" BEFORE UPDATE ON panda_core.t_total_lease_payments FOR EACH ROW EXECUTE PROCEDURE panda_core.updated_on_column_updater();


--
-- Name: t_equipment category_equip_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_equipment
    ADD CONSTRAINT category_equip_fk FOREIGN KEY (categoryid) REFERENCES panda_core.t_equip_category(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_subcounty county_sub_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_subcounty
    ADD CONSTRAINT county_sub_id_fk FOREIGN KEY (countyid) REFERENCES panda_core.t_county(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_customer_meta cust_village_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_customer_meta
    ADD CONSTRAINT cust_village_id_fk FOREIGN KEY (villageid) REFERENCES panda_core.t_village(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_county dist_county_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_county
    ADD CONSTRAINT dist_county_id_fk FOREIGN KEY (districtid) REFERENCES panda_core.t_district(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_village dist_county_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_village
    ADD CONSTRAINT dist_county_id_fk FOREIGN KEY (parishid) REFERENCES panda_core.t_parish(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_parish parish_scounty_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_parish
    ADD CONSTRAINT parish_scounty_id_fk FOREIGN KEY (subcountyid) REFERENCES panda_core.t_subcounty(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_district regi_dist_id_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_district
    ADD CONSTRAINT regi_dist_id_fk FOREIGN KEY (regionid) REFERENCES panda_core.t_region_ug(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_agent_meta t_agent_meta_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_agent_meta
    ADD CONSTRAINT t_agent_meta_t_user_fk FOREIGN KEY (userid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_sale t_agent_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_sale
    ADD CONSTRAINT t_agent_t_user_fk FOREIGN KEY (agentid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_approval t_approvals_approvaltype_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approval
    ADD CONSTRAINT t_approvals_approvaltype_fkey FOREIGN KEY (approvaltype) REFERENCES panda_core.t_approval_type(id);


--
-- Name: t_approver t_approver_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_approver
    ADD CONSTRAINT t_approver_t_user_fk FOREIGN KEY (userid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: t_capex t_capex_employeeid_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_capex
    ADD CONSTRAINT t_capex_employeeid_fkey FOREIGN KEY (employeeid) REFERENCES panda_core.t_employee_meta(userid);


--
-- Name: t_capex t_capex_expensetype_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_capex
    ADD CONSTRAINT t_capex_expensetype_fkey FOREIGN KEY (expensetype) REFERENCES panda_core.t_capex_type(id);


--
-- Name: t_customer_meta t_customer_meta_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_customer_meta
    ADD CONSTRAINT t_customer_meta_t_user_fk FOREIGN KEY (userid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_employee_meta t_employee_meta_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_employee_meta
    ADD CONSTRAINT t_employee_meta_t_user_fk FOREIGN KEY (userid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_installation t_inst_saleagnt_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_installation
    ADD CONSTRAINT t_inst_saleagnt_fk FOREIGN KEY (saleagentid) REFERENCES panda_core.t_employee_meta(userid) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_installation t_installation_t_employee_meta_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_installation
    ADD CONSTRAINT t_installation_t_employee_meta_fk FOREIGN KEY (customerid) REFERENCES panda_core.t_employee_meta(userid);


--
-- Name: t_lease_offer t_lease_offer_productid_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease_offer
    ADD CONSTRAINT t_lease_offer_productid_fkey FOREIGN KEY (productid) REFERENCES panda_core.t_product(id);


--
-- Name: t_lease t_lease_to_lease_offer_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_lease
    ADD CONSTRAINT t_lease_to_lease_offer_fk FOREIGN KEY (leaseofferid) REFERENCES panda_core.t_lease_offer(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_opex t_opex_employeeid_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_opex
    ADD CONSTRAINT t_opex_employeeid_fkey FOREIGN KEY (employeeid) REFERENCES panda_core.t_employee_meta(userid);


--
-- Name: t_opex t_opex_expensetype_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_opex
    ADD CONSTRAINT t_opex_expensetype_fkey FOREIGN KEY (expensetype) REFERENCES panda_core.t_opex_type(id);


--
-- Name: t_sale_rollback t_sale_rollback_saleid_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_sale_rollback
    ADD CONSTRAINT t_sale_rollback_saleid_fkey FOREIGN KEY (saleid) REFERENCES panda_core.t_sale(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_sale t_sale_t_user_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_sale
    ADD CONSTRAINT t_sale_t_user_fk FOREIGN KEY (customerid) REFERENCES panda_core.t_user(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_token t_token_t_token_type_fk; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_token
    ADD CONSTRAINT t_token_t_token_type_fk FOREIGN KEY (type) REFERENCES panda_core.t_token_type(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- Name: t_user_role t_user_roles_roleid_fkey; Type: FK CONSTRAINT; Schema: panda_core; Owner: boss
--

ALTER TABLE ONLY panda_core.t_user_role
    ADD CONSTRAINT t_user_roles_roleid_fkey FOREIGN KEY (roleid) REFERENCES panda_core.t_role(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

