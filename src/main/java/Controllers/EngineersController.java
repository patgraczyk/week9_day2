package Controllers;

import db.DBHelper;
import models.Department;
import models.Engineer;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static spark.Spark.get;
import static spark.Spark.post;

public class EngineersController {


    public EngineersController() {
        setupEndpoints();
    }

    private void setupEndpoints() {

        get("/engineers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/engineers/index.vtl");
            List<Engineer> engineers = DBHelper.getAll(Engineer.class);
            model.put("engineers", engineers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/engineers/new", (res, req) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            model.put("template", "templates/engineers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/engineers", (req, res) ->{
            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            Integer salary = Integer.parseInt(req.queryParams("salary"));

            int departmentId = Integer.parseInt(req.queryParams("departmentId"));
            Department department = DBHelper.find(departmentId, Department.class);

            Engineer engineer= new Engineer(firstName, lastName, salary, department);
            DBHelper.save(engineer);
            res.redirect("/engineers");
            return null; });

//show by id
        get("/engineers/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int engineerId = parseInt(req.params(":id"));
            Engineer engineer = DBHelper.find(engineerId, Engineer.class);
            model.put("engineer", engineer);
            model.put("template", "templates/engineers/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//  get edit form /works

        get("/engineers/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            Engineer engineer = DBHelper.find(parseInt(req.params(":id")), Engineer.class);
            model.put("engineer", engineer);
            model.put("template", "templates/engineers/edit.vtl");


            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

    }
}
