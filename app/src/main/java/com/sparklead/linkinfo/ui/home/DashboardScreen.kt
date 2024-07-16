@file:OptIn(ExperimentalMaterial3Api::class)

package com.sparklead.linkinfo.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.ui.theme.CircularProgress
import com.sparklead.linkinfo.ui.theme.PrimaryBlue
import com.sparklead.linkinfo.ui.theme.SweetError

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.dashboardUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDashboardDetails()
    }

    DashboardScreen(
        state = state,
        onRetry = {
            viewModel.getDashboardDetails()
        }
    )

}

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onRetry: () -> Unit
) {

    Scaffold(
        modifier = Modifier.padding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = PrimaryBlue),
                title = {
                    Text(
                        text = "Dashboard",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = {

                    }, colors = IconButtonDefaults.iconButtonColors(Color(0xFF2b81ff))) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        containerColor = PrimaryBlue
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(8.dp)) {

                    Text(
                        text = "Good Morning",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        ),
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    )
                    Row {
                        Text(
                            text = "Aditya Gupta",
                            modifier = Modifier.fillMaxWidth(),
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily(Font(R.font.outfit_medium))
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_greetings),
                            contentDescription = null
                        )
                    }

                    when (state) {
                        is DashboardUiState.DashboardDetails -> {
                            DashboardContent(details = state.details)
                        }

                        is DashboardUiState.Error -> SweetError(message = stringResource(id = state.message)) {
                            onRetry()
                        }

                        is DashboardUiState.Loading -> CircularProgress()
                    }
                }
            }
        }
    }
}


@Composable
fun DashboardContent(
    details: DashboardDto
) {

    Text(text = details.toString())
}

@Preview
@Composable
private fun DashboardScreenPreview() {

}