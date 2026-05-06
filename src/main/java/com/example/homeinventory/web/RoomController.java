package com.example.homeinventory.web;

import com.example.homeinventory.model.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.homeinventory.repository.RoomRepository;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public String listRooms(Model model) {

        List<Room> rooms = roomRepository.findAll();
        System.out.println("rooms size: " + rooms.size());
        model.addAttribute("rooms", rooms);
        return "rooms/list";
    }
}
