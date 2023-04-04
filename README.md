# AOP-Airline-Management-Project
This is my first Java project in which i implemented classes and services used for managing an airline. 

From the main menu you can call two other menus: actions on people (customers and employees) and on flights and aircraft.\
You can:\
➙add, delete, see flights, airplanes, clients, coupons, employees and bookings.\
➙search for a flight and a return for it depending on how many days you want to stay, the destination and the date of departure.\
➙check the profit of a flight, if it has empty seats and add coupons that can be sorted.\
➙see all the flights of a customer as well as of an employee,stored using a list and a map.These lists contain both economy and first class bookings.\
I use two abstract classes: Booking(Economy and FirstClass bookings) and Person(Client and Employee,which has two other subclasses: Pilot and Flight Attendant), and also two exceptions, for too many coupons and too many aircraft. \
The price of a ticket is calculated according to the type of booking, but also using discount coupons if they exist for the respective customer. The last 3 customers who reserve a seat on a specific flight receive a coupon.
