package com.easyhz.placeapp.ui.component.detail

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.ui.component.ContentCardIconSections
import com.easyhz.placeapp.ui.component.SimpleIconButton
import com.easyhz.placeapp.ui.component.map.NaverMap
import com.easyhz.placeapp.ui.theme.PlaceAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    context: Context,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier
    ) {
        MapBottomSheetHeader()
        NaverMap(
            context = context,
            modifier = modifier
        )
    }
}
@Composable
private fun MapBottomSheetHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            SimpleIconButton(
                icon = ContentCardIconSections.BOOKMARK.icon,
                contentDescription = stringResource(id = ContentCardIconSections.BOOKMARK.label),
                modifier = Modifier.size(30.dp),
                onClick = { }
            )
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text("여수 시청")
        }
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text("모아보기")
        }
    }
}

@Preview
@Composable
private fun MapBottomSheetHeaderPreview() {
    PlaceAppTheme {
        MapBottomSheetHeader()
    }
}