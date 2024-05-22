package com.yashmerino.online.shop.services;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.settings.IndexSettings;
import com.yashmerino.online.shop.model.Product;
import com.yashmerino.online.shop.model.dto.ProductDTO;
import com.yashmerino.online.shop.utils.ApplicationProperties;
import com.yashmerino.online.shop.utils.RequestBodyToEntityConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Сервіс Algolia, який використовує сервіси Algolia для маніпулювання пошуковим індексом.
 */
@Service
@Slf4j
public class AlgoliaService {

    /**
     * Клієнт пошуку для підключення до індексу Algolia.
     */
    private final SearchIndex<ProductDTO> index;

    /**
     * Конструктор.
     *
     * @param applicationProperties властивості додатка.
     */
    public AlgoliaService(ApplicationProperties applicationProperties) {
        SearchClient client = DefaultSearchClient.create(applicationProperties.algoliaApplicationId, applicationProperties.algoliaApiKey);

        this.index = client.initIndex(applicationProperties.algoliaIndexName, ProductDTO.class);
        this.index.setSettings(new IndexSettings()
                .setSearchableAttributes(List.of("name"))
                .setCustomRanking(List.of("desc(name)"))
                .setAttributesForFaceting(List.of("categories"))
                .setAttributesToHighlight(new ArrayList<>()));

        this.index.clearObjects();
    }

    /**
     * Заповнює індекс переданими продуктами.
     *
     * @param products список продуктів для додавання до індексу.
     */
    public void populateIndex(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(RequestBodyToEntityConverter.convertToProductDTO(product));
        }

        this.index.saveObjects(productDTOs).waitTask();
    }

    /**
     * Додає продукт до індексу.
     *
     * @param productDTO продукт для додавання.
     */
    public void addProductToIndex(ProductDTO productDTO) {
        this.index.saveObject(productDTO);
    }

    /**
     * Видаляє продукт з індексу.
     *
     * @param productId ідентифікатор продукту для видалення.
     */
    public void deleteProductFromIndex(Long productId) {
        this.index.deleteObject(productId.toString());
    }

    /**
     * Оновлює продукт.
     *
     * @param productDTO DTO продукту.
     */
    public void updateProduct(ProductDTO productDTO) {
        this.index.partialUpdateObject(productDTO);
    }
}
