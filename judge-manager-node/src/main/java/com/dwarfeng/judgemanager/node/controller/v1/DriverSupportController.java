package com.dwarfeng.judgemanager.node.controller.v1;

import com.dwarfeng.judge.sdk.bean.entity.FastJsonDriverSupport;
import com.dwarfeng.judge.sdk.bean.entity.WebInputDriverSupport;
import com.dwarfeng.judge.stack.bean.entity.DriverSupport;
import com.dwarfeng.judgemanager.stack.service.DriverSupportResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.http.BindingCheck;
import com.dwarfeng.subgrade.sdk.validation.group.Insert;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 驱动器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class DriverSupportController {

    @Autowired
    private DriverSupportResponseService service;
    @Autowired
    private ServiceExceptionMapper sem;

    @Autowired
    private BeanTransformer<DriverSupport, FastJsonDriverSupport> beanTransformer;

    @GetMapping("/driver-support/{id}/exists")
    @BehaviorAnalyse
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/driver-support/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<FastJsonDriverSupport> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            DriverSupport driverSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonDriverSupport.of(driverSupport)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonDriverSupport.class, e, sem));
        }
    }

    @PostMapping("/driver-support")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputDriverSupport driverSupport, BindingResult bindingResult) {
        try {
            StringIdKey insert = service.insert(WebInputDriverSupport.toStackBean(driverSupport));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/driver-support")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputDriverSupport driverSupport, BindingResult bindingResult) {
        try {
            service.update(WebInputDriverSupport.toStackBean(driverSupport));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/driver-support/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/driver-support/all")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDriverSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DriverSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonDriverSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/driver-support/id-like")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDriverSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DriverSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonDriverSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/driver-support/label-like")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonDriverSupport>> labelLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<DriverSupport> all = service.labelLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonDriverSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
