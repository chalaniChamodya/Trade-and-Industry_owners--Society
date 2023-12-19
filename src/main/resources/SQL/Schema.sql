DROP DATABASE trade_and_industrial_owners_society;

CREATE DATABASE trade_and_industrial_owners_society;

USE trade_and_industrial_owners_society;

CREATE TABLE member(
                       member_id VARCHAR(20) PRIMARY KEY,
                       name_with_initials VARCHAR(100) NOT NULL,
                       full_name VARCHAR(200) NOT NULL,
                       business_address VARCHAR(200) NOT NULL,
                       personal_address VARCHAR(200) NOT NULL,
                       business_Type VARCHAR(100) NOT NULL,
                       nic VARCHAR(30) UNIQUE NOT NULL,
                       email VARCHAR(50),
                       date_of_birth DATE NOT NULL,
                       personal_contact_num VARCHAR(15) NOT NULL,
                       business_contact_num VARCHAR(15) NOT NULL,
                       admission_fee VARCHAR(7),
                       joined_date DATE NOT NULL
);

INSERT INTO member VALUES ("M001","Kamal","Kamal Gamage","Galle","Galle","Shop","123456789v","chalanichamodya2003@gmail.com","1888-4-05","0714562359","0724532953","200","2015-01-15");

CREATE TABLE committee_member(
                                 committee_mem_id VARCHAR(20) PRIMARY KEY,
                                 member_id VARCHAR(20),
                                 name VARCHAR(100) NOT NULL,
                                 position VARCHAR(100) NOT NULL,
                                 date DATE NOT NULL,
                                 FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);

INSERT INTO committee_member VALUES ("C001","M001","Kamal","Chairman","2023-10-04");

CREATE TABLE user(
                     user_id VARCHAR(20) PRIMARY KEY,
                     com_mem_id VARCHAR(15) NOT NULL,
                     role VARCHAR(15) NOT NULL ,
                     username VARCHAR(50) NOT NULL,
                     password VARCHAR(10) NOT NULL,
                     FOREIGN KEY(com_mem_id) REFERENCES committee_member(committee_mem_id) on Delete Cascade on Update Cascade

);

CREATE TABLE member_family (
                               family_mem_id VARCHAR(20) PRIMARY KEY,
                               member_id VARCHAR(20) NOT NULL,
                               name VARCHAR(150) NOT NULL,
                               relationship VARCHAR(100) NOT NULL,
                               occupation VARCHAR(100),
                               date_of_birth DATE,
                               isAlive VARCHAR(10),
                               FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);

CREATE TABLE death_benefit(
                              death_benefit_id VARCHAR(20) PRIMARY KEY,
                              date DATE,
                              amount VARCHAR(30) NOT NULL,
                              family_mem_id VARCHAR(20),
                              FOREIGN KEY(family_mem_id) REFERENCES member_family(family_mem_id) on Delete Cascade on Update Cascade
);

CREATE TABLE scholarship(
                            scholarship_id VARCHAR(20) PRIMARY KEY,
                            date           DATE,
                            amount         VARCHAR(30) NOT NULL,
                            family_mem_id  VARCHAR(20),
                            FOREIGN KEY (family_mem_id) REFERENCES member_family (family_mem_id) on Delete Cascade on Update Cascade
);

CREATE TABLE general_meeting(
                                gen_meeting_id VARCHAR(20) PRIMARY KEY,
                                date DATE NOT NULL,
                                time TIME NOT NULL,
                                description VARCHAR(1000),
                                location VARCHAR(50) NOT NULL
);

CREATE TABLE general_attendance(
                                   gen_meeting_id VARCHAR(20) NOT NULL,
                                   member_id VARCHAR(20) NOT NULL,
                                   member_name VARCHAR(100) NOT NULL,
                                   date DATE NOT NULL ,
                                   time TIME NOT NULL,
                                   FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade,
                                   FOREIGN KEY(gen_meeting_id) REFERENCES general_meeting(gen_meeting_id) on Delete Cascade on Update Cascade
);


CREATE TABLE committee_meeting(
                                  com_meeting_id VARCHAR(20) PRIMARY KEY,
                                  date DATE NOT NULL,
                                  time TIME NOT NULL,
                                  description VARCHAR(1000),
                                  location VARCHAR(50) NOT NULL
);

CREATE TABLE committee_attendance(
                                     com_meeting_id VARCHAR(20) NOT NULL,
                                     member_id VARCHAR(20) NOT NULL,
                                     member_name VARCHAR(100) NOT NULL,
                                     date DATE NOT NULL ,
                                     time TIME NOT NULL,
                                     FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade,
                                     FOREIGN KEY(com_meeting_id) REFERENCES committee_meeting(com_meeting_id) on Delete Cascade on Update Cascade
);


CREATE TABLE member_fee(
                           member_fee_id VARCHAR(20) PRIMARY KEY,
                           member_id VARCHAR(20) NOT NULL,
                           member_name VARCHAR(100) NOT NULL,
                           date DATE NOT NULL,
                           amount VARCHAR(10) NOT NULL,
                           FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);

CREATE TABLE subscription_fee(
                                 subscription_fee_id VARCHAR(20) PRIMARY KEY,
                                 member_id VARCHAR(20) NOT NULL,
                                 member_name VARCHAR(100) NOT NULL,
                                 date DATE NOT NULL,
                                 amount VARCHAR(10) NOT NULL,
                                 FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);

CREATE TABLE funding_program (
                                 program_id VARCHAR(20) PRIMARY KEY,
                                 name VARCHAR(100) NOT NULL,
                                 description VARCHAR(200) NOT NULL,
                                 date DATE,
                                 location VARCHAR(150) NOT NULL,
                                 income VARCHAR(15),
                                 expenditure VARCHAR(15)
);

CREATE TABLE member_participation_detail (
                                             member_id VARCHAR(20) NOT NULL,
                                             program_id VARCHAR(20) NOT NULL,
                                             name VARCHAR(100) NOT NULL,
                                             FOREIGN KEY(program_id) REFERENCES funding_program(program_id) on Delete Cascade on Update Cascade,
                                             FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);

CREATE TABLE sponsor (
                         sponsor_id VARCHAR(20) PRIMARY KEY,
                         program_id VARCHAR(20) NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         description VARCHAR(200) NOT NULL,
                         date DATE,
                         amount VARCHAR(15) NOT NULL,
                         FOREIGN KEY(program_id) REFERENCES funding_program(program_id) on Delete Cascade on Update Cascade
);
CREATE TABLE special_schol(
                              schol_id VARCHAR(20) PRIMARY KEY,
                              member_id VARCHAR(20) NOT NULL,
                              member_name VARCHAR(100) NOT NULL,
                              date DATE NOT NULL,
                              amount VARCHAR(10) NOT NULL,
                              FOREIGN KEY(member_id) REFERENCES member(member_id) on Delete Cascade on Update Cascade
);
