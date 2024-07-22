package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAudiCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyAudiCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersAudiCarDto;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AudiServiceImplTest {

    @Mock
    private Authentication authentication;
    @Mock
    private AudiCarsRepository audiCarsRepository;
    @Mock
    private AudiModelRepository audiModelRepository;
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
    private AudiServiceImpl audiService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Mock repository instances
        audiCarsRepository = mock(AudiCarsRepository.class);
        audiModelRepository = mock(AudiModelRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        engineRepository = mock(EngineRepository.class);
        transmissionRepository = mock(TransmissionRepository.class);
        currencyRepository = mock(CurrencyRepository.class);
        userRepository = mock(UserRepository.class);

        // Create AudiServiceImpl with mocked dependencies
        audiService = new AudiServiceImpl(
                audiCarsRepository,
                audiModelRepository,
                categoryRepository,
                engineRepository,
                transmissionRepository,
                currencyRepository,
                userRepository
        );
    }

    @Test
    public void testGetAudiCarsForHomePage() {
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

        AudiModel audiModel1 = new AudiModel();
        audiModel1.setModel(AudiModelsEnum.A4);

        AudiModel audiModel2 = new AudiModel();
        audiModel2.setModel(AudiModelsEnum.Q7);

        Category category1 = new Category();
        category1.setCategory(CategoryName.SUV);

        Category category2 = new Category();
        category2.setCategory(CategoryName.SUV);

        Engine engine1 = new Engine();
        engine1.setEngineType(EngineTypeEnum.Gasoline);

        Engine engine2 = new Engine();
        engine2.setEngineType(EngineTypeEnum.Gasoline);

        Transmission transmission1 = new Transmission();
        transmission1.setTransmission(TransmissionType.Manual);

        Transmission transmission2 = new Transmission();
        transmission2.setTransmission(TransmissionType.Manual);

        Currency currency1 = new Currency();
        currency1.setCurrency(CurrencyName.Euro);

        Currency currency2 = new Currency();
        currency1.setCurrency(CurrencyName.Euro);


        AudiCar myAudiCar = new AudiCar();
        myAudiCar.setOwner(currentUser);
        myAudiCar.setAudiModel(audiModel1);
        myAudiCar.setReleaseDate(LocalDate.now());
        myAudiCar.setCategory(category1);
        myAudiCar.setEngine(engine1);
        myAudiCar.setTransmission(transmission1);
        myAudiCar.setCurrency(currency1);

        AudiCar otherAudiCar = new AudiCar();
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("otherUser");
        otherAudiCar.setOwner(otherUser);
        otherAudiCar.setAudiModel(audiModel2);
        otherAudiCar.setReleaseDate(LocalDate.now());
        otherAudiCar.setCategory(category2);
        otherAudiCar.setEngine(engine2);
        otherAudiCar.setTransmission(transmission2);
        otherAudiCar.setCurrency(currency2);


        when(audiCarsRepository.findAll()).thenReturn(Arrays.asList(myAudiCar, otherAudiCar));

        // Act
        HomeAudiCarsDto result = audiService.getAudiCarsForHomePage();

        List<MyAudiCarDto> myAudiCars = new ArrayList<>();
        myAudiCars.add(new MyAudiCarDto(myAudiCar));
        result.setMyAudiCars(myAudiCars);

        List<OthersAudiCarDto> othersAudiCar = new ArrayList<>();
        othersAudiCar.add(new OthersAudiCarDto(otherAudiCar));
        result.setOthersAudiCars(othersAudiCar);
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMyAudiCars().size());
        assertEquals(1, result.getOthersAudiCars().size());
        assertEquals(otherAudiCar.getOwner().getUsername(), result.getOthersAudiCars().get(0).getOwnerUsername());
    }


    @Test
    public void testCreateAudi() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        AudiModel audiModel = new AudiModel(AudiModelsEnum.A1, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");
        Engine engine = new Engine(EngineTypeEnum.Gasoline, "Gasoline engine");
        Transmission transmission = new Transmission(TransmissionType.Automatic, "Automatic transmission");
        Currency currency = new Currency(CurrencyName.Euro, "Euro origin", 1.0);
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.of(audiModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.of(engine));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(transmission));
        when(currencyRepository.findByCurrency(CurrencyName.Euro)).thenReturn(Optional.of(currency));

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
            boolean result = audiService.createAudi(bindingModel);

            // Assert
            assertTrue(result);
            verify(audiCarsRepository, times(1)).save(any(AudiCar.class));
        }
    }

    @Test
    public void testCreateAudiWithMissingModel() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("AudiModel must be specified.", thrown.getMessage());
    }

    @Test
    public void testCreateAudiWithInvalidModel() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("AudiModel not found: A1", thrown.getMessage());
    }

    @Test
    public void testCreateAudiWithInvalidCategory() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        AudiModel audiModel = new AudiModel(AudiModelsEnum.A1, "Description");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.of(audiModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("Category not found: SUV", thrown.getMessage());
    }

    @Test
    public void testCreateAudiWithInvalidEngine() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        AudiModel audiModel = new AudiModel(AudiModelsEnum.A1, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.of(audiModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("Engine not found for type: Gasoline", thrown.getMessage());
    }

    @Test
    public void testCreateAudiWithInvalidTransmission() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        AudiModel audiModel = new AudiModel(AudiModelsEnum.A1, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");
        Engine engine = new Engine(EngineTypeEnum.Gasoline, "Gasoline engine");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.of(audiModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.of(engine));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("Transmission not found: Automatic", thrown.getMessage());
    }

    @Test
    public void testCreateAudiWithInvalidCurrency() {
        // Arrange
        AudiAddBindingModel bindingModel = new AudiAddBindingModel();
        bindingModel.setAudiModel(AudiModelsEnum.A1);
        bindingModel.setHorsePower(100);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(20000.0);
        bindingModel.setDescription("Nice car");

        AudiModel audiModel = new AudiModel(AudiModelsEnum.A1, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");
        Engine engine = new Engine(EngineTypeEnum.Gasoline, "Gasoline engine");
        Transmission transmission = new Transmission(TransmissionType.Automatic, "Automatic transmission");

        when(audiModelRepository.findByModel(AudiModelsEnum.A1)).thenReturn(Optional.of(audiModel));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.of(engine));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(transmission));
        when(currencyRepository.findByCurrency(CurrencyName.Euro)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> audiService.createAudi(bindingModel));
        assertEquals("Currency not found: Euro", thrown.getMessage());
    }

    // Additional tests can be added here if necessary
}
