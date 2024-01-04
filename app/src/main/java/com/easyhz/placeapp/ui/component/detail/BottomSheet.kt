package com.easyhz.placeapp.ui.component.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easyhz.placeapp.R
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.detail.BoardDetailViewModel
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.ui.theme.roundShape
import com.easyhz.placeapp.util.borderBottom

enum class DetailActions(
    @StringRes val label: Int,
    val icon: ImageVector
) {
    MODIFY(R.string.action_modify, Icons.Outlined.ModeEdit) {
        override fun onClick(viewModel: BoardDetailViewModel, id: Int) {
            //TODO : MODIFY
        }
    },
    DELETE(R.string.action_delete, Icons.Outlined.Delete) {
        override fun onClick(viewModel: BoardDetailViewModel, id: Int) {
            viewModel.deleteDetail(id)
        }
    };

    abstract fun onClick(viewModel: BoardDetailViewModel, id: Int)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    id: Int,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        ActionsCard(id = id)
        SpaceDivider(padding = 10)
    }
}

@Composable
fun ActionsCard(
    viewModel:BoardDetailViewModel = hiltViewModel(),
    id: Int
) {
    Box(
        modifier = Modifier
            .clip(roundShape)
    ) {
        LazyColumn {
            itemsIndexed(DetailActions.values()) { index, item ->
                val isBottom = index == DetailActions.values().size - 1
                ActionContent(
                    viewModel = viewModel,
                    id = id,
                    actions = item,
                    isBottom = isBottom
                )
            }
        }
    }
}

@Composable
fun ActionContent(
    viewModel: BoardDetailViewModel,
    id: Int,
    actions: DetailActions,
    isBottom: Boolean
) {
    val modifier = if (isBottom) Modifier else Modifier.borderBottom(
        PlaceAppTheme.colorScheme.secondaryBorder,
        0.5.dp
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { actions.onClick(viewModel, id) }
            .padding(start = 20.dp)
            .padding(vertical = 5.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = actions.icon,
            contentDescription = stringResource(id = actions.label),
            modifier = Modifier.size(30.dp)
        )
        SpaceDivider(padding = 5)
        Text(
            text = stringResource(id = actions.label),
            fontSize = 17.sp
        )
    }
}

@Preview
@Composable
private fun ActionsCardPreview() {
    val vm: BoardDetailViewModel =  hiltViewModel()
    ActionsCard(viewModel = vm, id = 1)
}