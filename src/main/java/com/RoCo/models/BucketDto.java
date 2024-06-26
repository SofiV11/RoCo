package com.RoCo.models;

import lombok.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class BucketDto {
    private int amountProducts;
    private Double sum;
    private Double shipping;
    private List<BucketDetailDto> bucketDetails = new ArrayList<>();

    public void aggregate(){
        this.amountProducts = bucketDetails.size();
        this.sum = bucketDetails.stream()
                .map(BucketDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
        this.shipping = this.sum / 5;
    }

    public void setAmountProducts(int amountProducts) {
        this.amountProducts = amountProducts;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public void setBucketDetails(List<BucketDetailDto> bucketDetails) {
        this.bucketDetails = bucketDetails;
    }

    public int getAmountProducts() {
        return amountProducts;
    }

    public Double getSum() {
        return sum;
    }

    public List<BucketDetailDto> getBucketDetails() {
        return bucketDetails;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }
}
