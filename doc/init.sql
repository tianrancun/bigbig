CREATE TABLE "s_person" (
	id  varchar(20),
	username varchar(20),
	age varchar(20),
);

insert into s_person values('1','zhangshan','male');

CREATE TABLE "s_card" (
	id varchar(20),
	num varchar(20)
);

insert into s_card values('1','123456'),('2','123457');

CREATE TABLE "s_person_card" (
	p_id varchar(20),
	c_id varchar(20),
);
insert into s_person_card values('1','1'),('1','2');

CREATE TABLE "student_class" (
	class_nbr int,
	grade varchar(20),

);


CREATE TABLE "user1" (
	user_id int identity(1,1),
	name varchar(20),
	sex varchar(10),
	email varchar(100)
);

insert into user1('name','sex','email') values('zhang','male','abc@sina.com');


CREATE TABLE student (
	student_id int identity(1,1),
	student_name varchar(20),
	student_nbr varchar(10),
	class_nbr varchar(10),
	age varchar(2),
	email varchar(50)
);
insert into student values
('xiaoming','001','4','15','xiaoming@samsclub.com'),
('xiaozhang','002','4','15','xiaozhang@samsclub.com'),
('xiaohong','003','4','15','xiaohong@samsclub.com');

update student set class_nbr=4;

CREATE TABLE claim (
claim_id int NOT NULL IDENTITY(1,1),
club_nbr int NOT NULL,
state varchar(40) NOT NULL,
claim_reason_code varchar(12) NOT NULL,
claim_type_code varchar(12) NOT NULL,
claim_disposition_type_code varchar(12) NOT NULL,
claim_name varchar(40) NOT NULL,
created_user varchar(24) NOT NULL,
claim_open_ts datetime2 NOT NULL,
claim_sequence_nbr varchar(4) NOT NULL,
hazmat_id int,
last_modified_user varchar(24) NOT NULL,
last_modified_ts datetime2 NOT NULL,
return_auth_nbr varchar(24),
ship_charge_amt float,
store_notes_txt varchar(256),
claim_weight float,
tracking_nbr varchar(256),
vendor_dept_nbr int,
vendor_name varchar(256),
vendor_nbr int,
claim_shipment_id int,
box_audited bit,
pallet_id int,
box_audited_user varchar(24),
box_audited_ts datetime2,
recall_id int,
recall_end_date date,
CONSTRAINT PK__claim PRIMARY KEY (claim_id),
CONSTRAINT FKClaimShipmentReference FOREIGN KEY (claim_shipment_id) REFERENCES claim_shipment(claim_shipment_id),
CONSTRAINT FKClaimPalletReference FOREIGN KEY (pallet_id) REFERENCES claim_pallet(pallet_id)
) ;

CREATE TABLE claim_item (
claim_item_id int NOT NULL IDENTITY(1,1),
claim_id int,
item_nbr int NOT NULL,
cat_id int,
stock_number varchar(256),
upc_nbr varchar(15) NOT NULL,
item_qty smallint NOT NULL,
virt_qty smallint NOT NULL,
item_desc varchar(256),
item_desc2 varchar(256),
item_cost_amt numeric(19,2) NOT NULL,
item_sell_amt numeric(19,2) NOT NULL,
item_retail_amt numeric(19,2) NOT NULL,
vendor_credit_amt numeric(19,2),
handling_charge_amt numeric(19,2),
disposal_charge_amt numeric(19,2),
bucket_color varchar(12),
bucket_description varchar(24),
bucket_id smallint,
damage_reason varchar(256),
damage_type varchar(24),
gtin_nbr varchar(15) NOT NULL,
hazmat_id int,
hazwaste smallint,
vendor_dept_nbr smallint NOT NULL,
vendor_name varchar(256) NOT NULL,
vendor_nbr int NOT NULL,
vendor_pack_amt numeric(19,2),
vendor_pack_qty smallint,
vendor_seq_nbr smallint NOT NULL,
created_user varchar(24) NOT NULL,
created_ts datetime2 NOT NULL,
last_modified_user varchar(24) NOT NULL,
last_modified_ts datetime2 NOT NULL,

cm_rules varchar(2048),

dot_haz_class_code VARCHAR(12),
dot_haz_class_code_desc VARCHAR(48),
dot_reg_code VARCHAR(24),
dot_hazmat_nbr VARCHAR(12),
shipping_name VARCHAR(254),
limited_qty int,
dot_packing_code VARCHAR(12),
dot_exemption_nbr VARCHAR(24),
flash_point_amt VARCHAR(12),
flash_point_uom VARCHAR(12),
marine_pollute_ind BIT,

CONSTRAINT PK__claim_item PRIMARY KEY (claim_item_id),
CONSTRAINT FKClaimReference FOREIGN KEY (claim_id) REFERENCES claim(claim_id)
) ;
--mssql sequence
--select next value for claims.claim_seq_1;
--ALTER SEQUENCE claims.claim_seq_1 RESTART WITH 1