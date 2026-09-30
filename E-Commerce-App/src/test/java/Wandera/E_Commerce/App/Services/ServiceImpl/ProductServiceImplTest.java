package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Entities.Category;
import Wandera.E_Commerce.App.Entities.Product;
import Wandera.E_Commerce.App.Entities.SellerProfile;
import Wandera.E_Commerce.App.Repositories.CategoryRepository;
import Wandera.E_Commerce.App.Repositories.ProductRepository;
import Wandera.E_Commerce.App.Repositories.SellerProfileRepository;
import Wandera.E_Commerce.App.Repositories.UserEntityRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Test")
class ProductServiceImplTest {

    @Mock
    private  ProductRepository productRepository;
    @Mock
    private  CategoryRepository categoryRepository;
    @Mock
    private  SellerProfileRepository sellerProfileRepository;
    @Mock
    private  UserEntityRepository userEntityRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;  //class we want to test

    Product product = new Product();

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }


    @DisplayName("Get product")
    @Nested
    class  products{

        @Test
        final void testGetProduct(){

            product.setProductName("productName");
            product.setProductDescription("productDescription");
            product.setProductStock(22);
            product.setProductId(1L);
            product.setProductCode("productCode");
            product.setBackgroundColor("Black");
            product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            product.setProductImageUrl("productImageUrl");
            product.setSeller(SellerProfile.builder()
                            .email("seller@test.com")
                            .id(1L)
                            .AccountNumber("123456789")
                            .idNumber("987654321")
                            .createdAt(LocalDateTime.now())
                            .storeAddress("Kibabii University")
                            .storeName("University")
                    .build());
            product.setCategory(Category.builder()
                            .categoryName("categoryName")
                            .description("description")
                            .id(1L)
                    .build());
        }
    }
}
