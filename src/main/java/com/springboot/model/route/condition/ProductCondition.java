package com.springboot.model.route.condition;

import com.springboot.model.route.dst.DstRoute;
import com.springboot.model.route.bean.ProductRoute;
import lombok.Data;

import java.util.List;

@Data
public class ProductCondition extends Condition {
    ProductRoute productRoute;
    List<ProductRoute> productRoutes;
    public ProductCondition(ProductRoute productRoute){
        this.productRoute=productRoute;
    }

    public DstRoute getRouteFromProduct(ProductRoute productRoute){
        DstRoute dstRoute=new DstRoute();
        if(productRoute.getProductType()=="forHengShuiProduct"){
            dstRoute.setDst("HengShuiBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5002);

        }
        if(productRoute.getProductType()=="forHengShuiProduct"){
            dstRoute.setDst("HengShuiBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5002);

        }
        if(productRoute.getProductType()=="forXinWangProduct"){
            dstRoute.setDst("XWBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5001);

        }
        if(productRoute.getProductType()=="forXinWangProduct"){
            dstRoute.setDst("XWBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5001);

        }
        if(productRoute.getProductType()=="forBaoTouNongShangProduct"){
            dstRoute.setDst("BaoTouNongShangBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5003);

        }
        if(productRoute.getProductType()=="forOtherBankProduct"){
            dstRoute.setDst("OtherBank");
            dstRoute.setIp("127.0.0.1");
            dstRoute.setPort(5004);

        }
        return dstRoute;
    }

}
