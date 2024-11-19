package com.tiny.admin.biz.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tiny.admin.biz.pms.dto.PmsProductDto;
import com.tiny.admin.biz.pms.entity.PmsProduct;
import com.tiny.admin.biz.system.vo.BaseQueryParam;
import com.tiny.core.web.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;

/**
 * <p>
 * Product Table 服务类
 * </p>
 *
 * @author lxh
 * @since 2024-11-16
 */
public interface IPmsProductService extends IService<PmsProduct> {

    IPage<PmsProductDto> pageList(BaseQueryParam param);

    @Transactional(rollbackFor = Exception.class)
    Result<Boolean> saveOrUpdateProduct(PmsProductDto pmsProductDto);

    Result<ArrayList<PmsProductDto>> getProductsByIds(Set<String> ids);
}
