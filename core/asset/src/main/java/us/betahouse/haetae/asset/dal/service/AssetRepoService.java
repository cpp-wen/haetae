/**
 * betahouse.us
 * CopyRight (c) 2012 - 2019
 */
package us.betahouse.haetae.asset.dal.service;

import us.betahouse.haetae.asset.dal.model.AssetDO;
import us.betahouse.haetae.asset.model.basic.AssetBO;

/**
 * @author guofan.cp
 * @version : AssetRepoService.java 2019/01/21 22:31 guofan.cp
 */
public interface AssetRepoService {
    /**
     * @Description: 新增物资
     * @Param: [assetBO]
     */
    AssetBO createAsset(AssetBO assetBO);
    /** 
    * @Description: 查找物资 
    * @Param: [assetId] 
    */ 
    AssetDO findByAssetId(String assetId);
    /** 
    * @Description: 判断物资状态 
    * @Param: [assetId] 
    */ 
    String judgeByAssetId(String assetId);
}
