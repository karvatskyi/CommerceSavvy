package backend.storage.api.service.impl;

import backend.storage.api.model.Item;
import backend.storage.api.model.Task;
import backend.storage.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private static final int MAX_QUANTITY = 30;
    private final static int FIRST_ELEMENT = 0;
    private static final int MAX_SIZE_TASK = 200;

    //private final EmployeeRepository employeeRepository;

    @Override
    public List<Task> createTasks(List<Item> input) {
        List<Task> tasks = new ArrayList<>();
        List<Item> sortedInput = split(input);
        while (!sortedInput.isEmpty()) {
            Task task = new Task();
            TreeSet<Item> items = new TreeSet<>();
            while (task.getSizeTask() <= 100
                    && items.size() < 10
                    && !sortedInput.isEmpty()
                    && !items.contains(sortedInput.get(FIRST_ELEMENT))) {
                    items.add(sortedInput.get(FIRST_ELEMENT));
                    task.setSizeTask(sortedInput.get(FIRST_ELEMENT).getQuantity());
                    sortedInput.remove(FIRST_ELEMENT);
            }
            task.setItemList(items);
            tasks.add(task);
        }
        return tasks;
    }

    private List<Item> split(List<Item> items) {
        List<Item> result = new LinkedList<>();
        for (int i = 0; i < items.size(); i++) {
            Item current = items.get(i);
            if (current.getQuantity() > 30) {
                List<Item> currentList = new LinkedList<>();
                while (current.getQuantity() >= 30) {
                    Item item = new Item();
                    item.setQuantity(MAX_QUANTITY);
                    item.setProduct(current.getProduct());
                    currentList.add(item);
                    current.setQuantity(current.getQuantity() - MAX_QUANTITY);
                }
                currentList.add(current);
                result.addAll(currentList);
            } else {
                result.add(current);
            }
        }
        return result;
    }
}
