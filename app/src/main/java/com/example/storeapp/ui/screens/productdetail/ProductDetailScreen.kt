package com.example.storeapp.ui.screens.productdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.storeapp.R
import com.example.storeapp.ui.screens.AppBarScreenWithIcon
import com.example.storeapp.ui.theme.colorRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    idProduct: Int,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchProduct(idProduct)
    }
    val state = viewModel.state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            AppBarScreenWithIcon(
                title = "PRODUCT",
                scrollBehavior = scrollBehavior,
                onBackPressed = onBackPressed
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        if (state.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = state.product.title,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = state.product.image, contentDescription = state.product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                Text(
                    text = "US$ ${state.product.price}",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "${state.product.rating.count}+sold")
                    Column {
                        Row {
                            Text(
                                text = state.product.rating.rate.toString(),
                            )
                            var isHalfStar = (state.product.rating.rate.rem(1)) != 0.0
                            for (i in 1..5) {
                                Icon(
                                    tint = colorRating,
                                    contentDescription = stringResource(R.string.star),
                                    imageVector = if (i <= state.product.rating.rate) {
                                        Icons.Rounded.Star
                                    } else {
                                        if (isHalfStar) {
                                            isHalfStar = false
                                            ImageVector.vectorResource(id = R.drawable.round_star_half_24)
                                        } else {
                                            ImageVector.vectorResource(id = R.drawable.outline_star_border_24)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
                Text(
                    text = state.product.description,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

