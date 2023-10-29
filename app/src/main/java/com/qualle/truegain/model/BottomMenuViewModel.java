package com.qualle.truegain.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qualle.truegain.client.api.Category;
import com.qualle.truegain.client.api.Exercise;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BottomMenuViewModel extends ViewModel {

    private MutableLiveData<List<Category>> categoryData = new MutableLiveData<>();

    public List<Exercise> getExercises(long categoryId) {
        Optional<Category> category = categoryData.getValue().stream()
                .filter(c -> c.getId() == categoryId)
                .findFirst();

        if (category.isPresent()) {
            return category.get().getExercises();

        }
        return Collections.emptyList();
    }

    public boolean isAvailable() {
        boolean init = categoryData.isInitialized();

        return init && categoryData.getValue() != null && !categoryData.getValue().isEmpty();
    }

    public void setCategories(List<Category> categories) {
        categoryData.setValue(categories);
    }

    public List<Category> getCategories() {
        return categoryData.getValue();
    }
}
