package ru.ylab.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.hw.dto.CarDTO;
import ru.ylab.hw.entity.Car;
import ru.ylab.hw.mapper.CarMapper;
import ru.ylab.hw.repository.impl.CarRepositoryImpl;
import ru.ylab.hw.service.CarService;
import ru.ylab.hw.service.impl.CarServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/api/cars/*")
public class CarServlet extends HttpServlet {
    private final CarService carService;
    private final ObjectMapper objectMapper;

    public CarServlet() {
        this.carService = new CarServiceImpl(new CarRepositoryImpl());
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Handles {@code POST} requests to add a new car.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException      if the request for the {@code POST} cannot be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            CarDTO carDTO = objectMapper.readValue(req.getInputStream(), CarDTO.class);
            Car car = mapToEntity(carDTO);
            carService.addCar(car);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(CarMapper.INSTANCE.toDTO(car)));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    /**
     * Handles {@code PUT} requests to update an existing car.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException      if the request for the {@code PUT} cannot be handled
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String vin = getVinFromPath(req);
        try {
            CarDTO carDTO = objectMapper.readValue(req.getInputStream(), CarDTO.class);
            carDTO.setVin(vin);

            Car car = mapToEntity(carDTO);
            carService.editCar(car);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(CarMapper.INSTANCE.toDTO(car)));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    /**
     * Handles {@code DELETE} requests to remove a car by its VIN.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String vin = getVinFromPath(req);
        carService.deleteCar(vin);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Handles {@code GET} requests to retrieve one or more cars.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException      if the request for the {@code GET} cannot be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String vin = getVinFromPath(req);
        if (vin != null) {
            Optional<Car> car = carService.getCar(vin);
            if (car.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(objectMapper.writeValueAsString(CarMapper.INSTANCE.toDTO(car.get())));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<Car> cars = carService.getAllCars();
            resp.setStatus(HttpServletResponse.SC_OK);
            List<CarDTO> carDTOS = cars.stream()
                    .map(CarMapper.INSTANCE::toDTO)
                    .toList();
            resp.getWriter().write(objectMapper.writeValueAsString(carDTOS));
        }
    }

    /**
     * Extracts the VIN number from the request path.
     *
     * @param req the HTTP request
     * @return the VIN number or {@code null} if not present
     */
    private String getVinFromPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            return null;
        }
        return path.substring(1);
    }

    private Car mapToEntity(CarDTO carDTO) {
        return CarMapper.INSTANCE.toEntity(carDTO);
    }
}
