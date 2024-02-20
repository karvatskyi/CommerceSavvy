package backend.storage.api.service.impl;

import backend.storage.api.model.Item;
import backend.storage.api.model.Product;
import backend.storage.api.model.ProductType;
import backend.storage.api.service.ParserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ParserServiceImpl implements ParserService<Item> {
    private static final byte NAME_INDEX = 0;
    private static final byte PRICE_INDEX = 1;
    private static final byte WEIGHT_INDEX = 2;
    private static final byte PRODUCT_TYPE_INDEX = 3;
    private static final byte DESC_INDEX = 4;
    private static final byte NUMBER_INDEX = 5;
    @Override
    public List<Item> parse(List<String> rows) {
        List<Item> list = new LinkedList<>();
        for(int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i).split(";");
            Product product = new Product();
            product.setName(row[NAME_INDEX]);
            product.setPrice(Double.parseDouble(row[PRICE_INDEX]));
            product.setWeight(Double.parseDouble(row[WEIGHT_INDEX]));
            product.setProductType(ProductType.getByCode(Integer.parseInt(row[PRODUCT_TYPE_INDEX])));
            product.setDescription(row[DESC_INDEX]);
            Item item = new Item();
            item.setQuantity(Integer.parseInt(row[NUMBER_INDEX]));
            item.setProduct(product);
            list.add(item);
        }
        return list;
    }
}
