/*
 * betahouse.us
 * CopyRight (c) 2012 - 2019
 */
package us.betahouse.haetae.serviceimpl.asset.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.betahouse.haetae.asset.dal.service.AssetLoanRecordRepoService;
import us.betahouse.haetae.asset.enums.AssetLoanRecordStatusEnum;
import us.betahouse.haetae.asset.enums.AssetStatusEnum;
import us.betahouse.haetae.asset.enums.AssetTypeEnum;
import us.betahouse.haetae.asset.manager.AssetLoanRecordManager;
import us.betahouse.haetae.asset.manager.AssetManager;
import us.betahouse.haetae.asset.model.basic.AssetBO;
import us.betahouse.haetae.asset.model.basic.AssetLoanRecordBO;
import us.betahouse.haetae.asset.request.AssetLoanRecordRequest;
import us.betahouse.haetae.serviceimpl.asset.service.AssetLoanRecordService;
import us.betahouse.haetae.serviceimpl.common.OperateContext;
import us.betahouse.util.enums.RestResultCode;
import us.betahouse.util.utils.AssertUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yiyuk.hxy
 * @version : AssetLoanRecordServiceImpl.java 2019/01/25 15:55 yiyuk.hxy
 */
@Service
public class AssetLoanRecordServiceImpl implements AssetLoanRecordService {
    private final Logger LOGGER = LoggerFactory.getLogger(AssetLoanRecordServiceImpl.class);

    @Autowired
    private AssetManager assetManager;

    @Autowired
    private AssetLoanRecordManager assetLoanRecordManager;

    @Override
    @Transactional
    public List<AssetLoanRecordBO> create(AssetLoanRecordRequest request, OperateContext context) {
        AssertUtil.assertStringNotBlank(request.getUserId(), "用户id不能为空");

        AssetBO assetBO = assetManager.findAssetByAssetID(request.getAssetId());
        if (assetBO == null) {
            AssertUtil.assertStringNotBlank("物资码无效");
            return null;
        }
        System.out.println("成功查询到物资");
        AssetStatusEnum assetStatusEnum = AssetStatusEnum.getByCode(assetBO.getAssetStatus());
        AssertUtil.assertNotNull(assetStatusEnum, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "物资状态错误");
        switch (assetStatusEnum) {
            case ASSET_ALLLOAN:
                AssertUtil.assertStringNotBlank(assetBO.getAssetName(), "物资全部借出");
                return assetLoanRecordManager.findAssetLoanRecordByAssetId(request.getAssetId());
            case ASSET_DISTORY:
                AssertUtil.assertStringNotBlank(assetBO.getAssetName(), "物资耗尽");
                return assetLoanRecordManager.findDistoryRecordByAssetId(request.getAssetId());
            default:
                break;
        }
        request.setAssetType(assetBO.getAssetType());
        List<AssetLoanRecordBO> assetLoanRecordBOS = new ArrayList<AssetLoanRecordBO>();
        AssetLoanRecordBO assetLoanRecordBO = assetLoanRecordManager.create(request);
        assetLoanRecordBOS.add(assetLoanRecordBO);
        return assetLoanRecordBOS;
    }

    @Override
    @Transactional
    public AssetLoanRecordBO update(AssetLoanRecordRequest request, OperateContext context) {
        return assetLoanRecordManager.update(request);
    }

    @Override
    @Transactional
    public List<AssetLoanRecordBO> findAllAssetLoanRecordByAssetId(AssetLoanRecordRequest request, OperateContext context) {
        return assetLoanRecordManager.findAllAssetLoanRecordByAssetId(request.getAssetId());
    }

    @Override
    @Transactional
    public List<AssetLoanRecordBO> findAllAssetLoanRecordByUserId(AssetLoanRecordRequest request, OperateContext context) {
        return assetLoanRecordManager.findAssetLoanRecordByUserId(request.getUserId());
    }

    @Override
    public AssetLoanRecordBO findAssetLoanRecordByLoanRecordId(AssetLoanRecordRequest request, OperateContext context) {
        return assetLoanRecordManager.findAssetLoanRecordByLoanRecordId(request.getLoanRecordId());
    }
}
