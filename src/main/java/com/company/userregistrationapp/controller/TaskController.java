package com.company.userregistrationapp.controller;

import com.company.userregistrationapp.criteria.PageCriteria;
import com.company.userregistrationapp.criteria.SortingCriteria;
import com.company.userregistrationapp.dto.request.TaskSaveRequest;
import com.company.userregistrationapp.dto.request.TaskUpdateRequest;
import com.company.userregistrationapp.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TaskController {

    TaskService taskService;


    @GetMapping
    public ResponseEntity<?> getTasks(PageCriteria pageCriteria, SortingCriteria sortingCriteria){
        return ResponseEntity.ok(taskService.getTasks(pageCriteria,sortingCriteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@Valid @RequestBody TaskSaveRequest request){
        return ResponseEntity.ok(taskService.saveTask(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTask(@RequestBody TaskUpdateRequest request){
        return ResponseEntity.ok(taskService.updateTask(request));
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateTaskWithStatus(@RequestBody TaskUpdateRequest request){
        return ResponseEntity.ok(taskService.updateTaskWithStatus(request));
    }
}
