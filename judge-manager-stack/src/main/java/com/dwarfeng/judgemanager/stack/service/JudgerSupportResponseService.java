package com.dwarfeng.judgemanager.stack.service;

import com.dwarfeng.judge.stack.bean.entity.JudgerSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 判断器支持响应服务。
 *
 * @author DwArFeng
 * @since alpha-0.0.1
 */
public interface JudgerSupportResponseService extends Service {

    /**
     * 获取服务中是否有指定的判断器支持。
     *
     * @param key 指定的判断器支持的键。
     * @return 服务中是否有指定的判断器支持。
     * @throws ServiceException 服务异常。
     */
    boolean exists(StringIdKey key) throws ServiceException;

    /**
     * 获取服务中指定的主键对应的判断器支持。
     *
     * @param key 指定的主键。
     * @return 指定的主键对应的判断器支持。
     * @throws ServiceException 服务异常。
     */
    JudgerSupport get(StringIdKey key) throws ServiceException;

    /**
     * 向服务中插入指定的判断器支持。
     *
     * @param judgerSupport 指定的判断器支持。
     * @return 插入后分配的新主键。
     * @throws ServiceException 服务异常。
     */
    StringIdKey insert(JudgerSupport judgerSupport) throws ServiceException;

    /**
     * 向服务中更新指定的判断器支持。
     *
     * @param judgerSupport 指定的判断器支持。
     * @throws ServiceException 服务异常。
     */
    void update(JudgerSupport judgerSupport) throws ServiceException;

    /**
     * 从服务中删除指定的主键对应的判断器支持。
     *
     * @param key 指定的主键。
     * @throws ServiceException 服务异常。
     */
    void delete(StringIdKey key) throws ServiceException;

    /**
     * 获取所有判断器支持。
     *
     * @param pagingInfo 分页信息。
     * @return 所有判断器支持构成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<JudgerSupport> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 查找id匹配指定文本的所有判断器支持。
     *
     * @param pattern    指定的匹配文本。
     * @param pagingInfo 分页信息。
     * @return 所有符合要求的判断器支持组成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<JudgerSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    /**
     * 查找标签匹配指定文本的所有判断器支持。
     *
     * @param pattern    指定的匹配文本。
     * @param pagingInfo 分页信息。
     * @return 所有符合要求的判断器支持组成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<JudgerSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
