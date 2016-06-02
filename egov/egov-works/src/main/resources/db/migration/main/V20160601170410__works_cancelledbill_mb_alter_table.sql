ALTER TABLE EGW_CANCELLED_BILL ADD COLUMN version bigint DEFAULT 0;

ALTER TABLE EGW_CANCELLED_BILL RENAME COLUMN MBHEADER_ID TO MBHEADER;
ALTER TABLE EGW_CANCELLED_BILL RENAME COLUMN BILLREGISTER_ID TO CONTRACTORBILLREGISTER;

ALTER TABLE EGW_CANCELLED_BILL ADD COLUMN CREATEDBY bigint NOT NULL;
ALTER TABLE EGW_CANCELLED_BILL ADD COLUMN CREATEDDATE timestamp without time zone NOT NULL;
ALTER TABLE EGW_CANCELLED_BILL ADD COLUMN lastmodifiedby bigint;
ALTER TABLE EGW_CANCELLED_BILL ADD COLUMN lastmodifieddate timestamp without time zone;

ALTER TABLE EGW_CANCELLED_BILL ADD CONSTRAINT fk_cancelledbill_createdby FOREIGN KEY (createdby) REFERENCES eg_user (id);
ALTER TABLE EGW_CANCELLED_BILL ADD CONSTRAINT fk_cancelledbill_modifiedby FOREIGN KEY (lastmodifiedby) REFERENCES eg_user (id); 
  
--rollback ALTER TABLE EGW_CANCELLED_BILL DROP COLUMN lastmodifieddate;
--rollback ALTER TABLE EGW_CANCELLED_BILL DROP COLUMN lastmodifiedby;
--rollback ALTER TABLE EGW_CANCELLED_BILL DROP COLUMN CREATEDDATE;
--rollback ALTER TABLE EGW_CANCELLED_BILL DROP COLUMN CREATEDBY;

--rollback ALTER TABLE EGW_CANCELLED_BILL RENAME COLUMN CONTRACTORBILLREGISTER TO BILLREGISTER_ID;
--rollback ALTER TABLE EGW_CANCELLED_BILL RENAME COLUMN MBHEADER TO MBHEADER_ID;
--rollback ALTER TABLE EGW_CANCELLED_BILL DROP COLUMN version;