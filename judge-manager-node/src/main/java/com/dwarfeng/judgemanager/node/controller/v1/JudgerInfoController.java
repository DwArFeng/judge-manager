package com.dwarfeng.judgemanager.node.controller.v1;

import com.dwarfeng.judge.sdk.bean.entity.JSFixedFastJsonJudgerInfo;
import com.dwarfeng.judge.sdk.bean.entity.WebInputJudgerInfo;
import com.dwarfeng.judge.stack.bean.entity.JudgerInfo;
import com.dwarfeng.judgemanager.stack.service.JudgerInfoResponseService;
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
 * 判断器信息控制器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class JudgerInfoController {

    @Autowired
    private JudgerInfoResponseService service;
    @Autowired
    private ServiceExceptionMapper sem;

    @Autowired
    private BeanTransformer<JudgerInfo, JSFixedFastJsonJudgerInfo> beanTransformer;

    @GetMapping("/judger-info/{id}/exists")
    @BehaviorAnalyse
    public FastJsonResponseData<Boolean> exists(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Boolean.class, e, sem));
        }
    }

    @GetMapping("/judger-info/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonJudgerInfo> get(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            JudgerInfo judgerInfo = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonJudgerInfo.of(judgerInfo)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonJudgerInfo.class, e, sem));
        }
    }

    @PostMapping("/judger-info")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody @Validated(Insert.class) WebInputJudgerInfo judgerInfo, BindingResult bindingResult) {
        try {
            LongIdKey insert = service.insert(WebInputJudgerInfo.toStackBean(judgerInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(insert)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonLongIdKey.class, e, sem));
        }
    }

    @PatchMapping("/judger-info")
    @BehaviorAnalyse
    @BindingCheck
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody @Validated WebInputJudgerInfo judgerInfo, BindingResult bindingResult) {
        try {
            service.update(WebInputJudgerInfo.toStackBean(judgerInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @DeleteMapping("/judger-info/{id}")
    @BehaviorAnalyse
    public FastJsonResponseData<Object> delete(HttpServletRequest request, @PathVariable("id") long id) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(Object.class, e, sem));
        }
    }

    @GetMapping("/judger-info/all")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonJudgerInfo>> all(
            HttpServletRequest request, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<JudgerInfo> all = service.all(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonJudgerInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }

    @GetMapping("/section/{sectionId}/judger-infos")
    @BehaviorAnalyse
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonJudgerInfo>> childForSection(
            HttpServletRequest request,
            @PathVariable("sectionId") long sectionId, @RequestParam("page") int page, @RequestParam("rows") int rows) {
        try {
            PagedData<JudgerInfo> all = service.childForSection(new LongIdKey(sectionId), new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonJudgerInfo> transform = PagingUtil.transform(all, beanTransformer);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (Exception e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(JSFixedFastJsonPagedData.class, e, sem));
        }
    }
}
