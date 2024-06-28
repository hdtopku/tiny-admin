package com.tiny.core.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxh at 2024-06-07 13:24:28
 */
@SuppressWarnings("all")
public class BaseController<S extends IService<E>, E> {
    @Autowired
    public S baseService;

    @Operation(summary = "增")
    @PostMapping("/insert")
    public Result<String> insert(@RequestBody E entity) {
        baseService.save(entity);
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>(){{
                add(1);
                add(2);
        }});
        return Result.success("添加成功");
    }

    @Operation(summary = "删")
    @PostMapping("/deleteByIds")
    public Result<String> delete(@RequestBody List<String> ids) {
        baseService.removeByIds(ids);
        return Result.success("删除成功");
    }

    @Operation(summary = "查")
    @GetMapping("/getById")
    public Result<E> getById(@RequestParam String id) {
        return Result.success(baseService.getById(id));
    }

    @Operation(summary = "存或改")
    @PostMapping("/saveOrUpdate")
    public Result<String> save(@RequestBody E entity) {
        baseService.saveOrUpdate(entity);
        return Result.success("操作成功");
    }

    @Operation(summary = "list查")
    @PostMapping("/list")
    public Result list(@RequestBody E entity) {
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(entity);
        List<E> list = baseService.list(queryWrapper);
        return Result.success(list);
    }

    @Operation(summary = "page查")
//    @PostMapping("/page")
    public Result<IPage<E>> page(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                       @RequestBody(required = false) E entity) {
        //限制条件
        if (pageNum < 1) {
            pageNum = 1;
        }

        if (pageSize > 100) {
            pageSize = 100;
        }
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(entity);
        IPage<E> page = new Page<>(pageNum, pageSize);
        IPage<E> result = baseService.page(page, queryWrapper);
        return Result.success(result);
    }

    @Operation(summary = "获取数量")
    @PostMapping("/count")
    public Result count(@RequestBody E entity) {
        QueryWrapper<E> queryWrapper = ApprenticeUtil.getQueryWrapper(entity);
        long count = baseService.count(queryWrapper);
        return Result.success(count);
    }
}

