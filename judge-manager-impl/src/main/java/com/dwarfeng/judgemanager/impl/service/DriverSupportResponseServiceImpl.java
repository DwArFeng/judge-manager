package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.DriverSupport;
import com.dwarfeng.judge.stack.service.DriverSupportMaintainService;
import com.dwarfeng.judgemanager.stack.service.DriverSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DriverSupportResponseServiceImpl implements DriverSupportResponseService {

    @Autowired
    @Qualifier("driverSupportMaintainService")
    private DriverSupportMaintainService service;

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public DriverSupport get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(DriverSupport driverSupport) throws ServiceException {
        return service.insertIfNotExists(driverSupport);
    }

    @Override
    public void update(DriverSupport driverSupport) throws ServiceException {
        service.update(driverSupport);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<DriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<DriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(DriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo);
    }

    @Override
    public PagedData<DriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(DriverSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo);
    }
}
