package se.solrike.cloud.serverless.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.solrike.cloud.serverless.common.NotFoundException;
import se.solrike.cloud.serverless.infomodel.Item;
import se.solrike.cloud.serverless.persistence.ItemRepository;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

  private ItemRepository mItemRepository;

  public ItemController(ItemRepository itemRepository) {
    mItemRepository = itemRepository;
  }

  @GetMapping("{itemId}")
  public Item getItemById(@PathVariable Integer itemId) {
    return mItemRepository.findById(itemId).orElseThrow(() -> new NotFoundException(itemId.toString()));
  }

  @PostMapping()
  public Item createItem(@RequestBody Item newItem) {
    return mItemRepository.save(newItem);
  }

  @GetMapping()
  List<Item> all() {
    return mItemRepository.findAll();
  }

}