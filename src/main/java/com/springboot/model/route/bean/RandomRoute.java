package com.springboot.model.route.bean;

import com.springboot.model.route.dst.DstRoute;
import lombok.Data;

import java.util.List;
@Data
public class RandomRoute extends Route {
    List<DstRoute> dstRoutes;
    public RandomRoute(){

    }

    @Override
    public DstRoute getDst() {

        return super.getDst();
    }


}
