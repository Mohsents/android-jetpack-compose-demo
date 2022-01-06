/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mohsents.androidjetpackcomposedemo.todo

import com.google.common.truth.Truth.assertThat
import com.mohsents.androidjetpackcomposedemo.util.generateRandomTodoItem
import org.junit.Before
import org.junit.Test

class TodoViewModelTest {

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setUp() {
        viewModel = TodoViewModel()
    }

    @Test
    fun whenAddItem_updatesList() {
        // GIVEN
        val item = generateRandomTodoItem()
        // WHEN
        viewModel.addItem(item)
        // THEN
        assertThat(viewModel.todoItems).isEqualTo(listOf(item))
    }

    @Test
    fun whenRemovingItem_updatesList() {
        // GIVEN
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)

        // WHEN
        viewModel.removeItem(item1)

        // THEN
        assertThat(viewModel.todoItems).isEqualTo(listOf(item2))
    }

    @Test
    fun whenNotEditing_currentEditItemIsNull() {
        // GIVEN
        val item = generateRandomTodoItem()
        // WHEN
        viewModel.addItem(item)
        // THEN
        assertThat(viewModel.currentEditItem).isNull()
    }

    @Test
    fun whenEditingItem_currentEditItem_equalToEditedOne() {
        // GIVEN
        val item = generateRandomTodoItem()
        viewModel.addItem(item)

        // WHEN
        viewModel.onEditItemSelected(item)

        // THEN
        assertThat(viewModel.currentEditItem).isEqualTo(item)
    }

    @Test
    fun whenEditingItem_onEditItemChange_itemReplaced() {
        // GIVEN
        val item = generateRandomTodoItem()
        viewModel.addItem(item)

        // WHEN
        viewModel.onEditItemSelected(item)
        val expected = item.copy("task")
        viewModel.onEditItemChange(expected)

        // THEN
        assertThat(viewModel.todoItems).isEqualTo(listOf(expected))
    }

    @Test
    fun whenEditingItem_EditDone_currentEditItemIsNull() {
        // GIVEN
        val item = generateRandomTodoItem()
        viewModel.addItem(item)

        // WHEN
        viewModel.onEditItemSelected(item)
        viewModel.onEditDone()

        // THEN
        assertThat(viewModel.currentEditItem).isEqualTo(null)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenEditing_wrongItemThrows() {
        val item1 = generateRandomTodoItem()
        val item2 = generateRandomTodoItem()
        viewModel.addItem(item1)
        viewModel.addItem(item2)
        viewModel.onEditItemSelected(item1)
        val expected = item2.copy(task = "task")
        viewModel.onEditItemChange(expected)
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenNotEditing_onEditItemChangeThrows() {
        val item = generateRandomTodoItem()
        viewModel.onEditItemChange(item)
    }
}
