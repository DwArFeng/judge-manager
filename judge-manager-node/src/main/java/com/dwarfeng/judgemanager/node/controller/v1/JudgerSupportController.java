package com.dwarfeng.judgemanager.node.controller.v1;

import com.dwarfeng.judge.sdk.bean.entity.FastJsonJudgerSupport;
import com.dwarfeng.judge.sdk.bean.entity.WebInputJudgerSupport;
import com.dwarfeng.judge.stack.bean.entity.JudgerSupport;
import com.dwarfeng.judgemanager.stack.service.JudgerSupportResponseService;
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
 * 触发器支持控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class JudgerSupportController {

    @Autowired
    private JudgerSupportResponseService service;
    @Autowired
    private ServiceExceptionMapper sem;

    @Autowired
    private BeanTransformer<JudgerSupport, FastJsonJudgerSupport> beanTransformer;

    @GetMapping("/judger-support/{id}/exists")
    @BehaviorAnalyse
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/judger-support/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<FastJsonJudgerSupport> get(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            JudgerSupport judgerSupport = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonJudgerSupport.of(judgerSupport)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonJudgerSupport.class, e, sem));
        }
    }

    @PostMapping("/judger-support")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputJudgerSupport judgerSupport, BindingResult bindingResult) {
        try {
            StringIdKey insert = service.insert(WebInputJudgerSupport.toStackBean(judgerSupport));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(FastJsonStringIdKey.class, e, sem));
        }
    }

    @PatchMapping("/judger-support")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputJudgerSupport judgerSupport, BindingResult bindingResult) {
        try {
            service.update(WebInputJudgerSupport.toStackBean(judgerSupport));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/judger-support/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") String id) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/judger-support/all")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonJudgerSupport>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<JudgerSupport> all = service.all(new PagingInfo(page, rows));
            PagedData<FastJsonJudgerSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/judger-support/id-like")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonJudgerSupport>> idLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<JudgerSupport> all = service.idLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonJudgerSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/judger-support/label-like")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonJudgerSupport>> labelLike(
            HttpServletRequest request,
            @RequestParam("pattern") String pattern, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<JudgerSupport> all = service.labelLike(pattern, new PagingInfo(page, rows));
            PagedData<FastJsonJudgerSupport> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
