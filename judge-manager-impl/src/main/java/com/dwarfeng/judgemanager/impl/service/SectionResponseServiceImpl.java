package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.Section;
import com.dwarfeng.judge.stack.service.SectionMaintainService;
import com.dwarfeng.judgemanager.stack.service.SectionResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class SectionResponseServiceImpl implements SectionResponseService {

    @Autowired
    @Qualifier("sectionMaintainService")
    private SectionMaintainService service;

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public Section get(LongIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public LongIdKey insert(Section section) throws ServiceException {
        return service.insert(section);
    }

    @Override
    public void update(Section section) throws ServiceException {
        service.update(section);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<Section> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<Section> nameLike(String nameLike, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(SectionMaintainService.NAME_LIKE, new Object[]{nameLike}, pagingInfo);
    }
}
