package com.maor.studyflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetDashboardSummary() throws Exception {
        mockMvc.perform(get("/api/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCourses").value(3))
                .andExpect(jsonPath("$.totalTasks").value(4))
                .andExpect(jsonPath("$.todoTasks").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.inProgressTasks").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.openTasks").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.totalEstimatedHoursForOpenTasks").exists());
    }

    @Test
    void shouldGetCourseProgressDashboard() throws Exception {
        mockMvc.perform(get("/api/dashboard/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].courseId").exists())
                .andExpect(jsonPath("$[0].courseName").exists())
                .andExpect(jsonPath("$[0].totalTasks").exists())
                .andExpect(jsonPath("$[0].todoTasks").exists())
                .andExpect(jsonPath("$[0].inProgressTasks").exists())
                .andExpect(jsonPath("$[0].doneTasks").exists())
                .andExpect(jsonPath("$[0].openTasks").exists())
                .andExpect(jsonPath("$[0].completionPercentage").exists())
                .andExpect(jsonPath("$[0].remainingEstimatedHours").exists());
    }
}