/*
 * betahouse.us
 * CopyRight (c) 2012 - 2019
 */
package us.betahouse.haetae.serviceimpl.asset.service;

import us.betahouse.haetae.asset.model.basic.AssetLoanRecordBO;
import us.betahouse.haetae.asset.request.AssetLoanRecordRequest;
import us.betahouse.haetae.serviceimpl.common.OperateContext;

import java.util.List;

/**
 * @author yiyuk.hxy
 * @version : AssetLoanRecordService.java 2019/01/25 15:48 yiyuk.hxy
 */
public interface AssetLoanRecordService {
    /**
     * 创建借用记录
     *
     * @param request
     * @param context
     * @return
     */
    List<AssetLoanRecordBO> create(AssetLoanRecordRequest request, OperateContext context);

    /**
     * 更新借用记录
     *
     * @param request
     * @param context
     * @return
     */
    AssetLoanRecordBO update(AssetLoanRecordRequest request, OperateContext context);

    /**
     * 查询所有借用记录
     *
     * @param request
     * @param context
     * @return
     */
    List<AssetLoanRecordBO> findAllAssetLoanRecordByAssetId(AssetLoanRecordRequest request, OperateContext context);
    /**
     * 查询所有借用记录
     *
     * @param request
     * @param context
     * @return
     */
    List<AssetLoanRecordBO> findAllAssetLoanRecordByUserId(AssetLoanRecordRequest request, OperateContext context);

    /**
     *
     * @param request
     * @param context
     * @return
     */
    AssetLoanRecordBO findAssetLoanRecordByLoanRecordId(AssetLoanRecordRequest request, OperateContext context);
}
