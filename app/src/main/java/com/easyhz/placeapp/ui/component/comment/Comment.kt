package com.easyhz.placeapp.ui.component.comment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easyhz.placeapp.constants.ContentCardIcons
import com.easyhz.placeapp.domain.model.feed.comment.CommentContent
import com.easyhz.placeapp.ui.component.SpaceDivider
import com.easyhz.placeapp.ui.theme.PlaceAppTheme
import com.easyhz.placeapp.util.toTimeFormat


@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    item: CommentContent
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = ContentCardIcons.USER.icon,
            contentDescription = stringResource(id = ContentCardIcons.USER.label),
            modifier = Modifier.size(36.dp)
        )
        Column(
            modifier = Modifier.padding(start = 5.dp)
        ) {
            UserDateRow(
                userName = item.nickname,
                regDate = item.regDate.toTimeFormat(),
                padding = 5
            )
            Text(item.content)
        }
    }

}

@Composable
private fun UserDateRow(
    userName: String,
    regDate:String,
    padding: Int
) {
    Row(
        modifier = Modifier.padding(bottom = padding.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            userName,
            fontWeight = FontWeight.Bold,
        )
        SpaceDivider(padding = padding)
        Text(
            text = regDate,
            color = PlaceAppTheme.colorScheme.subText,
            style = TextStyle.Default.copy(
                textAlign = TextAlign.Justify,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false,
                ),
            ),
        )
    }
}

@Preview("CommentCard Preview")
@Composable
private fun CommentCardPreview() {
    val mock = CommentContent(
            commentId = 1,
            userId = "user1",
            nickname = "유저1",
            content = "잠온다 ㄹㅇ 개 자고 싶다 그냥 먹고 자고 놀고 하고 싶다.",
            regDate = "2023.11.08"
        )

    PlaceAppTheme {
        CommentCard(item = mock)
    }
}
