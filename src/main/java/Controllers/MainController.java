package Controllers;

import db.Seeds;

public class MainController {

    public static void main(String[] args) {

        Seeds.seedData();
        ManagersController managersController = new ManagersController();
        EmployeesController employeesController = new EmployeesController();

    }
}
