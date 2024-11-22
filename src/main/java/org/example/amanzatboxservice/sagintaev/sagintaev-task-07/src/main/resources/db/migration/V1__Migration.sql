CREATE TABLE user_account (
                             id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                             date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             blocked BOOLEAN DEFAULT FALSE
);

CREATE TABLE borrower (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          user_account_id BIGINT NOT NULL,
                          FOREIGN KEY (user_account_id) REFERENCES user_account(id) ON DELETE CASCADE
);

CREATE TABLE personal_data (
                              id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              borrower_id BIGINT NOT NULL,
                              birthday DATE,
                              first_name VARCHAR(100) NOT NULL,
                              last_name VARCHAR(100) NOT NULL,
                              marital_status ENUM('UNMARRIED', 'MARRIED', 'WIDOWED', 'DIVORCED', 'CIVIL_MARRIAGE'),
                              FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE
);

CREATE TABLE work (
                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                      borrower_id BIGINT NOT NULL,
                      company_name VARCHAR(255) NOT NULL,
                      position VARCHAR(100) NOT NULL,
                      start_date DATE,
                      FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE
);

CREATE TABLE address (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         borrower_id BIGINT NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         street VARCHAR(255) NOT NULL,
                         house_number VARCHAR(50) NOT NULL,
                         apartment_number VARCHAR(50),
                         FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE
);

CREATE TABLE credit (
                        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        borrower_id BIGINT NOT NULL,
                        amount DECIMAL(10, 2) NOT NULL,
                        interest_rate DECIMAL(5, 2) NOT NULL,
                        start_date DATE,
                        end_date DATE,
                        status ENUM('ACTIVE', 'CLOSED', 'DEFAULTED'),
                        FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE
);

CREATE TABLE product (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         product_name VARCHAR(255) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         quantity INT NOT NULL
);

CREATE TABLE payment (
                         id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         credit_id BIGINT NOT NULL,
                         amount DECIMAL(10, 2) NOT NULL,
                         payment_date DATE,
                         FOREIGN KEY (credit_id) REFERENCES credit(id) ON DELETE CASCADE
);

CREATE TABLE verifier (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          contact_info VARCHAR(255)
);

CREATE TABLE verification_credit (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    borrower_id BIGINT NOT NULL,
                                    verifier_id BIGINT NOT NULL,
                                    verificationDate DATE,
                                    status ENUM('PENDING', 'VERIFIED', 'REJECTED'),
                                    FOREIGN KEY (borrower_id) REFERENCES borrower(id) ON DELETE CASCADE,
                                    FOREIGN KEY (verifier_id) REFERENCES verifier(id) ON DELETE CASCADE
);

CREATE TABLE verification_call (
                                  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  verification_credit_id BIGINT NOT NULL,
                                  call_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  duration INT,
                                  notes TEXT,
                                  FOREIGN KEY (verification_credit_id) REFERENCES verification_credit(id) ON DELETE CASCADE
);

CREATE TABLE collector (
                           id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           contact_info VARCHAR(255)
);

CREATE TABLE collector_credit (
                                 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                 credit_id BIGINT NOT NULL,
                                 collector_id BIGINT NOT NULL,
                                 transferred_date DATE,
                                 FOREIGN KEY (credit_id) REFERENCES credit(id) ON DELETE CASCADE,
                                 FOREIGN KEY (collector_id) REFERENCES collector(id) ON DELETE CASCADE
);

CREATE TABLE collector_interaction (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      collector_credit_id BIGINT NOT NULL,
                                      interaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                      notes TEXT,
                                      FOREIGN KEY (collector_credit_id) REFERENCES collector_credit(id) ON DELETE CASCADE
);

alter table work
    add industry varchar(120),
    add education varchar(120);

-- Вставка данных в таблицу UserAccount
INSERT INTO user_account (email, blocked) VALUES
                                             ('john.doe@example.com', FALSE),
                                             ('jane.smith@example.com', FALSE),
                                             ('bob.johnson@example.com', TRUE), -- Заблокирован
                                             ('alice.brown@example.com', FALSE),
                                             ('mike.davis@example.com', FALSE),
                                             ('lisa.white@example.com', FALSE),
                                             ('tom.black@example.com', FALSE); -- Новый пользователь

-- Вставка данных в таблицу Borrower
INSERT INTO borrower (user_account_id) VALUES
                                         (1), -- John Doe
                                         (2), -- Jane Smith
                                         (3), -- Bob Johnson (заблокирован)
                                         (4), -- Alice Brown
                                         (5), -- Mike Davis
                                         (6), -- Lisa White
                                         (7); -- Tom Black

-- Вставка данных в таблицу PersonalData
INSERT INTO personal_data (borrower_id, birthday, first_name, last_name, marital_status) VALUES
                                                                                        (1, '1985-05-15', 'John', 'Doe', 'MARRIED'),
                                                                                        (2, '1990-10-30', 'Jane', 'Smith', 'UNMARRIED'),
                                                                                        (3, '1978-03-22', 'Bob', 'Johnson', 'DIVORCED'),
                                                                                        (4, '1983-08-14', 'Alice', 'Brown', 'CIVIL_MARRIAGE'),
                                                                                        (5, '1992-02-11', 'Mike', 'Davis', 'UNMARRIED'),
                                                                                        (6, '1988-07-25', 'Lisa', 'White', 'MARRIED'),
                                                                                        (7, '1995-12-02', 'Tom', 'Black', 'UNMARRIED');

-- Вставка данных в таблицу Work
INSERT INTO work (borrower_id, company_name, position, start_date, industry, education) VALUES
                                                                                         (1, 'ABC Corp', 'Software Engineer', '2010-06-01', 'IT', 'Bachelor\'s Degree'),
(2, 'XYZ Ltd', 'Marketing Manager', '2015-03-15', 'Marketing', 'Master\'s Degree'),
                                                                                         (4, '123 Inc', 'Project Coordinator', '2021-01-01', 'Management', 'Bachelor\'s Degree'),
(5, 'Tech Solutions', 'Data Analyst', '2020-05-20', 'IT', 'Bachelor\'s Degree'),
                                                                                         (6, 'Global Marketing', 'Senior Strategist', '2019-11-01', 'Marketing', 'Master\'s Degree'),
(7, 'Local Ventures', 'Sales Executive', '2023-01-10', 'Sales', 'Bachelor\'s Degree');

-- Вставка данных в таблицу Address
INSERT INTO address (borrower_id, city, street, house_number, apartment_number) VALUES
                                                                                 (1, 'New York', '5th Avenue', '10', '101'),
                                                                                 (2, 'Los Angeles', 'Sunset Blvd', '20', '201'),
                                                                                 (4, 'Chicago', 'Lake Shore Dr', '30', NULL),
                                                                                 (5, 'San Francisco', 'Market St', '50', '301'),
                                                                                 (6, 'Boston', 'Boylston St', '15', '202'),
                                                                                 (7, 'Seattle', 'Pine St', '100', '10');


ALTER TABLE product
    ADD COLUMN type ENUM('START', 'VZLET', 'PILOTAZH', 'TURBO', 'SUPER_TURBO') NOT NULL,
    ADD COLUMN finance_type ENUM('PAYDAY_LOAN', 'INSTALLMENT_LOAN') NOT NULL,
    ADD COLUMN min_cost DECIMAL(10, 2),
    ADD COLUMN max_cost DECIMAL(10, 2),
    ADD COLUMN min_period INT,
    ADD COLUMN max_period INT,
    ADD COLUMN period_type ENUM('DAYS', 'WEEKS'),
    ADD COLUMN initial_cost DECIMAL(10, 2),
    ADD COLUMN initial_period INT,
    ADD COLUMN percent_per_day DECIMAL(5, 2),
    ADD COLUMN penalty DECIMAL(10, 2);



ALTER TABLE credit
DROP COLUMN start_date,
    DROP COLUMN end_date,
    ADD COLUMN date_requested DATETIME DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN date_repaid DATE,
    ADD COLUMN due_date DATE,
    ADD COLUMN period INT,
    ADD COLUMN period_type ENUM('DAYS', 'WEEKS'),
    ADD COLUMN percent_per_day DECIMAL(5, 2),
    ADD COLUMN main_debt DECIMAL(10, 2),
    ADD COLUMN penalty DECIMAL(10, 2),
    ADD COLUMN penalty_debt DECIMAL(10, 2),
    ADD COLUMN product_id BIGINT,
    ADD FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE;

DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         credit_id BIGINT NOT NULL,
                         amount DOUBLE NOT NULL,
                         received_date DATETIME NOT NULL,
                         goal ENUM('ROLLOVER', 'DEBT', 'RESTRUCTING') NOT NULL,
                         source ENUM('BANK_ACCOUNT', 'CREDIT_CARD', 'BONUS') NOT NULL,
                         source_payment_id VARCHAR(255),
                         raw_source_data_id VARCHAR(255),
                         FOREIGN KEY (credit_id) REFERENCES credit(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS verification_call;
DROP TABLE IF EXISTS verification_credit;
DROP TABLE IF EXISTS verifier;

CREATE TABLE verifier (
                          id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          activity_status ENUM('IN_WORK', 'ON_PAUSE', 'NOT_IN_WORK') NOT NULL
);

CREATE TABLE verification_credit (
                                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    credit_id BIGINT NOT NULL,
                                    verifier_id BIGINT NOT NULL,
                                    status ENUM('WAIT_VERIFICATION', 'APPROVED', 'CANCELLED') NOT NULL,
                                    status_date DATETIME NOT NULL,
                                    FOREIGN KEY (verifier_id) REFERENCES verifier(id) ON DELETE CASCADE
);

CREATE TABLE verification_call (
                                  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  verification_credit_id BIGINT NOT NULL,
                                  status ENUM('NOT_ANSWERED', 'ANSWERED') NOT NULL,
                                  call_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  call_duration_seconds BIGINT,
                                  FOREIGN KEY (verification_credit_id) REFERENCES verification_credit(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS collector_interaction;
DROP TABLE IF EXISTS collector_credit;
DROP TABLE IF EXISTS collector;

CREATE TABLE collector (
                           id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           activity_status ENUM('IN_WORK', 'ON_PAUSE', 'NOT_IN_WORK') NOT NULL
);

CREATE TABLE collector_credit (
                                 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                 credit_id BIGINT NOT NULL,
                                 collector_id BIGINT NOT NULL,
                                 status ENUM('WAIT_COLLECTION', 'IN_COLLECTION', 'COLLECTED') NOT NULL,
                                 status_date DATETIME NOT NULL,
                                 FOREIGN KEY (credit_id) REFERENCES credit(id) ON DELETE CASCADE,
                                 FOREIGN KEY (collector_id) REFERENCES collector(id) ON DELETE CASCADE
);

CREATE TABLE collector_interaction (
                                      id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      collector_credit_id BIGINT NOT NULL,
                                      interaction_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                      interaction_type ENUM('CALL_TO_BORROWER', 'CALL_TO_EMPLOYER', 'CALL_TO_FAMILY_PERSON', 'VISIT_HOME') NOT NULL,
                                      interaction_status ENUM('AGREED', 'NOT_AGREED', 'TRIED_BUT_NOT_INTERACTED') NOT NULL,
                                      FOREIGN KEY (collector_credit_id) REFERENCES collector_credit(id) ON DELETE CASCADE
);

INSERT INTO user_account (email, blocked) VALUES
                                             ('user1@example.com', FALSE),
                                             ('user2@example.com', FALSE),
                                             ('user3@example.com', TRUE),
                                             ('user4@example.com', FALSE),
                                             ('user5@example.com', FALSE);

INSERT INTO borrower (user_account_id) VALUES
                                         (1),  -- user1
                                         (2),  -- user2
                                         (3),  -- user3
                                         (4);  -- user4


INSERT INTO personal_data (borrower_id, birthday, first_name, last_name, marital_status) VALUES
                                                                                        (1, '1990-01-01', 'John', 'Doe', 'MARRIED'),
                                                                                        (2, '1985-05-15', 'Jane', 'Smith', 'UNMARRIED'),
                                                                                        (3, '1975-12-30', 'Bob', 'Johnson', 'DIVORCED'),
                                                                                        (4, '1992-08-23', 'Alice', 'Williams', 'MARRIED');

-- Work
INSERT INTO work (borrower_id, company_name, position, start_date, industry, education) VALUES
                                                                                         (1, 'Tech Solutions', 'Software Engineer', '2015-03-01', 'Information Technology', 'Bachelor\'s'),
                                                                                         (2, 'Financial Services', 'Analyst', '2018-06-15', 'Finance', 'Master\'s'),
                                                                                         (4, 'Health Corp', 'Nurse', '2020-01-10', 'Healthcare', 'Bachelor\'s');

-- Address
INSERT INTO address (borrower_id, city, street, house_number, apartment_number) VALUES
                                                                                 (1, 'New York', '1st Ave', '123', '1A'),
                                                                                 (2, 'Los Angeles', '2nd St', '456', '2B'),
                                                                                 (3, 'Chicago', '3rd St', '789', NULL),
                                                                                 (4, 'Houston', '4th St', '101', '4C');

-- Product
INSERT INTO product (product_name, price, quantity, type, finance_type, min_cost, max_cost, min_period, max_period, period_type, initial_cost, initial_period, percent_per_day, penalty) VALUES
                                                                                                                                                                                  ('Personal Loan', 5000.00, 10, 'START', 'PAYDAY_LOAN', 1000.00, 5000.00, 7, 30, 'DAYS', 100.00, 14, 0.5, 100.00),
                                                                                                                                                                                  ('Home Loan', 150000.00, 5, 'VZLET', 'INSTALLMENT_LOAN', 15000.00, 150000.00, 30, 360, 'DAYS', 3000.00, 12, 0.3, 300.00);

-- Credit
INSERT INTO credit (borrower_id, amount, interest_rate, date_requested, due_date, period, period_type, product_id) VALUES
                                                                                                                 (1, 5000.00, 5.0, NOW(), '2024-12-01', 30, 'DAYS', 1),
                                                                                                                 (2, 3000.00, 7.0, NOW(), '2025-01-01', 60, 'DAYS', 1),
                                                                                                                 (1, 10000.00, 6.0, NOW(), '2024-11-15', 90, 'DAYS', 2),
                                                                                                                 (4, 20000.00, 5.5, NOW(), '2024-10-20', 120, 'DAYS', 2);

-- Payment
INSERT INTO payment (credit_id, amount, received_date, goal, source) VALUES
                                                                       (1, 500.00, NOW(), 'DEBT', 'BANK_ACCOUNT'),
                                                                       (1, 200.00, NOW(), 'ROLLOVER', 'CREDIT_CARD'),
                                                                       (2, 300.00, NOW(), 'DEBT', 'BONUS'),
                                                                       (3, 600.00, NOW(), 'ROLLOVER', 'BANK_ACCOUNT'),
                                                                       (4, 800.00, NOW(), 'DEBT', 'CREDIT_CARD');

-- Verifier
INSERT INTO verifier (first_name, last_name, activity_status) VALUES
                                                               ('Michael', 'Brown', 'IN_WORK'),
                                                               ('Sarah', 'Davis', 'NOT_IN_WORK'),
                                                               ('David', 'Wilson', 'ON_PAUSE');

-- VerificationCredit
INSERT INTO verification_credit (credit_id, verifier_id, status, status_date) VALUES
                                                                              (1, 1, 'WAIT_VERIFICATION', NOW()),
                                                                              (2, 1, 'APPROVED', NOW()),
                                                                              (3, 2, 'CANCELLED', NOW());

-- VerificationCall
INSERT INTO verification_call (verification_credit_id, status, call_duration_seconds) VALUES
                                                                                     (1, 'ANSWERED', 120),
                                                                                     (2, 'NOT_ANSWERED', NULL),
                                                                                     (3, 'ANSWERED', 80);

-- Collector
INSERT INTO collector (first_name, last_name, activity_status) VALUES
                                                                ('Tom', 'Smith', 'IN_WORK'),
                                                                ('Emma', 'Jones', 'NOT_IN_WORK'),
                                                                ('Oliver', 'Garcia', 'ON_PAUSE');

-- CollectorCredit
INSERT INTO collector_credit (credit_id, collector_id, status, status_date) VALUES
                                                                            (1, 1, 'IN_COLLECTION', NOW()),
                                                                            (2, 2, 'COLLECTED', NOW()),
                                                                            (3, 1, 'WAIT_COLLECTION', NOW());

-- CollectorInteraction
INSERT INTO collector_interaction (collector_credit_id, interaction_date, interaction_type, interaction_status) VALUES
                                                                                                              (1, NOW(), 'CALL_TO_BORROWER', 'AGREED'),
                                                                                                              (2, NOW(), 'VISIT_HOME', 'NOT_AGREED'),
                                                                                                              (3, NOW(), 'CALL_TO_EMPLOYER', 'TRIED_BUT_NOT_INTERACTED');