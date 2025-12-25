package com.company.crm.config;

import com.company.crm.controllers.implement.*;
import com.company.crm.controllers.interfaces.GenericController;

import com.company.crm.dao.inmemory.*;
import com.company.crm.dao.interfaces.*;
import com.company.crm.dao.jdbc.*;
import com.company.crm.services.implement.*;
import com.company.crm.utils.GenerateDatabase;

import java.util.ArrayList;
import java.util.List;

public class ApplicationFactory {

//    private static final DaoMode MODE = DaoMode.MEMORY;
     private static final DaoMode MODE = DaoMode.JDBC;

    public static MainMenuControllerImpl createApplication() {
        List<GenericController> controllers = new ArrayList<>();

        controllers.add(createBookingController());
        controllers.add(createGroupApplicationController());
        controllers.add(createClientController());
        controllers.add(createStaffController());
        controllers.add(createLivingRoomController());

        return new MainMenuControllerImpl(controllers);
    }


    // ---------------------- CLIENT ----------------------
    public static ClientControllerImpl createClientController() {
        ClientDao repo;

        switch (MODE) {
            case MEMORY:
                ClientDaoInMemory memory = new ClientDaoInMemory();
                memory.addAll(GenerateDatabase.generateClients(10));
                repo = memory;
                break;
            case JDBC:
                repo = new ClientDaoJdbcImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + MODE);
        }

        ClientServiceImpl service = new ClientServiceImpl(repo);
        return new ClientControllerImpl(service);
    }



    // ---------------------- STAFF ----------------------
    public static StaffControllerImpl createStaffController() {
        StaffDao repo;

        switch (MODE) {
            case MEMORY:
                StaffDaoInMemory memory = new StaffDaoInMemory();
                memory.addAll(GenerateDatabase.generateStaff(10));
                repo = memory;
                break;
            case JDBC:
                repo = new StaffDaoJdbcImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + MODE);
        }

        StaffServiceImpl service = new StaffServiceImpl(repo);
        return new StaffControllerImpl(service);
    }



    // ---------------------- LIVING ROOM ----------------------
    public static LivingRoomControllerImpl createLivingRoomController() {
        LivingRoomDao repo;

        switch (MODE) {
            case MEMORY:
                LivingRoomDaoInMemory memory = new LivingRoomDaoInMemory();
                memory.addAll(GenerateDatabase.generateLivingRooms(10));
                repo = memory;
                break;
            case JDBC:
                repo = new LivingRoomDaoJdbcImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + MODE);
        }

        LivingRoomServiceImpl service = new LivingRoomServiceImpl(repo);
        return new LivingRoomControllerImpl(service);
    }


    // ---------------------- BOOKING ----------------------
    public static BookingControllerImpl createBookingController() {
        BookingDao repo;

        switch (MODE) {
            case MEMORY:
                BookingDaoInMemory memory = new BookingDaoInMemory();
                memory.addAll(GenerateDatabase.generateBookings(10));
                repo = memory;
                break;
            case JDBC:
                repo = new BookingDaoJdbcImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + MODE);
        }

        BookingServiceImpl service = new BookingServiceImpl(repo);
        return new BookingControllerImpl(service);
    }



    // ---------------------- GROUP APPLICATION ----------------------
    public static GroupApplicationControllerImpl createGroupApplicationController() {
        GroupApplicationDao repo;

        switch (MODE) {
            case MEMORY:
                GroupApplicationDaoInMemory memory = new GroupApplicationDaoInMemory();
                memory.addAll(GenerateDatabase.generateGroupApps(10));
                repo = memory;
                break;
            case JDBC:
                repo = new GroupApplicationDaoJdbcImpl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + MODE);
        }

        GroupApplicationServiceImpl service = new GroupApplicationServiceImpl(repo);
        return new GroupApplicationControllerImpl(service);
    }

}
