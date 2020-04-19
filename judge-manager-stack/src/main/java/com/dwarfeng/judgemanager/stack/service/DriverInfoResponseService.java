package com.dwarfeng.judgemanager.stack.service;

import com.dwarfeng.judge.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动器信息响应服务。
 *
 * @author DwArFeng
 * @since alpha-0.0.1
 */
public interface DriverInfoResponseService extends Service {

    /**
     * 获取服务中是否有指定的驱动器信息。
     *
     * @param key 指定的驱动器信息的键。
     * @return 服务中是否有指定的驱动器信息。
     * @throws ServiceException 服务异常。
     */
    boolean exists(LongIdKey key) throws ServiceException;

    /**
     * 获取服务中指定的主键对应的驱动器信息。
     *
     * @param key 指定的主键。
     * @return 指定的主键对应的驱动器信息。
     * @throws ServiceException 服务异常。
     */
    DriverInfo get(LongIdKey key) throws ServiceException;

    /**
     * 向服务中插入指定的驱动器信息。
     *
     * @param driverInfo 指定的驱动器信息。
     * @return 插入后分配的新主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey insert(DriverInfo driverInfo) throws ServiceException;

    /**
     * 向服务中更新指定的驱动器信息。
     *
     * @param driverInfo 指定的驱动器信息。
     * @throws ServiceException 服务异常。
     */
    void update(DriverInfo driverInfo) throws ServiceException;

    /**
     * 从服务中删除指定的主键对应的驱动器信息。
     *
     * @param key 指定的主键。
     * @throws ServiceException 服务异常。
     */
    void delete(LongIdKey key) throws ServiceException;

    /**
     * 从服务中获取所有的驱动器信息。
     *
     * @param pagingInfo 分页信息。
     * @return 所有的驱动器信息。
     * @throws ServiceException 服务异常。
     */
    PagedData<DriverInfo> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 从服务中获取指定部件对应的所有驱动器信息。
     *
     * @param sectionKey 指定的部件对应的主键。
     * @param pagingInfo 分页信息。
     * @return 指定的部件对应的驱动器信息。
     * @throws ServiceException 服务异常。
     */
    PagedData<DriverInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;
}
