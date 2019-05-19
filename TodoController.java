package hello;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@RestController
public class TodoController {

    @Autowired
    public static Map<Long, Todo> TodoMap = Collections.synchronizedMap(new HashMap<Long, Todo>());

    //添加一个id
    @RequestMapping(value = "/api/tasks",method = RequestMethod.POST,produces = "application/json")
    public Todo newToDo(@ModelAttribute Todo todo) {
        TodoMap.put(todo.getId(),todo);
        return todo;
    }
    //删除某一个id
    @RequestMapping(value = "/api/tasks/{id}",method = RequestMethod.DELETE)
    public String deleteToDo(@PathVariable Long id) {
        Todo todo = TodoMap.remove(id);
        return "success";
    }

    //查询所有的todo
    @RequestMapping(value = "/api/tasks",method = RequestMethod.GET)
    public List<Todo> allToDo() {
        List<Todo> todoList = new ArrayList<Todo>(TodoMap.values());
        return todoList;
    }

    //查询某一个id
    @RequestMapping(value = "/api/tasks/{id}",method = RequestMethod.GET)
    public Todo getidToDo(@PathVariable Long id) {
        Todo todo = TodoMap.get(id);
        return todo;
    }

}
