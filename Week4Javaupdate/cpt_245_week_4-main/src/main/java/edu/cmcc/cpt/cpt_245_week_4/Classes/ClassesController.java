package edu.cmcc.cpt.cpt_245_week_4.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Classes> getAllClasses() {
        String sql = "SELECT * FROM classes;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Classes classes = new Classes();
            classes.setId(rs.getInt("id"));
            classes.setName(rs.getString("name"));
            classes.setInstructor(rs.getString("instructor"));
            classes.setCredits(rs.getInt("credits"));
            return classes;
        });
    }

    @GetMapping("/{id}")
    public Classes getClassById(@PathVariable int id) {
        String sql = "SELECT * FROM classes WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Classes classes = new Classes();
            classes.setId(rs.getInt("id"));
            classes.setName(rs.getString("name"));
            classes.setInstructor(rs.getString("instructor"));
            classes.setCredits(rs.getInt("credits"));
            return classes;
        });
    }

    @PostMapping
    public void createClass(@RequestBody Classes classes) {
        String sql = "INSERT INTO classes (name, instructor, credits) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, classes.getName(), classes.getInstructor(), classes.getCredits());
    }

    @PutMapping("/{id}")
    public void updateClass(@PathVariable int id, @RequestBody Classes classes) {
        String sql = "UPDATE classes SET name = ?, instructor = ?, credits = ? WHERE id = ?";
        jdbcTemplate.update(sql, classes.getName(), classes.getInstructor(), classes.getCredits(), id);
    }

    @DeleteMapping("/{id}")
    public void deleteClass(@PathVariable int id) {
        String sql = "DELETE FROM classes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
