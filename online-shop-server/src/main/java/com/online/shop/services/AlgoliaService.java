package com.online.shop.services;

import com.algolia.search.DefaultSearchClient;
import com.algolia.search.SearchClient;
import com.algolia.search.SearchIndex;
import com.algolia.search.models.settings.IndexSettings;
import com.online.shop.model.Product;
import com.online.shop.model.dto.ProductDTO;
import com.online.shop.utils.ApplicationProperties;
import com.online.shop.utils.RequestBodyToEntityConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AlgoliaService {
    private final SearchIndex<ProductDTO> index;
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
    public void populateIndex(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            productDTOs.add(RequestBodyToEntityConverter.convertToProductDTO(product));
        }

        this.index.saveObjects(productDTOs).waitTask();
    }
    public void addProductToIndex(ProductDTO productDTO) {
        this.index.saveObject(productDTO);
    }
    public void deleteProductFromIndex(Long productId) {
        this.index.deleteObject(productId.toString());
    }
    public void updateProduct(ProductDTO productDTO) {
        this.index.partialUpdateObject(productDTO);
    }
}
