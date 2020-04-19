package com.dwarfeng.judgemanager.stack.service;

import com.dwarfeng.judge.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since alpha-0.0.1
 */
public interface DriverSupportResponseService extends Service {

    /**
     * 获取服务中是否有指定的驱动器支持。
     *
     * @param key 指定的驱动器支持的键。
     * @return 服务中是否有指定的驱动器支持。
     * @throws ServiceException 服务异常。
     */
    boolean exists(StringIdKey key) throws ServiceException;

    /**
     * 获取服务中指定的主键对应的驱动器支持。
     *
     * @param key 指定的主键。
     * @return 指定的主键对应的驱动器支持。
     * @throws ServiceException 服务异常。
     */
    DriverSupport get(StringIdKey key) throws ServiceException;

    /**
     * 向服务中插入指定的驱动器支持。
     *
     * @param driverSupport 指定的驱动器支持。
     * @return 插入后分配的新主键。
     * @throws ServiceException 服务异常。
     */
    StringIdKey insert(DriverSupport driverSupport) throws ServiceException;

    /**
     * 向服务中更新指定的驱动器支持。
     *
     * @param driverSupport 指定的驱动器支持。
     * @throws ServiceException 服务异常。
     */
    void update(DriverSupport driverSupport) throws ServiceException;

    /**
     * 从服务中删除指定的主键对应的驱动器支持。
     *
     * @param key 指定的主键。
     * @throws ServiceException 服务异常。
     */
    void delete(StringIdKey key) throws ServiceException;

    /**
     * 获取所有驱动器支持。
     *
     * @param pagingInfo 分页信息。
     * @return 所有驱动器支持构成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<DriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 查找id匹配指定文本的所有驱动器支持。
     *
     * @param pattern    指定的匹配文本。
     * @param pagingInfo 分页信息。
     * @return 所有符合要求的驱动器支持组成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<DriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    /**
     * 查找标签匹配指定文本的所有驱动器支持。
     *
     * @param pattern    指定的匹配文本。
     * @param pagingInfo 分页信息。
     * @return 所有符合要求的驱动器支持组成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<DriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
