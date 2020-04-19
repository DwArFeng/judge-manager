package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.JudgerSupport;
import com.dwarfeng.judge.stack.service.JudgerSupportMaintainService;
import com.dwarfeng.judgemanager.stack.service.JudgerSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class JudgerSupportResponseServiceImpl implements JudgerSupportResponseService {

    @Autowired
    @Qualifier("judgerSupportMaintainService")
    private JudgerSupportMaintainService service;

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public JudgerSupport get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(JudgerSupport judgerSupport) throws ServiceException {
        return service.insertIfNotExists(judgerSupport);
    }

    @Override
    public void update(JudgerSupport judgerSupport) throws ServiceException {
        service.update(judgerSupport);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<JudgerSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<JudgerSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(JudgerSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo);
    }

    @Override
    public PagedData<JudgerSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(JudgerSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo);
    }
}
