package Controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;


public class ManagersController {

    public ManagersController(){
        setupEndpoints();
    }


    private void setupEndpoints(){
        get("/managers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/managers/index.vtl");
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        },new VelocityTemplateEngine());



        get("/managers/new", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());



        post("/managers", (req, res) ->{
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            Integer salary = Integer.parseInt(req.queryParams("salary"));

            int departmentId = Integer.parseInt(req.queryParams("departmentId"));
            Department department = DBHelper.find(departmentId, Department.class);

            Integer budget = Integer.parseInt(req.queryParams("budget"));
            Manager manager= new Manager(firstName, lastName, salary, department, budget);
            DBHelper.save(manager);
            res.redirect("/managers");
            return null; });



//        get("/managers/:id/edit", (req, res) -> {
//            HashMap<String, Object> model = new HashMap<>();
//            int managerId = Integer.parseInt(req.queryParams(":id"));
//            Manager manager = DBHelper.find(managerId, Manager.class);
//
//
//            model.put("manager", manager);
//            model.put("template", "templates/managers/edit.vtl");
//
//            List<Department> departments = DBHelper.getAll(Department.class);
//            model.put("departments", departments);
//
//            return  new ModelAndView( model,"templates/layout.vtl");
//        }, new VelocityTemplateEngine());

//
//
//        post("/managers/:id", (req, res) -> {
//        int managerId = Integer.parseInt(req.queryParams(":id"));
//        Manager manager = DBHelper.find(managerId, Manager.class);
//        String firstName = req.queryParams("firstName");
//        String lastName = req.queryParams("lastName");
//        Integer salary = Integer.parseInt(req.queryParams("salary"));
//
//        int departmentId = Integer.parseInt(req.queryParams("departmentId"));
//        Department department = DBHelper.find(departmentId, Department.class);
//
//        Integer budget = Integer.parseInt(req.queryParams("budget"));
//        DBHelper.save(manager);
//        res.redirect("/managers");
//        return null; });
//
//        post("/managers/:id/destroy", (req, res) ->){
//        int managerId = Integer.parseInt(req.queryParams(":id"));
////      Manager manager = DBHelper.find(managerId, Manager.class);
//        DBHelper.delete(Manager.class);
//        res.redirect("/managers");
//        return null; });

    }
}
