package Controllers;

import db.DBHelper;
import db.Seeds;
import models.Department;
import models.Engineer;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class MainController {

    public static void main(String[] args) {
        staticFileLocation("/public");


        Seeds.seedData();
        ManagersController managersController = new ManagersController();
        EmployeesController employeesController = new EmployeesController();
        DepartmentController departmentController = new DepartmentController();
        EngineersController engineersController = new EngineersController();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }
}
