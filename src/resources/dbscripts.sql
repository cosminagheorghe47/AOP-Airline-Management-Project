
USE `myBank`;

CREATE TABLE `aircrafts` (
                                `idAircraft` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(100) NOT NULL,
                                `nrOfSeatsEconomy` int DEFAULT NULL,
                                `nrOfSeatsFirstClass` int DEFAULT NULL,
                                `maxSpeed` int NOT NULL,
                                `weight` int NOT NULL,
                                `wingSpan` int NOT NULL,
                                PRIMARY KEY (`idAircraft`));



DELIMITER $$
CREATE TABLE `cities` (
                             `idCity` int(11) NOT NULL ,
                             `cityName` varchar(100) NOT NULL,
                             PRIMARY KEY (`idCity`));


DELIMITER ;
CREATE TABLE flightAttendants (
                                   id INT PRIMARY KEY,
                                   lastName VARCHAR(255) NOT NULL,
                                   firstName VARCHAR(255) NOT NULL,
                                   gender VARCHAR(255) NOT NULL,
                                   age INT NOT NULL,
                                   nationality VARCHAR(255) NOT NULL,
                                   hireDate VARCHAR(255) NOT NULL,
                                   salary INT NOT NULL,
                                   nrOfFlights INT NOT NULL
);

CREATE TABLE pilots (
                        id INT PRIMARY KEY,
                        lastName VARCHAR(255) NOT NULL,
                        firstName VARCHAR(255) NOT NULL,
                        gender VARCHAR(255) NOT NULL,
                        age INT NOT NULL,
                        nationality VARCHAR(255) NOT NULL,
                        hireDate VARCHAR(255) NOT NULL,
                        salary INT NOT NULL,
                        yearsOfExperience INT NOT NULL
);
CREATE TABLE clients (
                        id INT PRIMARY KEY,
                        lastName VARCHAR(255) NOT NULL,
                        firstName VARCHAR(255) NOT NULL,
                        gender VARCHAR(255) NOT NULL,
                        age INT NOT NULL,
                        nationality VARCHAR(255) NOT NULL
);

select * from aircrafts;
select * from cities;
select * from flightAttendants;
select * from pilots;
select * from flights;
select * from FlightCity;
#truncate flights;
#drop table flights;
#drop table FlightCity;
CREATE TABLE pilots (
                        id INT PRIMARY KEY,
                        lastName VARCHAR(255) NOT NULL,
                        firstName VARCHAR(255) NOT NULL,
                        gender VARCHAR(255) NOT NULL,
                        age INT NOT NULL,
                        nationality VARCHAR(255) NOT NULL,
                        hireDate VARCHAR(255) NOT NULL,
                        salary INT NOT NULL,
                        yearsOfExperience INT NOT NULL
);
CREATE TABLE IF NOT EXISTS flights (
                                       idFlight INT PRIMARY KEY,
                                       aircraftId INT,
                                       departureTime VARCHAR(10),
                                       departureDate VARCHAR(10),
                                       departureCityId INT,
                                       arrivalTime VARCHAR(10),
                                       arrivalDate VARCHAR(10),
                                       arrivalCityId INT,
                                       distance INT,
                                       soldSeatsEconomy INT,
                                       soldSeatsFirstClass INT,
                                       FOREIGN KEY (aircraftId) REFERENCES aircrafts(idAircraft),
                                       FOREIGN KEY (departureCityId) REFERENCES cities(idCity),
                                       FOREIGN KEY (arrivalCityId) REFERENCES cities(idCity)
);
CREATE TABLE coupons (
                         idCoupon INT PRIMARY KEY,
                         discountPercentage INT,
                         expirationDate VARCHAR(20),
                         flightUsedON INT,
                         FOREIGN KEY (flightUsedON) REFERENCES flights(idFlight)
);
CREATE TABLE FlightCity (
                            idFlight INT,
                            idCity INT,
                            PRIMARY KEY (idFlight, idCity),
                            FOREIGN KEY (idFlight) REFERENCES flights(idFlight) ON DELETE CASCADE,
                            FOREIGN KEY (idCity) REFERENCES cities(idCity) ON DELETE CASCADE
);


select * from aircrafts;
select * from cities;
select * from flightAttendants;
select * from pilots;
select * from FlightCity;
select * from clients;
select * from coupons;
select * from flights;
select * from economybookings;
select * from firstclassbookings;
#truncate economybookings;
#truncate flights;
#drop table flights;
#drop table FlightCity;
#drop table coupons;
CREATE TABLE pilots (
                        id INT PRIMARY KEY,
                        lastName VARCHAR(255) NOT NULL,
                        firstName VARCHAR(255) NOT NULL,
                        gender VARCHAR(255) NOT NULL,
                        age INT NOT NULL,
                        nationality VARCHAR(255) NOT NULL,
                        hireDate VARCHAR(255) NOT NULL,
                        salary INT NOT NULL,
                        yearsOfExperience INT NOT NULL
);
CREATE TABLE IF NOT EXISTS flights (
                                       idFlight INT PRIMARY KEY,
                                       aircraftId INT,
                                       departureTime VARCHAR(10),
                                       departureDate VARCHAR(10),
                                       departureCityId INT,
                                       arrivalTime VARCHAR(10),
                                       arrivalDate VARCHAR(10),
                                       arrivalCityId INT,
                                       distance INT,
                                       soldSeatsEconomy INT,
                                       soldSeatsFirstClass INT,
                                       FOREIGN KEY (aircraftId) REFERENCES aircrafts(idAircraft),
                                       FOREIGN KEY (departureCityId) REFERENCES cities(idCity),
                                       FOREIGN KEY (arrivalCityId) REFERENCES cities(idCity)
);
CREATE TABLE coupons (
                         idCoupon INT PRIMARY KEY,
                         idClient INT,
                         discountPercentage INT,
                         expirationDate VARCHAR(20),
                         flightUsedONId INT DEFAULT NULL,
                         FOREIGN KEY (flightUsedONId) REFERENCES flights(idFlight) ON DELETE CASCADE,
                         FOREIGN KEY (idClient) REFERENCES clients(id) ON DELETE CASCADE
);
CREATE TABLE FlightCity (
                            idFlight INT,
                            idCity INT,
                            PRIMARY KEY (idFlight, idCity),
                            FOREIGN KEY (idFlight) REFERENCES flights(idFlight) ON DELETE CASCADE,
                            FOREIGN KEY (idCity) REFERENCES cities(idCity) ON DELETE CASCADE
);
CREATE TABLE clients (
                         id INT PRIMARY KEY,
                         lastName VARCHAR(255) NOT NULL,
                         firstName VARCHAR(255) NOT NULL,
                         gender VARCHAR(255) NOT NULL,
                         age INT NOT NULL,
                         nationality VARCHAR(255) NOT NULL
);
CREATE TABLE economybookings (
                                 idBooking INT PRIMARY KEY,
                                 flightId INT,
                                 clientId INT,
                                 seat INT,
                                 row INT,
                                 nrOfBaggages INT,
                                 hasPriority BOOLEAN,
                                 FOREIGN KEY (flightId) REFERENCES flights (idFlight) ON DELETE CASCADE,
                                 FOREIGN KEY (clientId) REFERENCES clients (id) ON DELETE CASCADE
);
CREATE TABLE firstclassbookings (
                                    idBooking INT PRIMARY KEY,
                                    flightId INT,
                                    clientId INT,
                                    seat INT,
                                    row INT,
                                    nrOfBaggages INT,
                                    isVegetarian BOOLEAN,
                                    FOREIGN KEY (flightId) REFERENCES flights (idFlight) ON DELETE CASCADE,
                                    FOREIGN KEY (clientId) REFERENCES clients (id) ON DELETE CASCADE
);

