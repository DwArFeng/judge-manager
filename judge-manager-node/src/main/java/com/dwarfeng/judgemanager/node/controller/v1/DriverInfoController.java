package com.dwarfeng.judgemanager.node.controller.v1;

import com.dwarfeng.judge.sdk.bean.entity.JSFixedFastJsonDriverInfo;
import com.dwarfeng.judge.sdk.bean.entity.WebInputDriverInfo;
import com.dwarfeng.judge.stack.bean.entity.DriverInfo;
import com.dwarfeng.judgemanager.stack.service.DriverInfoResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 触发器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class DriverInfoController {

    @Autowired
    private DriverInfoResponseService service;
    @Autowired
    private ServiceExceptionMapper sem;

    @Autowired
    private BeanTransformer<DriverInfo, JSFixedFastJsonDriverInfo> beanTransformer;

    @GetMapping("/driver-info/{id}/exists")
    @BehaviorAnalyse
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/driver-info/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonDriverInfo> get(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            DriverInfo driverInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonDriverInfo.of(driverInfo)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonDriverInfo.class, e, sem));
        }
    }

    @PostMapping("/driver-info")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputDriverInfo driverInfo, BindingResult bindingResult) {
        try {
            LongIdKey insert = service.insert(WebInputDriverInfo.toStackBean(driverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLongIdKey.class, e, sem));
        }
    }

    @PatchMapping("/driver-info")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDriverInfo driverInfo, BindingResult bindingResult) {
        try {
            service.update(WebInputDriverInfo.toStackBean(driverInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/driver-info/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/driver-info/all")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDriverInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DriverInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/section/{sectionId}/driver-infos")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonDriverInfo>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") long sectionId, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DriverInfo> all = service.childForSection(new LongIdKey(sectionId), new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonDriverInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
