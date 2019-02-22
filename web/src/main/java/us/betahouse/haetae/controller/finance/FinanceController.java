/*
  betahouse.us
  CopyRight (c) 2012 - 2019
 */
package us.betahouse.haetae.controller.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import us.betahouse.haetae.common.log.LoggerName;
import us.betahouse.haetae.common.session.CheckLogin;
import us.betahouse.haetae.common.template.RestOperateCallBack;
import us.betahouse.haetae.common.template.RestOperateTemplate;
import us.betahouse.haetae.finance.model.basic.FinanceMessageBO;
import us.betahouse.haetae.finance.model.basic.FinanceTotalBO;
import us.betahouse.haetae.finance.model.common.PageList;
import us.betahouse.haetae.model.finance.request.FinanceRestRequest;
import us.betahouse.haetae.model.finance.vo.FinanceTotalVO;
import us.betahouse.haetae.model.finance.vo.FinanceVO;
import us.betahouse.haetae.model.finance.vo.builder.FinanceTotalVOBuilder;
import us.betahouse.haetae.serviceimpl.common.OperateContext;
import us.betahouse.haetae.serviceimpl.finance.request.FinanceManagerRequest;
import us.betahouse.haetae.serviceimpl.finance.request.builder.FinanceManagerRequestBuilder;
import us.betahouse.haetae.serviceimpl.finance.service.FinanceService;
import us.betahouse.haetae.utils.IPUtil;
import us.betahouse.haetae.utils.RestResultUtil;
import us.betahouse.util.common.Result;
import us.betahouse.util.enums.RestResultCode;
import us.betahouse.util.log.Log;
import us.betahouse.util.utils.AssertUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 财务接口
 *
 * @author MessiahJK
 * @version : FinanceController.java 2019/02/21 1:15 MessiahJK
 */
@RestController
@RequestMapping(value = "/finance")
public class FinanceController {
    /**
     * 日志实体
     */
    private final Logger LOGGER = LoggerFactory.getLogger(FinanceController.class);


    @Autowired
    FinanceService financeService;

    /**
     * 获取财务信息列表
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @GetMapping("/message")
    @Log(loggerName = LoggerName.FINANCE_DIGEST)
    public Result<PageList<FinanceMessageBO>> getMessage(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "获取财务信息列表", request, new RestOperateCallBack<PageList<FinanceMessageBO>>(){

            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getOrganizationId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "组织id不能为空");
                AssertUtil.assertStringNotBlank(request.getFinanceName(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "财务名不能为空");
            }

            @Override
            public Result<PageList<FinanceMessageBO>> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                FinanceManagerRequest financeManagerRequest=FinanceManagerRequestBuilder
                        .aFinanceManagerRequest()
                        .withOrganizationId(request.getOrganizationId())
                        .withStatus(request.getStatus())
                        .withTerm(request.getTerm())
                        .withPage(request.getPage())
                        .withLimit(request.getLimit())
                        .build();

                return RestResultUtil.buildSuccessResult(financeService.findMessage(financeManagerRequest,context),"财务信息列表获取成功");
            }
        });
    }

    /**
     * 提交预算
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @PostMapping("/budget")
    @Log(loggerName =LoggerName.FINANCE_DIGEST)
    public Result<FinanceMessageBO> submitBudget(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "提交预算", request, new RestOperateCallBack<FinanceMessageBO>(){

            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getOrganizationId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "组织id不能为空");
                AssertUtil.assertStringNotBlank(request.getFinanceName(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "财务名不能为空");
                AssertUtil.assertNotNull(request.getBudget(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "预算金额不能为空");
                AssertUtil.assertBigDecimalPositive(request.getBudget(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "预算金额不能为非正");
            }

            @Override
            public Result<FinanceMessageBO> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                FinanceManagerRequest financeManagerRequest=FinanceManagerRequestBuilder
                        .aFinanceManagerRequest()
                        .withOrganizationId(request.getOrganizationId())
                        .withFinanceName(request.getFinanceName())
                        .withFinanceInfo(request.getFinanceInfo())
                        .withRemark(request.getRemark())
                        .withBudget(request.getBudget())
                        .withUserId(request.getUserId())
                        .build();
                return RestResultUtil.buildSuccessResult(financeService.submitBudget(financeManagerRequest, context),"预算提交成功");
            }
        });
    }

    /**
     * 审核
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @PutMapping("/audite")
    @Log(loggerName =LoggerName.FINANCE_DIGEST)
    public Result<FinanceVO> audite(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "审核预算", request, new RestOperateCallBack<FinanceVO>(){

            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getFinanceMessageId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "财务信息id不能为空");
                AssertUtil.assertNotNull(request.getAudite(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "审核判断不能为空");
                AssertUtil.assertNotNull(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为空");
                AssertUtil.assertBigDecimalPositive(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为非正");
            }

            @Override
            public Result<FinanceVO> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                return null;
            }
        });
    }

    /**
     * 核算
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @PutMapping("/check")
    @Log(loggerName =LoggerName.FINANCE_DIGEST)
    public Result<FinanceVO> check(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "核算", request, new RestOperateCallBack<FinanceVO>(){

            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getFinanceMessageId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "财务信息id不能为空");
                AssertUtil.assertNotNull(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为空");
                AssertUtil.assertBigDecimalPositive(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为非正");
            }

            @Override
            public Result<FinanceVO> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                return null;
            }
        });
    }

    /**
     * 记账
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @PutMapping("/tally")
    @Log(loggerName =LoggerName.FINANCE_DIGEST)
    public Result<FinanceMessageBO> tally(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "记账", request, new RestOperateCallBack<FinanceMessageBO>(){

            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getOrganizationId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "组织id不能为空");
                AssertUtil.assertStringNotBlank(request.getFinanceName(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "财务名不能为空");
                AssertUtil.assertNotNull(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为空");
                AssertUtil.assertBigDecimalPositive(request.getTrueMoney(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "真实金额不能为非正");
            }

            @Override
            public Result<FinanceMessageBO> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                FinanceManagerRequest financeManagerRequest=FinanceManagerRequestBuilder
                        .aFinanceManagerRequest()
                        .withOrganizationId(request.getOrganizationId())
                        .withFinanceName(request.getFinanceName())
                        .withFinanceInfo(request.getFinanceInfo())
                        .withRemark(request.getRemark())
                        .withTrueMoney(request.getTrueMoney())
                        .withType(request.getType())
                        .withUserId(request.getUserId())
                        .build();
                return RestResultUtil.buildSuccessResult(financeService.tally(financeManagerRequest, context),"记账成功");
            }
        });
    }

    /**
     * 获取总金额
     *
     * @param request
     * @param httpServletRequest
     * @return
     */
    @CheckLogin
    @GetMapping("/total")
    @Log(loggerName =LoggerName.FINANCE_DIGEST)
    public Result<FinanceTotalVO> total(FinanceRestRequest request, HttpServletRequest httpServletRequest) {
        return RestOperateTemplate.operate(LOGGER, "获取总金额", request, new RestOperateCallBack<FinanceTotalVO>(){
            @Override
            public void before() {
                AssertUtil.assertNotNull(request, RestResultCode.ILLEGAL_PARAMETERS.getCode(), "请求体不能为空");
                AssertUtil.assertStringNotBlank(request.getUserId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "用户不能为空");
                AssertUtil.assertStringNotBlank(request.getOrganizationId(), RestResultCode.ILLEGAL_PARAMETERS.getCode(), "组织id不能为空");
            }

            @Override
            public Result<FinanceTotalVO> execute() {
                OperateContext context = new OperateContext();
                context.setOperateIP(IPUtil.getIpAddr(httpServletRequest));
                FinanceManagerRequest financeManagerRequest=FinanceManagerRequestBuilder
                        .aFinanceManagerRequest()
                        .withOrganizationId(request.getOrganizationId())
                        .withUserId(request.getUserId())
                        .build();
                FinanceTotalBO financeTotalBO=financeService.total(financeManagerRequest, context);
                return RestResultUtil.buildSuccessResult(FinanceTotalVOBuilder
                        .aFinanceTotalVO()
                        .withTotalMoney(financeTotalBO.getTotalMoney())
                        .withTotalMoneyIncludeBudget(financeTotalBO.getTotalMoneyIncludeBudget())
                        .build(),"获取总金额成功");
            }
        });
    }

}
