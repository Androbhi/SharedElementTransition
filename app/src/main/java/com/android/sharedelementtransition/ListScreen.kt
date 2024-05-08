package com.android.sharedelementtransition

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.ListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (Animal) -> Unit,
) {
    val animals = listOf(
        Animal(
            img = R.drawable.img_deer,
            name = "Deer",
            description = stringResource(R.string.deer_description)
        ),
        Animal(
            img = R.drawable.img_monkey,
            name = "Monkey",
            description = stringResource(R.string.monkey_description)
        ),
        Animal(
            img = R.drawable.img_elephant,
            name = "Elephant",
            description = stringResource(R.string.elephant_description)
        ),
        Animal(
            img = R.drawable.img_tiger,
            name = "Tiger",
            description = stringResource(R.string.tiger_description)
        ),
        Animal(
            img = R.drawable.img_lion,
            name = "Lion",
            description = stringResource(R.string.lion_description)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Animals") }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues = it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                itemsIndexed(animals) { index, animal ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(animal) }
                    ) {
                        Image(
                            painter = painterResource(id = animal.img),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(16 / 9f)
                                .weight(0.5f)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image/${animal.img}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    }
                                )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(
                           modifier =  Modifier.weight(1.5f)
                        ) {
                            Text(
                                text = animal.name,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "text/${animal.name}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 1000)
                                        }
                                    )
                            )
                            Text(
                                text = animal.description,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .sharedElement(
                                        state = rememberSharedContentState(key = "text/${animal.description}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        boundsTransform = { _, _ ->
                                            tween(durationMillis = 1000)
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }
    )


}