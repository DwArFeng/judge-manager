package com.dwarfeng.judgemanager.stack.service;

import com.dwarfeng.judge.stack.bean.entity.Section;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 部件响应服务。
 *
 * @author DwArFeng
 * @since alpha-0.0.1
 */
public interface SectionResponseService extends Service {

    /**
     * 获取服务中是否有指定的部件。
     *
     * @param key 指定的部件的键。
     * @return 服务中是否有指定的部件。
     * @throws ServiceException 服务异常。
     */
    boolean exists(LongIdKey key) throws ServiceException;

    /**
     * 获取服务中指定的主键对应的部件。
     *
     * @param key 指定的主键。
     * @return 指定的主键对应的部件。
     * @throws ServiceException 服务异常。
     */
    Section get(LongIdKey key) throws ServiceException;

    /**
     * 向服务中插入指定的部件。
     *
     * @param section 指定的部件。
     * @return 插入后分配的新主键。
     * @throws ServiceException 服务异常。
     */
    LongIdKey insert(Section section) throws ServiceException;

    /**
     * 向服务中更新指定的部件。
     *
     * @param section 指定的部件。
     * @throws ServiceException 服务异常。
     */
    void update(Section section) throws ServiceException;

    /**
     * 从服务中删除指定的主键对应的部件。
     *
     * @param key 指定的主键。
     * @throws ServiceException 服务异常。
     */
    void delete(LongIdKey key) throws ServiceException;

    /**
     * 获取所有部件。
     *
     * @param pagingInfo 分页信息。
     * @return 所有部件构成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<Section> all(PagingInfo pagingInfo) throws ServiceException;

    /**
     * 获取具有匹配名称的部件。
     *
     * @param nameLike   匹配的名称。
     * @param pagingInfo 分页信息。
     * @return 所有符合要求的部件构成的分页数据。
     * @throws ServiceException 服务异常。
     */
    PagedData<Section> nameLike(String nameLike, PagingInfo pagingInfo) throws ServiceException;
}
