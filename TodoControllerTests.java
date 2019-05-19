/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hello;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTests {

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm;ss'Z'");
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {
//
//        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("Hello, World!"));
//    }
//
//    @Test
//    public void paramGreetingShouldReturnTailoredMessage() throws Exception {
//
//        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
//    }
    @Before
    public void setUp() throws Exception{
        mockMvc= MockMvcBuilders.standaloneSetup(new TodoController()).build();
    }

    @Test
    public void testTodoController() throws Exception{
        RequestBuilder request;
//        Long id=counter.incrementAndGet();
        String createdTime = df.format(new Date());
        String content = "Restful API homework";
        Todo todo=new Todo(1,content,createdTime);
        //1、post提交一个todo
        request=post("/api/tasks/")
                .param("id","1")
                .param("content",content)
                .param("createdTime",createdTime);
        mockMvc.perform(request).andExpect(content().string(equalTo("{\"id\":1,\"content\":\"Restful API homework\",\"createdTime\":\""+createdTime+"\"}")));

        //2、get查询列表todo
        request=get("/api/tasks/");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"content\":\"Restful API homework\",\"createdTime\":\""+createdTime+"\"}]")));
        //3、get一个id为1的todo
        request=get("/api/tasks/1");
        mockMvc.perform(request).andExpect(content().string(equalTo("{\"id\":1,\"content\":\"Restful API homework\",\"createdTime\":\""+createdTime+"\"}")));
        //4、del删除id为1的
        request=delete("/api/tasks/1");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));
    }

}
