package company.product;

import company.address.Coordinates;
import storage.Item;

/**
 * Created by Arthur Deschamps on 02.06.17.
 * Defines a Product/item of type ProductType
 */
public class Product extends Item {

    private ProductType productType;
    private float price;

    public Product(ProductType productType, Coordinates currentLocation, float price) {
        this.productType = productType;
        this.price = price;
    }

    public Product(ProductType productType, Coordinates currentLocation) {
        this.productType = productType;
        this.price = productType.getBasePrice();
    }


    @Override
    public boolean validate() {
        return ((productType != null));
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
