package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyBmwCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersBmwCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.model.entity.enums.*;
import com.example.java_spring_advanced_project.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BmwServiceImplTest {

    @Mock
    private Authentication authentication;
    @Mock
    private BmwCarsRepository bmwCarsRepository;
    @Mock
    private BmwModelRepository bmwModelRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private EngineRepository engineRepository;
    @Mock
    private TransmissionRepository transmissionRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BmwServiceImpl bmwService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock repository instances
        bmwCarsRepository = mock(BmwCarsRepository.class);
        bmwModelRepository = mock(BmwModelRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        engineRepository = mock(EngineRepository.class);
        transmissionRepository = mock(TransmissionRepository.class);
        currencyRepository = mock(CurrencyRepository.class);
        userRepository = mock(UserRepository.class);


        bmwService = new BmwServiceImpl(
                bmwCarsRepository,
                bmwModelRepository,
                categoryRepository,
                engineRepository,
                transmissionRepository,
                currencyRepository,
                userRepository
        );
    }

    @Test
    public void testGetBmwCarsForHomePage() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        BmwModel bmwModel1 = new BmwModel();
        bmwModel1.setModel(BmwModelsEnum.X5);

        BmwModel bmwModel2 = new BmwModel();
        bmwModel2.setModel(BmwModelsEnum.X6);

        Category category1 = new Category();
        category1.setCategory(CategoryName.SUV);

        Category category2 = new Category();
        category2.setCategory(CategoryName.SUV);

        Engine engine1 = new Engine();
        engine1.setEngineType(EngineTypeEnum.Diesel);

        Engine engine2 = new Engine();
        engine2.setEngineType(EngineTypeEnum.Diesel);

        Transmission transmission1 = new Transmission();
        transmission1.setTransmission(TransmissionType.Automatic);

        Transmission transmission2 = new Transmission();
        transmission2.setTransmission(TransmissionType.Automatic);

        Currency currency1 = new Currency();
        currency1.setCurrency(CurrencyName.Dollar);

        Currency currency2 = new Currency();
        currency2.setCurrency(CurrencyName.Dollar);

        BmwCar myBmwCar = new BmwCar();
        myBmwCar.setOwner(currentUser);
        myBmwCar.setBmwModel(bmwModel1);
        myBmwCar.setReleaseDate(LocalDate.now());
        myBmwCar.setCategory(category1);
        myBmwCar.setEngine(engine1);
        myBmwCar.setTransmission(transmission1);
        myBmwCar.setCurrency(currency1);

        BmwCar otherBmwCar = new BmwCar();
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("otherUser");
        otherBmwCar.setOwner(otherUser);
        otherBmwCar.setBmwModel(bmwModel2);
        otherBmwCar.setReleaseDate(LocalDate.now());
        otherBmwCar.setCategory(category2);
        otherBmwCar.setEngine(engine2);
        otherBmwCar.setTransmission(transmission2);
        otherBmwCar.setCurrency(currency2);

        when(bmwCarsRepository.findAll()).thenReturn(Arrays.asList(myBmwCar, otherBmwCar));

        // Act
        HomeBmwCarsDto result = bmwService.getBmwCarsForHomePage();

        List<MyBmwCarDto> myBmwCars = new ArrayList<>();
        myBmwCars.add(new MyBmwCarDto(myBmwCar));
        result.setMyBmwCars(myBmwCars);

        List<OthersBmwCarDto> othersBmwCars = new ArrayList<>();
        othersBmwCars.add(new OthersBmwCarDto(otherBmwCar));
        result.setOthersBmwCars(othersBmwCars);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMyBmwCars().size());
        assertEquals(1, result.getOthersBmwCars().size());
        assertEquals(otherBmwCar.getOwner().getUsername(), result.getOthersBmwCars().get(0).getOwnerUsername());
    }

    @Test
    public void testCreateBmw() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        BmwModel bmwModel = new BmwModel(BmwModelsEnum.X3, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");
        Engine engine = new Engine(EngineTypeEnum.Diesel, "Diesel engine");
        Transmission transmission = new Transmission(TransmissionType.Automatic, "Automatic transmission");
        Currency currency = new Currency(CurrencyName.Dollar, "USD origin", 1.0);
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.of(bmwModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(engine));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(transmission));
        when(currencyRepository.findByCurrency(CurrencyName.Dollar)).thenReturn(Optional.of(currency));

        // Mocking Security Context
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserDetails userDetails = mock(UserDetails.class);

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(userDetails.getUsername()).thenReturn("testUser");

            when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(currentUser));

            // Act
            boolean result = bmwService.createBmw(bindingModel);

            // Assert
            assertTrue(result);
            verify(bmwCarsRepository, times(1)).save(any(BmwCar.class));
        }
    }

    @Test
    public void testCreateBmwWithMissingModel() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("BmwModel must be specified.", thrown.getMessage());
    }

    @Test
    public void testCreateBmwWithInvalidModel() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("BmwModel not found: X3", thrown.getMessage());
    }

    @Test
    public void testCreateBmwWithInvalidCategory() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.of(new BmwModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("Category not found: SUV", thrown.getMessage());
    }

    @Test
    public void testCreateBmwWithInvalidEngine() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.of(new BmwModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("Engine not found for type: Diesel", thrown.getMessage());
    }

    @Test
    public void testCreateBmwWithInvalidTransmission() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.of(new BmwModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("Transmission not found: Automatic", thrown.getMessage());
    }

    @Test
    public void testCreateBmwWithInvalidCurrency() {
        // Arrange
        BmwAddBindingModel bindingModel = new BmwAddBindingModel();
        bindingModel.setBmwModel(BmwModelsEnum.X3);
        bindingModel.setHorsePower(150);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Dollar);
        bindingModel.setPrice(30000.0);
        bindingModel.setDescription("Excellent car");

        when(bmwModelRepository.findByModel(BmwModelsEnum.X3)).thenReturn(Optional.of(new BmwModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(new Transmission()));
        when(currencyRepository.findByCurrency(CurrencyName.Dollar)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> bmwService.createBmw(bindingModel));
        assertEquals("Currency not found: Dollar", thrown.getMessage());
    }

    @Test
    public void testDeleteBmw() {
        // Arrange
        String bmwUUID = "1234";

        // Act
        bmwService.deleteBmw(bmwUUID);

        // Assert
        verify(bmwCarsRepository, times(1)).deleteBmwCarById(bmwUUID);
    }
}
