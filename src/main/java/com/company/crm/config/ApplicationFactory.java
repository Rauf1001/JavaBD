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
        controllers.add(createLiving_roomController());

        return new MainMenuControllerImpl(controllers);
    }


    // ---------------------- CLIENT ----------------------
    public static ClientControllerControllerImpl createClientController() {
        ClientDao repo = switch (MODE) {
            case MEMORY -> {
                ClientDaoInMemory memory = new ClientDaoInMemory();
                memory.addAll(GenerateDatabase.generateClients(10));

                yield memory;
            }
            case JDBC -> new ClientDaoJdbcImpl();
        };

        ClientServiceServiceImpl service = new ClientServiceServiceImpl(repo);
        return new ClientControllerControllerImpl(service);
    }


    // ---------------------- STAFF ----------------------
    public static StaffControllerControllerImpl createStaffController() {
        StaffDao repo = switch (MODE) {
            case MEMORY -> {
                StaffDaoInMemory memory = new StaffDaoInMemory();
                memory.addAll(GenerateDatabase.generateStaff(10));
                yield memory;
            }
            case JDBC -> new StaffDaoJdbcImpl();
        };

        StaffServiceServiceImpl service = new StaffServiceServiceImpl(repo);
        return new StaffControllerControllerImpl(service);
    }


    // ---------------------- LIVING ROOM ----------------------
    public static LivingRoomControllerControllerImpl createLiving_roomController() {
        LivingRoomDao repo = switch (MODE) {
            case MEMORY -> {
                LivingRoomDaoInMemory memory = new LivingRoomDaoInMemory();
                memory.addAll(GenerateDatabase.generateLivingRooms(10));
                yield memory;
            }
            case JDBC -> new LivingRoomDaoJdbcImpl();
        };

        LivingRoomServiceServiceImpl service = new LivingRoomServiceServiceImpl(repo);
        return new LivingRoomControllerControllerImpl(service);
    }


    // ---------------------- BOOKING ----------------------
    public static BookingControllerImpl createBookingController() {
        BookingDao repo = switch (MODE) {
            case MEMORY -> {
                BookingDaoInMemory memory = new BookingDaoInMemory();
                memory.addAll(GenerateDatabase.generateBookings(10));
                yield memory;
            }
            case JDBC -> new BookingDaoJdbcImpl();
        };

        BookingServiceServiceImpl service = new BookingServiceServiceImpl(repo);
        return new BookingControllerImpl(service);
    }


    // ---------------------- GROUP APPLICATION ----------------------
    public static GroupApplicationControllerImpl createGroupApplicationController() {

        GroupApplicationDao repo = switch (MODE) {
            case MEMORY -> {
                GroupApplicationDaoInMemory memory = new GroupApplicationDaoInMemory();
                memory.addAll(GenerateDatabase.generateGroupApps(10));
                yield memory;
            }
            case JDBC -> new GroupApplicationDaoJdbcImpl();
        };

        GroupApplicationServiceServiceImpl service = new GroupApplicationServiceServiceImpl(repo);
        return new GroupApplicationControllerImpl(service);
    }
}
