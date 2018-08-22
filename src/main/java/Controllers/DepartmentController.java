package Controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentController {

    public DepartmentController(){
        setupEndpoints();
    }

    private void setupEndpoints(){

        get("/departments", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/index.vtl");
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        },new VelocityTemplateEngine());


        get("/departments/new", (res, req) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        post("/departments", (req, res) -> {
            String title = req.queryParams("title");
            Department department = new Department(title);
            DBHelper.save(department);
            res.redirect("/departments");
            return null;
        },new VelocityTemplateEngine());

//       works
        get("/departments/:id", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            int departmentId = parseInt(req.params(":id"));
            Department department = DBHelper.find(departmentId, Department.class);
            model.put("department", department);
            model.put("template", "templates/departments/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//      works
        get("/departments/:id/edit", (req, res) -> {
            HashMap<String, Object> model = new HashMap<>();
            Department department = DBHelper.find(parseInt(req.params(":id")), Department.class);
            model.put("template", "templates/departments/edit.vtl");
            model.put("department", department);
            return new ModelAndView(model, "templates/layout.vtl");
            }, new VelocityTemplateEngine());


        post("/departments/:id", (req, res) -> {
//            same as post new + set & save
            String title = req.queryParams("title");
            Department departmentToUpdate = DBHelper.find(parseInt(req.params(":id")), Department.class);
            departmentToUpdate.setTitle(title);
            DBHelper.save(departmentToUpdate);
            res.redirect("/departments");
            return null;
        });


        post("/department/:id/destroy", (req, res) -> {
            int departmentId = Integer.parseInt(req.params(":id"));
            Department departmentToDelete = DBHelper.find(departmentId, Department.class);
                DBHelper.delete(departmentToDelete);
                res.redirect("/departments");
                return null;
            });
    }


}
