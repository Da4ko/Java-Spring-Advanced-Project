package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyMercedesCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersMercedesCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.model.entity.enums.*;
import com.example.java_spring_advanced_project.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

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


public class MercedesServiceImplTest {

    @Mock
    private Authentication authentication;
    @Mock
    private MercedesCarsRepository mercedesCarsRepository;
    @Mock
    private MercedesModelRepository mercedesModelRepository;
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
    private MercedesServiceImpl mercedesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mercedesCarsRepository = mock(MercedesCarsRepository.class);
        mercedesModelRepository = mock(MercedesModelRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        engineRepository = mock(EngineRepository.class);
        transmissionRepository = mock(TransmissionRepository.class);
        currencyRepository = mock(CurrencyRepository.class);
        userRepository = mock(UserRepository.class);


        mercedesService = new MercedesServiceImpl(
                mercedesCarsRepository,
                mercedesModelRepository,
                categoryRepository,
                engineRepository,
                transmissionRepository,
                currencyRepository,
                userRepository
        );
    }

    @Test
    public void testGetMercedesCarsForHomePage() {
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

        MercedesModel mercedesModel1 = new MercedesModel();
        mercedesModel1.setModel(MercedesModelsEnum.CL);

        MercedesModel mercedesModel2 = new MercedesModel();
        mercedesModel2.setModel(MercedesModelsEnum.EQS);

        Category category1 = new Category();
        category1.setCategory(CategoryName.Sedan);

        Category category2 = new Category();
        category2.setCategory(CategoryName.SUV);

        Engine engine1 = new Engine();
        engine1.setEngineType(EngineTypeEnum.Diesel);

        Engine engine2 = new Engine();
        engine2.setEngineType(EngineTypeEnum.Hybrid);

        Transmission transmission1 = new Transmission();
        transmission1.setTransmission(TransmissionType.Automatic);

        Transmission transmission2 = new Transmission();
        transmission2.setTransmission(TransmissionType.Manual);

        Currency currency1 = new Currency();
        currency1.setCurrency(CurrencyName.Euro);

        Currency currency2 = new Currency();
        currency2.setCurrency(CurrencyName.Dollar);

        MercedesCar myMercedesCar = new MercedesCar();
        myMercedesCar.setOwner(currentUser);
        myMercedesCar.setMercedesModel(mercedesModel1);
        myMercedesCar.setReleaseDate(LocalDate.now());
        myMercedesCar.setCategory(category1);
        myMercedesCar.setEngine(engine1);
        myMercedesCar.setTransmission(transmission1);
        myMercedesCar.setCurrency(currency1);

        MercedesCar otherMercedesCar = new MercedesCar();
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("otherUser");
        otherMercedesCar.setOwner(otherUser);
        otherMercedesCar.setMercedesModel(mercedesModel2);
        otherMercedesCar.setReleaseDate(LocalDate.now());
        otherMercedesCar.setCategory(category2);
        otherMercedesCar.setEngine(engine2);
        otherMercedesCar.setTransmission(transmission2);
        otherMercedesCar.setCurrency(currency2);

        when(mercedesCarsRepository.findAll()).thenReturn(Arrays.asList(myMercedesCar, otherMercedesCar));

        // Act
        HomeMercedesCarsDto result = mercedesService.getMercedesCarsForHomePage();

        List<MyMercedesCarDto> myMercedesCars = new ArrayList<>();
        myMercedesCars.add(new MyMercedesCarDto(myMercedesCar));
        result.setMyMercedesCars(myMercedesCars);

        List<OthersMercedesCarDto> othersMercedesCars = new ArrayList<>();
        othersMercedesCars.add(new OthersMercedesCarDto(otherMercedesCar));
        result.setOthersMercedesCars(othersMercedesCars);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMyMercedesCars().size());
        assertEquals(1, result.getOthersMercedesCars().size());
        assertEquals(otherMercedesCar.getOwner().getUsername(), result.getOthersMercedesCars().get(0).getOwnerUsername());
    }

    @Test
    public void testCreateMercedes() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        MercedesModel mercedesModel = new MercedesModel(MercedesModelsEnum.CL, "Description");
        Category category = new Category(CategoryName.Sedan, "Sedan description");
        Engine engine = new Engine(EngineTypeEnum.Diesel, "Diesel engine");
        Transmission transmission = new Transmission(TransmissionType.Automatic, "Automatic transmission");
        Currency currency = new Currency(CurrencyName.Euro, "EUR origin", 1.0);
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.of(mercedesModel));
        when(categoryRepository.findByCategory(CategoryName.Sedan)).thenReturn(Optional.of(category));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(engine));
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
            boolean result = mercedesService.createMercedes(bindingModel);

            // Assert
            assertTrue(result);
            verify(mercedesCarsRepository, times(1)).save(any(MercedesCar.class));
        }
    }

    @Test
    public void testCreateMercedesWithMissingModel() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("MercedesModel must be specified.", thrown.getMessage());
    }

    @Test
    public void testCreateMercedesWithInvalidModel() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("MercedesModel not found: CL", thrown.getMessage());
    }

    @Test
    public void testCreateMercedesWithInvalidCategory() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.of(new MercedesModel()));
        when(categoryRepository.findByCategory(CategoryName.Sedan)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("Category not found: Sedan", thrown.getMessage());
    }

    @Test
    public void testCreateMercedesWithInvalidEngine() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.of(new MercedesModel()));
        when(categoryRepository.findByCategory(CategoryName.Sedan)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("Engine not found for type: Diesel", thrown.getMessage());
    }

    @Test
    public void testCreateMercedesWithInvalidTransmission() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.of(new MercedesModel()));
        when(categoryRepository.findByCategory(CategoryName.Sedan)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("Transmission not found: Automatic", thrown.getMessage());
    }

    @Test
    public void testCreateMercedesWithInvalidCurrency() {
        // Arrange
        MercedesAddBindingModel bindingModel = new MercedesAddBindingModel();
        bindingModel.setMercedesModel(MercedesModelsEnum.CL);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.Sedan);
        bindingModel.setEngineType(EngineTypeEnum.Diesel);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(10000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(50000.0);
        bindingModel.setDescription("Luxury Sedan");

        when(mercedesModelRepository.findByModel(MercedesModelsEnum.CL)).thenReturn(Optional.of(new MercedesModel()));
        when(categoryRepository.findByCategory(CategoryName.Sedan)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Diesel)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(new Transmission()));
        when(currencyRepository.findByCurrency(CurrencyName.Euro)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> mercedesService.createMercedes(bindingModel));
        assertEquals("Currency not found: Euro", thrown.getMessage());
    }

    @Test
    public void testDeleteMercedes() {
        // Arrange
        String mercedesUUID = "1234";

        // Act
        mercedesService.deleteMercedes(mercedesUUID);

        // Assert
        verify(mercedesCarsRepository, times(1)).deleteMercedesCarById(mercedesUUID);
    }
}
