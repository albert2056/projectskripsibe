package com.project.controller;

import com.project.controller.path.ProjectPath;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProjectPath.TRANSACTION)
@Tag(name = "TransactionController", description = "Transaction Service API")
public class TransactionController {
}
