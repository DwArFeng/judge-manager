package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.DriverInfo;
import com.dwarfeng.judge.stack.service.DriverInfoMaintainService;
import com.dwarfeng.judgemanager.stack.service.DriverInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DriverInfoResponseServiceImpl implements DriverInfoResponseService {

    @Autowired
    @Qualifier("driverInfoMaintainService")
    private DriverInfoMaintainService service;

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public DriverInfo get(LongIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public LongIdKey insert(DriverInfo driverInfo) throws ServiceException {
        return service.insert(driverInfo);
    }

    @Override
    public void update(DriverInfo driverInfo) throws ServiceException {
        service.update(driverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<DriverInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<DriverInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(DriverInfoMaintainService.CHILD_FOR_SECTION, new Object[]{sectionKey}, pagingInfo);
    }
}
