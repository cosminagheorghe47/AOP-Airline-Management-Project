# AOP-Airline-Management-Project
This is a repository for the AOP Airline Management Project. The project is designed to simulate an airline management system using Advanced-Oriented Programming(AOP) in Java.\
\
From the main menu you can call two other menus: actions on people (customers and employees) and on flights and aircrafts.\
You can:\
➙add, delete, see flights, airplanes, clients, coupons, employees and bookings.\
➙search for a flight and a return for it depending on how many days you want to stay, the destination and the date of departure.\
➙check the profit of a flight, if it has empty seats and add coupons that can be sorted.\
➙see all the flights of a customer as well as of an employee,stored using a list and a map.These lists contain both economy and first class bookings.\
I use two abstract classes: Booking(Economy and FirstClass bookings) and Person(Client and Employee,which has two other subclasses: Pilot and Flight Attendant), and also two exceptions, for too many coupons and too many aircraft. The flights can be sorted by departure date. if two flights have the same departure date, i select the earliest flight,by comparing hours and minutes.\
The price of a ticket is calculated according to the type of booking, but also using discount coupons if they exist for the respective customer. The last 3 customers who reserve a seat on a specific flight receive a coupon.\
\
In the second part, I connected the project to a MySql database, implementing CRUD functions on all the classes in the model. For some classes I also have functions to find an object by id or to return some lists, for example, in the client repository I have a function that returns all the coupons that have the respective client as clientId. Also, when adding or deleting a booking, the number of reserved seats on the flight for which that booking was booked increases or decreases.\
I have created editing options for some parameters for all classes. They are called both by choice and automatically in certain cases. For example, when deleting an aircraft that was assigned to a flight, the editing of the flight is automatically opened to assign a new aircraft.\
I also added an audit functionality, which writes in a csv file the date and time when an action was carried out in the project, as well as the name of the action.
