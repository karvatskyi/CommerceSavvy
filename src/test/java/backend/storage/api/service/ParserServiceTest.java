package backend.storage.api.service;

import backend.storage.api.model.Item;
import backend.storage.api.model.ProductType;
import backend.storage.api.service.impl.ParserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParserServiceTest {

    @InjectMocks
    private ParserServiceImpl parserService;

    @Test
    public void parse_validInput_shouldReturnListItems() {
        List<String> input = List.of("name;price;weight;productType;description;number"
                , "Диск гальмівний;24.99;45.00;1;descDisc;123");

        List<Item> actual = parserService.parse(input);

        assertThat(actual.get(0).getProduct().getName()).isEqualTo("Диск гальмівний");
        assertThat(actual.get(0).getProduct().getPrice()).isEqualTo(24.99);
        assertThat(actual.get(0).getProduct().getWeight()).isEqualTo(45.00);
        assertThat(actual.get(0).getProduct().getProductType()).isEqualTo(ProductType.getByCode(1));
        assertThat(actual.get(0).getProduct().getDescription()).isEqualTo("descDisc");
        assertThat(actual.get(0).getQuantity()).isEqualTo(123);
    }

}

