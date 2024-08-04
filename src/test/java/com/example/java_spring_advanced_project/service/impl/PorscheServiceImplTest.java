package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyPorscheCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersPorscheCarDto;
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


public class PorscheServiceImplTest {

    @Mock
    private Authentication authentication;
    @Mock
    private PorscheCarsRepository porscheCarsRepository;
    @Mock
    private PorscheModelRepository porscheModelRepository;
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
    private PorscheServiceImpl porscheService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mock repository instances
        porscheCarsRepository = mock(PorscheCarsRepository.class);
        porscheModelRepository = mock(PorscheModelRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        engineRepository = mock(EngineRepository.class);
        transmissionRepository = mock(TransmissionRepository.class);
        currencyRepository = mock(CurrencyRepository.class);
        userRepository = mock(UserRepository.class);


        porscheService = new PorscheServiceImpl(
              porscheModelRepository,
                categoryRepository,
                engineRepository,
                transmissionRepository,
                currencyRepository,
                porscheCarsRepository,
                userRepository

        );
    }

    @Test
    public void testGetPorscheCarsForHomePage() {
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

        PorscheModel porscheModel1 = new PorscheModel();
        porscheModel1.setModel(PorscheModelsEnum.Cayenne);

        PorscheModel porscheModel2 = new PorscheModel();
        porscheModel2.setModel(PorscheModelsEnum.Panamera);

        Category category1 = new Category();
        category1.setCategory(CategoryName.SUV);

        Category category2 = new Category();
        category2.setCategory(CategoryName.Sedan);

        Engine engine1 = new Engine();
        engine1.setEngineType(EngineTypeEnum.Gasoline);

        Engine engine2 = new Engine();
        engine2.setEngineType(EngineTypeEnum.Electric);

        Transmission transmission1 = new Transmission();
        transmission1.setTransmission(TransmissionType.Automatic);

        Transmission transmission2 = new Transmission();
        transmission2.setTransmission(TransmissionType.Manual);

        Currency currency1 = new Currency();
        currency1.setCurrency(CurrencyName.Euro);

        Currency currency2 = new Currency();
        currency2.setCurrency(CurrencyName.Pounds);

        PorscheCar myPorscheCar = new PorscheCar();
        myPorscheCar.setOwner(currentUser);
        myPorscheCar.setPorscheModel(porscheModel1);
        myPorscheCar.setReleaseDate(LocalDate.now());
        myPorscheCar.setCategory(category1);
        myPorscheCar.setEngine(engine1);
        myPorscheCar.setTransmission(transmission1);
        myPorscheCar.setCurrency(currency1);

        PorscheCar otherPorscheCar = new PorscheCar();
        UserEntity otherUser = new UserEntity();
        otherUser.setUsername("otherUser");
        otherPorscheCar.setOwner(otherUser);
        otherPorscheCar.setPorscheModel(porscheModel2);
        otherPorscheCar.setReleaseDate(LocalDate.now());
        otherPorscheCar.setCategory(category2);
        otherPorscheCar.setEngine(engine2);
        otherPorscheCar.setTransmission(transmission2);
        otherPorscheCar.setCurrency(currency2);

        when(porscheCarsRepository.findAll()).thenReturn(Arrays.asList(myPorscheCar, otherPorscheCar));

        // Act
        HomePorscheCarsDto result = porscheService.getPorscheCarsForHomePage();

        List<MyPorscheCarDto> myPorscheCars = new ArrayList<>();
        myPorscheCars.add(new MyPorscheCarDto(myPorscheCar));
        result.setMyPorscheCars(myPorscheCars);

        List<OthersPorscheCarDto> othersPorscheCars = new ArrayList<>();
        othersPorscheCars.add(new OthersPorscheCarDto(otherPorscheCar));
        result.setOthersPorscheCars(othersPorscheCars);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getMyPorscheCars().size());
        assertEquals(1, result.getOthersPorscheCars().size());
        assertEquals(otherPorscheCar.getOwner().getUsername(), result.getOthersPorscheCars().get(0).getOwnerUsername());
    }

    @Test
    public void testCreatePorsche() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        PorscheModel porscheModel = new PorscheModel(PorscheModelsEnum.Cayenne, "Description");
        Category category = new Category(CategoryName.SUV, "SUV description");
        Engine engine = new Engine(EngineTypeEnum.Gasoline, "Petrol engine");
        Transmission transmission = new Transmission(TransmissionType.Automatic, "Automatic transmission");
        Currency currency = new Currency(CurrencyName.Euro, "EUR origin", 1.0);
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.of(porscheModel));
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
            boolean result = porscheService.createPorsche(bindingModel);

            // Assert
            assertTrue(result);
            verify(porscheCarsRepository, times(1)).save(any(PorscheCar.class));
        }
    }

    @Test
    public void testCreatePorscheWithMissingModel() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("PorscheModel must be specified.", thrown.getMessage());
    }

    @Test
    public void testCreatePorscheWithInvalidModel() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("PorscheModel not found: Cayenne", thrown.getMessage());
    }

    @Test
    public void testCreatePorscheWithInvalidCategory() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.of(new PorscheModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("Category not found: SUV", thrown.getMessage());
    }

    @Test
    public void testCreatePorscheWithInvalidEngine() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.of(new PorscheModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("Engine not found for type: Gasoline", thrown.getMessage());
    }

    @Test
    public void testCreatePorscheWithInvalidTransmission() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.of(new PorscheModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("Transmission not found: Automatic", thrown.getMessage());
    }

    @Test
    public void testCreatePorscheWithInvalidCurrency() {
        // Arrange
        PorscheAddBindingModel bindingModel = new PorscheAddBindingModel();
        bindingModel.setPorscheModel(PorscheModelsEnum.Cayenne);
        bindingModel.setHorsePower(250);
        bindingModel.setImageUrl("http://example.com/image.jpg");
        bindingModel.setReleaseDate(LocalDate.now());
        bindingModel.setCategoryName(CategoryName.SUV);
        bindingModel.setEngineType(EngineTypeEnum.Gasoline);
        bindingModel.setTransmission(TransmissionType.Automatic);
        bindingModel.setKilometers(5000);
        bindingModel.setCurrencyName(CurrencyName.Euro);
        bindingModel.setPrice(80000.0);
        bindingModel.setDescription("Luxury SUV");

        when(porscheModelRepository.findByModel(PorscheModelsEnum.Cayenne)).thenReturn(Optional.of(new PorscheModel()));
        when(categoryRepository.findByCategory(CategoryName.SUV)).thenReturn(Optional.of(new Category()));
        when(engineRepository.findByEngineType(EngineTypeEnum.Gasoline)).thenReturn(Optional.of(new Engine()));
        when(transmissionRepository.findByTransmission(TransmissionType.Automatic)).thenReturn(Optional.of(new Transmission()));
        when(currencyRepository.findByCurrency(CurrencyName.Euro)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> porscheService.createPorsche(bindingModel));
        assertEquals("Currency not found: Euro", thrown.getMessage());
    }

    @Test
    public void testDeletePorsche() {
        // Arrange
        String porscheUUID = "1234";

        // Act
        porscheService.deletePorsche(porscheUUID);

        // Assert
        verify(porscheCarsRepository, times(1)).deletePorscheCarById(porscheUUID);
    }
}
