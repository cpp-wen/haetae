/**
 * betahouse.us
 * CopyRight (c) 2012 - 2019
 */
package us.betahouse.haetae.serviceimpl.organization.service;

import us.betahouse.haetae.organization.model.OrganizationBO;
import us.betahouse.haetae.serviceimpl.organization.request.OrganizationRequest;

/**
 * 组织管理服务
 * 无需鉴权
 *
 * @author dango.yxm
 * @version : OrganizationService.java 2019/03/26 22:48 dango.yxm
 */
public interface OrganizationService {

    /**
     * 创建组织服务
     *
     * @param request
     * @return
     */
    OrganizationBO create(OrganizationRequest request);

    /**
     * 成员管理
     *
     * @param request
     */
    void memberManage(OrganizationRequest request);

    /**
     * 成员移除
     *
     * @param request
     */
    void removeMember(OrganizationRequest request);

    /**
     * 解散组织
     *
     * @param request
     */
    void disband(OrganizationRequest request);
}