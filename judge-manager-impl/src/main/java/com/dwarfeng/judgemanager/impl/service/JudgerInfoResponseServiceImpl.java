package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.JudgerInfo;
import com.dwarfeng.judge.stack.service.JudgerInfoMaintainService;
import com.dwarfeng.judgemanager.stack.service.JudgerInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class JudgerInfoResponseServiceImpl implements JudgerInfoResponseService {

    @Autowired
    @Qualifier("judgerInfoMaintainService")
    private JudgerInfoMaintainService service;

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public JudgerInfo get(LongIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public LongIdKey insert(JudgerInfo judgerInfo) throws ServiceException {
        return service.insert(judgerInfo);
    }

    @Override
    public void update(JudgerInfo judgerInfo) throws ServiceException {
        service.update(judgerInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<JudgerInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<JudgerInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(JudgerInfoMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo);
    }
}
