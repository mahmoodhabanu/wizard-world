package com.example.houses_presentation.housedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.houses_domain.model.House

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseDetailScreen(
    house: House,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(house.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            DetailCard(title = "House Name", value = house.name)
            DetailCard(title = "Founder", value = house.founder)
            DetailCard(title = "House Colours", value = house.houseColours)
            DetailCard(title = "Animal", value = house.animal)
            DetailCard(title = "Element", value = house.element)
            DetailCard(title = "Ghost", value = house.ghost)
            DetailCard(title = "Common Room", value = house.commonRoom)

            if (house.heads.isNotEmpty()) {
                DetailCard(title = "Heads") {
                    house.heads.forEach { head ->
                        Text(
                            text = "${head.firstName} ${head.lastName}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            if (house.traits.isNotEmpty()) {
                DetailCard(title = "Traits") {
                    house.traits.forEach { trait ->
                        Text(
                            text = trait.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
            TextChangeCompose()
        }
    }
}

@Composable
fun DetailCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )


        }
    }
}

@Composable
fun DetailCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(4.dp))
            content()
        }
    }
}

@Composable
fun TextChangeCompose() {
    var text by remember { mutableStateOf("Hello, World!") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = { text = "Button Clicked!" }) {
            Text("Click Me")
        }
    }
}