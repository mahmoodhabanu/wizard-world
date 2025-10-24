package com.example.houses_presentation.houselist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.core_common.UIState
import com.example.core_common.toMessage
import com.example.houses_domain.model.House
import com.example.houses_presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HouseListScreen(
    viewModel: HouseListViewModel = hiltViewModel(),
    onHouseClick: (House) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.wizarding_houses_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { viewModel.fetchHouses() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)

        when (val state = uiState) {
            UIState.Loading -> {
                Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UIState.Success -> {
                if (state.data.isEmpty()) {
                    Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                        Text("No houses found. Tap refresh to try again.")
                    }
                } else {
                    LazyColumn(
                        modifier = contentModifier,
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        state = lazyListState // Assign the state to enable scroll observation
                    ) {
                        items(state.data) { house ->
                            HouseListItem(house = house, onClick = { onHouseClick(house) })
                        }
                    }
                }
            }
            is UIState.Error -> {
                Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: ${state.error.toMessage()}", color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(8.dp))
                        Text("Please check your network or try again.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            UIState.Idle -> {
                Box(modifier = contentModifier, contentAlignment = Alignment.Center) {
                    Text("Welcome to Wizard World! Fetching houses...")
                }
            }
        }
    }
}

@Composable
fun HouseListItem(house: House, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = house.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Founder: ${house.founder}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Animal: ${house.animal}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}