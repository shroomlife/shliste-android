package com.shroomlife.shliste.modules.recipes.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shroomlife.shliste.components.DefaultCard
import com.shroomlife.shliste.modules.recipes.Recipe
import com.shroomlife.shliste.utils.ColorUtils

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: ((String) -> Unit)? = null,
    content: @Composable () -> Unit
) {
    DefaultCard(
        backgroundColor = ColorUtils.colorWith20Opacity(recipe.color),
        header = {
            Text(
                text = recipe.name,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onClickHeader = if(onClick != null) {
            {
                onClick(recipe.uuid)
            }
        } else {
            null
        }
    ) {
        content()
    }
}
