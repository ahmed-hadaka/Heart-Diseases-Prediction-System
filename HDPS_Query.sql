DROP DATABASE IF EXISTS heartdiseasepredictionSystem;
CREATE DATABASE heartdiseasepredictionSystem;
USE heartdiseasepredictionSystem;

-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE person;
-- TRUNCATE TABLE role;
-- TRUNCATE TABLE address;
-- SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO role (name) values("ADMIN");
INSERT INTO role (name) values("PATIENT");
INSERT INTO role (name) values("DOCTOR");

INSERT INTO address(city,country,state,street_address) VALUES("cairo","Egypt","Qesm Thany- Nasr City", "NourElkhatab streat");

-- Admin(password: pakapoka)
INSERT INTO person(email,name,password,user_name,contact_number,address_id,role_id) VALUES("ahmed@gmail.com","Ahmed Hadaka", "$2a$12$q/QaSRQa1DGtyIfEZVPV6OFFyiOd4bd4YwDETLJrAIarUqjMRrTS.","ahmedhadaka73","+201284282809",1,1);
-- Doctor(password: 123#Aya)
INSERT INTO person(email,name,password,user_name,contact_number,address_id,role_id) VALUES("Aya@gmail.com","Aya Ali", "$2a$12$NvYxq16w3htSLdmqN9MBSO2H.PecqBLTfJ3Wnmmczo8zm.b.G0Uui","ayaali73","+201283582809",1,3);
INSERT INTO doctor(id,work_time,specialization) VALUES(4,"","Heart Failure");
-- we must insert it because the person & doctor & patients are different tables in mysql.
-- Patient: ali (password:123#ahmed)
-- Patient: ahmedhadaka8 (password:"123#ali)
INSERT INTO prediction ( prediction_result,risk_score,patient_id)VALUES("UnHealthy","75%",7);


SELECT * FROM person;
SELECT * FROM patient pat JOIN person per ON pat.id = per.id;
-- WHERE name = "ali gomha";
SELECT * FROM doctor;
SELECT * FROM role;
SELECT * FROM address;
SELECT * FROM prediction;

