package com.jsut.classmanage.controller;

import com.jsut.classmanage.common.api.ApiResult;
import com.jsut.classmanage.model.Student;
import com.jsut.classmanage.model.vo.StudentVo;
import com.jsut.classmanage.service.StudentService;
import com.jsut.classmanage.util.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @className StudentController
 **/
@Api(tags = "学生接口")
@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Resource
    private StudentService studentService;


    @ApiOperation(value = "学生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", required = false, example = "1"),
            @ApiImplicitParam(name = "size", required = false, example = "10")
    })
    @GetMapping("/pageList")
    public ApiResult<PageUtils<StudentVo>> pageList(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageUtils<StudentVo> pageUtils = studentService.pageList(pageNo, size);
        return ApiResult.success(pageUtils);
    }
}
